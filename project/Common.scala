import sbt._
import Keys._
import play.PlayImport._

object Common {

  def appName = "pray-ddd"

  def settings(theName: String) = Seq(
    name := theName,
    organization := "com.pray",
    version := "1.0-SNAPSHOT",
    scalaVersion := "2.11.6",
    scalacOptions ++= Seq("-feature", "-deprecation", "-unchecked", "-language:reflectiveCalls")
  )

  val appSettings = settings(appName)

  def layeredSettings(layerName: String) = settings(layerName)

  val commonDependencies = Seq(
    cache,
    ws
  )

  object Library {

    private lazy val mysqlConnectorVersion = "5.1.33"

    private lazy val skinnyOrmVersion = "1.3.+"

    private lazy val playFlywayVersion = "1.2.1"

    private lazy val scalikejdbcVersion = "2.2.+"

    private lazy val scalikejdbcPlayVersion = "2.3.+"

    lazy val mysqlConnector = "mysql" % "mysql-connector-java" % mysqlConnectorVersion

    lazy val skinnyOrm = "org.skinny-framework" %% "skinny-orm" % skinnyOrmVersion

    lazy val playFlyway = "com.github.tototoshi" %% "play-flyway" % playFlywayVersion

    def scalikejdbc = Seq(
      "org.scalikejdbc" %% "scalikejdbc"                     % scalikejdbcVersion,
      "org.scalikejdbc" %% "scalikejdbc-config"              % scalikejdbcVersion,
      "org.scalikejdbc" %% "scalikejdbc-test"                % scalikejdbcVersion
    )

    def scalikejdbcPlay = Seq(
      "org.scalikejdbc" %% "scalikejdbc-play-plugin"         % scalikejdbcPlayVersion,
      "org.scalikejdbc" %% "scalikejdbc-play-fixture-plugin" % scalikejdbcPlayVersion
    )

  }

}
