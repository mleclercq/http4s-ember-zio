val scala3Version = "3.2.2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "http4s-ember-zio",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    run / fork := true,
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "2.0.11",
      "dev.zio" %% "zio-interop-cats" % "23.0.0.4",
      "org.http4s" %% "http4s-ember-server" % "0.23.19"
    )
  )
