import Common.Library._

name := """pray-domain"""

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  mysqlConnector,
  skinnyOrm,
  playFlyway,
  h2,
  bcrypt ) ++:
  scalikejdbc ++:
  scalikejdbcPlay

lazy val h2 =   "com.h2database" % "h2" % "1.4.+"

lazy val bcrypt = "com.github.t3hnar" %% "scala-bcrypt" % bcryptVersion

lazy val bcryptVersion = "2.4"
