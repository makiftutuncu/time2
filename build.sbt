name := "time2"

version := "0.1"

scalaVersion := "2.12.4"

val circeVersion     = "0.9.0"
val scalaTestVersion = "3.0.4"

libraryDependencies ++= Seq(
  "io.circe"      %% "circe-core"    % circeVersion,
  "io.circe"      %% "circe-generic" % circeVersion,
  "io.circe"      %% "circe-parser"  % circeVersion,
  "org.scalatest" %% "scalatest"     % scalaTestVersion % Test
)
