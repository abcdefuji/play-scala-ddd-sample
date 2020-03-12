Common.appSettings

lazy val infrastructure = (project in file("infrastructure"))
  .enablePlugins(PlayScala)
  .settings(Common.layeredSettings("infrastructure"): _*)

lazy val domain = (project in file("domain"))
  .enablePlugins(PlayScala)
  .dependsOn(infrastructure)
  .settings(Common.layeredSettings("domain"): _*)

lazy val application = (project in file("application"))
  .enablePlugins(PlayScala)
  .dependsOn(infrastructure, domain)
  .settings(Common.layeredSettings("application"): _*)

lazy val admin = (project in file("application/admin"))
  .enablePlugins(PlayScala)
  .dependsOn(infrastructure, domain, application)
  .settings(Common.layeredSettings("admin"): _*)

lazy val api = (project in file("application/api"))
  .enablePlugins(PlayScala)
  .dependsOn(infrastructure, domain, application)
  .settings(Common.layeredSettings("api"): _*)

lazy val web = (project in file("application/web"))
  .enablePlugins(PlayScala)
  .dependsOn(infrastructure, domain, application)
  .settings(Common.layeredSettings("web"): _*)

lazy val root = (project in file(".")).enablePlugins(PlayScala)
  .aggregate(infrastructure, domain, application, admin, api, web)
  .dependsOn(infrastructure, domain, application, admin, api, web)
  .settings(Common.appSettings: _*)

libraryDependencies ++= Common.commonDependencies
