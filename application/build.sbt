import Common.Library._

name := """pray-application"""

version := "1.0-SNAPSHOT"

libraryDependencies ++=
  scalikejdbc ++:
  json4s

def json4s = Seq(
  "com.github.tototoshi" %% "play-json4s-native" % json4sVersion,
  "com.github.tototoshi" %% "play-json4s-test-native" % json4sVersion % "test"
)

lazy val json4sVersion = "0.3.1"