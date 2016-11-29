lazy val commonBuildSettings = Seq(
  organization := "com.simplehttpserver",
  name := "SimpleHttpServer",
  version := "0.0.1",
  scalaVersion in ThisBuild := "2.11.8",
  scalacOptions ++= Seq(
    "-deprecation", // Emit warning and location for usages of deprecated APIs.
    "-feature", // Emit warning and location for usages of features that should be imported explicitly.
    "-unchecked", // Enable additional warnings where generated code depends on assumptions.
    "-Xfatal-warnings", // Fail the compilation if there are any warnings.
    "-Xlint", // Enable recommended additional warnings.
    "-Yno-adapted-args", // Warn if an argument list is modified to match the receiver.
    "-Ywarn-dead-code", // Warn when dead code is identified.
    "-Ywarn-inaccessible", // Warn about inaccessible types in method signatures.
    "-Xlint:missing-interpolator", // A string literal appears to be missing an interpolator id.
    "-Ywarn-unused-import", //Warn when imports are unused
    "-Ywarn-unused", // Warn when local and private vals, vars, defs, and types are unused.
    "-Ywarn-nullary-override", // Warn when non-nullary overrides nullary, e.g. def foo() over def foo.
    "-explaintypes", //Explain type errors in more detail.
    "-encoding", "UTF-8",
    "-Xexperimental",
    "-Ydelambdafy:method", // the strategy used for translating lambdas into JVM code. The current standard is "inline" but they're moving towards "method."
    "-Xfuture" // Turn on future language features and guard against a few deprecated features around Futures.
  ),
  scalafmtConfig in ThisBuild := Some(file(".scalafmt.conf")),
  resolvers ++= Seq(
    Resolver.defaultLocal,
    Resolver.sonatypeRepo("releases"),
    Resolver.sonatypeRepo("snapshots")
  )
)
lazy val root = (project in file("."))
  .settings(commonBuildSettings)
  .settings(libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "3.0.0" % "test"
  ))
