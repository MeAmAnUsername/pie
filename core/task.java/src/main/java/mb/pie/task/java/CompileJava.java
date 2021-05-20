package mb.pie.task.java;

import mb.common.message.KeyedMessages;
import mb.common.message.KeyedMessagesBuilder;
import mb.common.message.Severity;
import mb.common.region.Region;
import mb.pie.api.ExecContext;
import mb.pie.api.Supplier;
import mb.pie.api.TaskDef;
import mb.pie.api.stamp.resource.ResourceStampers;
import mb.resource.hierarchical.HierarchicalResource;
import mb.resource.hierarchical.ResourcePath;
import mb.resource.hierarchical.match.ResourceMatcher;
import mb.resource.hierarchical.match.path.PathMatcher;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.immutables.value.Value;

import javax.tools.Diagnostic;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Value.Enclosing
public class CompileJava implements TaskDef<CompileJava.Input, KeyedMessages> {
    @Value.Immutable
    public interface Input extends Serializable {
        class Builder extends CompileJavaData.Input.Builder {}

        static Builder builder() { return new Builder(); }


        List<ResourcePath> sourceFiles();

        List<ResourcePath> sourcePaths();

        List<ResourcePath> sourceDirectoryPaths(); // Source path that is only used for packages (directories)


        // Using File for classPath and annotationProcessorPath, as handling this with ResourcePath takes too much effort at the moment.

        List<File> classPaths(); // If empty, passes no classpath, which makes javac use the current system classloader as the classpath.

        List<File> annotationProcessorPaths(); // If empty, passes no processorpath, which makes javac use the current system classloader as the processorpath.


        Optional<String> release();


        ResourcePath sourceFileOutputDirectory();

        ResourcePath classFileOutputDirectory();


        @Value.Default default boolean reportWarnings() { return true; }

        @Value.Default default boolean emitDebuggingAttributes() { return true; }


        List<String> additionalOptions();


        List<Supplier<?>> originTasks();

        Optional<Serializable> key();
    }


    private final JavaCompiler compiler;
    private final FileManagerFactory fileManagerFactory;
    private final JavaFileObjectFactory javaFileObjectFactory;

    public CompileJava(JavaCompiler compiler, FileManagerFactory fileManagerFactory, JavaFileObjectFactory javaFileObjectFactory) {
        this.compiler = compiler;
        this.fileManagerFactory = fileManagerFactory;
        this.javaFileObjectFactory = javaFileObjectFactory;
    }

    public CompileJava() {
        this(ToolProvider.getSystemJavaCompiler(), JavaResourceManager::new, new JavaResource.Factory());
    }


    @Override public String getId() {
        return getClass().getName();
    }

    @Override public KeyedMessages exec(ExecContext context, Input input) throws Exception {
        for(final Supplier<?> originTask : input.originTasks()) {
            context.require(originTask);
        }

        final ArrayList<JavaFileObject> compilationUnits = new ArrayList<>();
        for(ResourcePath sourceFilePath : input.sourceFiles()) {
            final HierarchicalResource sourceFile = context.require(sourceFilePath, ResourceStampers.<HierarchicalResource>modifiedFile());
            compilationUnits.add(javaFileObjectFactory.create(sourceFile));
        }
        final ArrayList<HierarchicalResource> sourcePath = new ArrayList<>();
        for(ResourcePath sourcePathPart : input.sourcePaths()) {
            final HierarchicalResource sourceDirectory = context.getHierarchicalResource(sourcePathPart);
            // Require directories recursively, so we re-execute whenever a file is added/removed from a directory.
            sourceDirectory.walkForEach(ResourceMatcher.ofDirectory(), context::require);
            // Require all Java source files recursively, so we re-execute whenever a file changes.
            sourceDirectory.walkForEach(ResourceMatcher.ofFile().and(ResourceMatcher.ofPath(PathMatcher.ofExtension("java"))), context::require);
            sourcePath.add(sourceDirectory);
        }
        for(ResourcePath sourceDirectoryPath : input.sourceDirectoryPaths()) {
            final HierarchicalResource sourceDirectory = context.getHierarchicalResource(sourceDirectoryPath);
            // Require directories recursively, so we re-execute whenever a file is added/removed from a directory.
            sourceDirectory.walkForEach(ResourceMatcher.ofDirectory(), context::require);
            sourcePath.add(sourceDirectory);
        }
        final HierarchicalResource sourceFileOutputDir = context.getHierarchicalResource(input.sourceFileOutputDirectory());
        final HierarchicalResource classFileOutputDir = context.getHierarchicalResource(input.classFileOutputDirectory());

        final JavaFileManager fileManager = fileManagerFactory.create(
            compiler.getStandardFileManager(null, null, null),
            javaFileObjectFactory,
            context.getResourceService(),
            sourcePath,
            sourceFileOutputDir,
            classFileOutputDir
        );
        final ArrayList<String> options = new ArrayList<>();
        input.release().ifPresent(release -> {
            if(compiler.isSupportedOption("--release") != -1) {
                options.add("--release");
                options.add(release);
            } else {
                options.add("-source");
                options.add(release);
                options.add("-target");
                options.add(release);
            }
        });
        if(!input.classPaths().isEmpty()) {
            options.add("-classpath");
            options.add(input.classPaths().stream().map(File::toString).collect(Collectors.joining(File.pathSeparator)));
        }
        if(!input.annotationProcessorPaths().isEmpty()) {
            options.add("-processorpath");
            options.add(input.annotationProcessorPaths().stream().map(File::toString).collect(Collectors.joining(File.pathSeparator)));
        }
        if(!input.reportWarnings()) {
            options.add("-nowarn");
        }
        if(!input.emitDebuggingAttributes()) {
            options.add("-g:none");
        }
        options.addAll(input.additionalOptions());
        final KeyedMessagesBuilder messagesBuilder = new KeyedMessagesBuilder();
        final CompilationTask compilationTask = compiler.getTask(null, fileManager, d -> collectDiagnostic(d, messagesBuilder), options, null, compilationUnits);

        compilationTask.call();

        // Provide generated Java source files.
        provideFilesInDirectoryOfExtension(context, sourceFileOutputDir, "java");
        // Provide compiled Java class files.
        provideFilesInDirectoryOfExtension(context, classFileOutputDir, "class");

        return messagesBuilder.build();
    }

    @Override public Serializable key(Input input) {
        return input.key().orElse(input);
    }


    private static void provideFilesInDirectoryOfExtension(ExecContext context, HierarchicalResource directory, String extension) throws IOException {
        directory.walkForEach(ResourceMatcher.ofFile().and(ResourceMatcher.ofPath(PathMatcher.ofExtension(extension))), context::provide);
    }

    private static void collectDiagnostic(Diagnostic<? extends JavaFileObject> diagnostic, KeyedMessagesBuilder messagesBuilder) {
        final String text = diagnostic.getMessage(null);
        final Severity severity = toSeverity(diagnostic.getKind());
        final @Nullable ResourcePath resource;
        final @Nullable JavaFileObject source = diagnostic.getSource();
        if(source instanceof JavaResource) {
            resource = ((JavaResource)source).resource.getPath();
        } else {
            resource = null;
        }
        final @Nullable Region region;
        if(diagnostic.getPosition() != Diagnostic.NOPOS) {
            region = Region.fromOffsets((int)diagnostic.getStartPosition(), (int)diagnostic.getEndPosition(), (int)diagnostic.getLineNumber());
        } else {
            region = null;
        }
        messagesBuilder.addMessage(text, severity, resource, region);
    }

    private static Severity toSeverity(Diagnostic.Kind kind) {
        switch(kind) {
            case ERROR:
                return Severity.Error;
            case WARNING:
            case MANDATORY_WARNING:
                return Severity.Warning;
            case NOTE:
                return Severity.Info;
            default:
                return Severity.Debug;
        }
    }
}
