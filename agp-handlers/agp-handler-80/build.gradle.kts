plugins {
  kotlin("jvm")
  alias(libs.plugins.ksp)
  alias(libs.plugins.mavenPublish)
}

dependencies {
  ksp(libs.autoService.ksp)

  api(projects.agpHandlers.agpHandlerApi)

  implementation(libs.autoService.annotations)

  compileOnly("com.android.tools.build:gradle:8.0.2")
  compileOnly(gradleApi())
}
