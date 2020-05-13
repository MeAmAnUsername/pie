plugins {
  id("org.metaborg.gradle.config.java-library")
  id("org.metaborg.gradle.config.junit-testing")
  id("org.metaborg.spoofax.gradle.project")
}

spoofax {
  addCompileLanguageProjectDep(":pie.lang")
}

val daggerVersion = "2.25.2"

dependencies {
  api(platform(project(":pie.depconstraints")))

  annotationProcessor("com.google.dagger:dagger-compiler:$daggerVersion")

  compileOnly("org.checkerframework:checker-qual-android")

  testImplementation("org.metaborg:resource")
  testImplementation(project(":pie.runtime"))
  testImplementation(project(":pie.lang.runtime.java"))

  testAnnotationProcessor("com.google.dagger:dagger-compiler:$daggerVersion")

  testCompileOnly("org.checkerframework:checker-qual-android")
}
