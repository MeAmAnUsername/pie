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
  api("com.google.dagger:dagger:$daggerVersion")

  annotationProcessor("com.google.dagger:dagger-compiler:$daggerVersion")

  compileOnly("org.checkerframework:checker-qual-android")

  testAnnotationProcessor("com.google.dagger:dagger-compiler:$daggerVersion")

  testCompileOnly("org.checkerframework:checker-qual-android")

  testImplementation("org.metaborg:resource")
  testImplementation(project(":pie.runtime"))
  testImplementation(project(":pie.lang.runtime.java"))
  testImplementation(project(":pie.dagger"))
}

sourceSets {
  main {
    java.srcDirs(java.srcDirs, "build/generated/sources")
  }
}
