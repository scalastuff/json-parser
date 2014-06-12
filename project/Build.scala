import sbt._
import Keys._

object JsonParserBuild extends Build {

  def defaultSettings =
    Project.defaultSettings ++
      Seq(
        sbtPlugin := false, 
        organization := "org.scalastuff",
        version := "2.0.1",
        scalaVersion := "2.11.1",
        crossScalaVersions := Seq("2.10.4", "2.11.1"),
        scalacOptions ++= Seq("-deprecation", "-unchecked", "-encoding", "utf8", "-feature"))
          
  def publishSettings = Seq(
    publishMavenStyle := true,
    publishArtifact in Test := false,
    pomIncludeRepository := { _ => false },
    licenses := Seq("The Apache Software Licence, Version 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
    homepage := Some(url("https://github.com/scalastuff/json-parser")),
    publishTo := {
      if (version.value.trim.endsWith("SNAPSHOT"))
        Some("snapshots" at "https://oss.sonatype.org/content/repositories/snapshots")
      else
        Some("releases"  at "https://oss.sonatype.org/service/local/staging/deploy/maven2")},
    pomExtra := 
      <scm>
        <connection>scm:git:git@github.com:scalastuff/json-parser.git</connection>
        <url>https://github.com/scalastuff/json-parser</url>
      </scm>
      <developers>
        <developer>
          <id>ruudditerwich</id>
          <name>Ruud Diterwich</name>
          <url>http://ruud.diterwich.com</url>
        </developer>
      </developers>) 

    
  val jsonParser = Project(id = "json-parser", base = file("."), settings = defaultSettings ++ publishSettings ++ Seq(
    libraryDependencies <++= scalaBinaryVersion {
      case "2.11" => Seq(
        "io.spray" % "spray-json_2.11.0-RC4" % "1.2.6" % "optional")
      case _ => Seq(
        "io.spray" %% "spray-json" % "1.2.6" % "optional")
    },
    libraryDependencies += "org.specs2" %% "specs2" % "2.3.12" % "test"))
}
