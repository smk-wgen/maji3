import sbt._
import Keys._
import play.Project._
import cloudbees.Plugin._


object ApplicationBuild extends Build {

  val appName         = "jphm1"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    jdbc,
    anorm,
    "mysql" % "mysql-connector-java" % "5.1.18"
  )


  val main = play.Project(appName, appVersion, appDependencies)
    // Add your own project settings here
    .settings(cloudBeesSettings :_*)
    .settings(CloudBees.applicationId := Some("maji1"))

}
