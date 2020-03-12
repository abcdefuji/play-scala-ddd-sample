import Common.Library._

name := """pray-infrastructure"""

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  cache,
  skinnyOrm ) ++:
  scalikejdbc