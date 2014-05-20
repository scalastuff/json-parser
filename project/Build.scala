import sbt._
import Keys._
import com.typesafe.sbteclipse.plugin.EclipsePlugin.EclipseKeys
import com.typesafe.sbteclipse.plugin.EclipsePlugin.EclipseCreateSrc

object JsonBuild extends Build {

  def defaultSettings =
    Project.defaultSettings ++
      Seq(
        sbtPlugin := false, 
        organization := "org.scalastuff",
        version := "1.1.3-SNAPSHOT",
        scalaVersion := "2.10.4",
        crossScalaVersions := Seq("2.10.4", "2.11.0"),
        scalacOptions ++= Seq("-deprecation", "-unchecked", "-encoding", "utf8", "-feature"),
        EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource,
        EclipseKeys.withSource := true)
          
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
    libraryDependencies += "io.spray" %% "spray-json" % "1.2.6" % "optional",
    libraryDependencies += "org.specs2" %% "specs2" % "2.3.12" % "test",
    mainClass in (Compile, run) := Some("org.scalastuff.json.PerformanceTests")))
}
