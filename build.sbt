name := "time2"

version := "0.1"

scalaVersion := "2.12.4"

val circeVersion      = "0.9.0"
val pureConfigVersion = "0.9.0"
val scalaTestVersion  = "3.0.4"

libraryDependencies ++= Seq(
  "com.github.pureconfig" %% "pureconfig" % pureConfigVersion,
  "io.circe"              %% "circe-core" % circeVersion,
  "org.scalatest"         %% "scalatest"  % scalaTestVersion % Test
)
