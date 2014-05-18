import sbt._
import Keys._
import com.typesafe.sbteclipse.plugin.EclipsePlugin.EclipseKeys
import com.typesafe.sbteclipse.plugin.EclipsePlugin.EclipseCreateSrc

object JsonPullParserBuild extends Build {

  def defaultSettings =
    Project.defaultSettings ++
      Seq(
        sbtPlugin := false, 
        organization := "net.scalaleafs",
        version := "1.0.0-SNAPSHOT",
        scalaVersion := "2.10.4",
        crossScalaVersions := Seq("2.10.4", "2.11.0"),
        scalacOptions ++= Seq("-deprecation", "-unchecked", "-encoding", "utf8", "-feature"),
        //scalacOptions ++= Seq("-language:implicitConversions", "-language:postfixOps", "-language:reflectiveCall", "-language:higherKinds", "-language:existentials", "-language:reflectiveCalls"),
        EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource,
        EclipseKeys.withSource := true)
          
  def publishSettings = Seq(
    publishMavenStyle := true,
    publishArtifact in Test := false,
    pomIncludeRepository := { _ => false },
    licenses := Seq("The Apache Software Licence, Version 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
    homepage := Some(url("http://scalaleafs.net")),
    publishTo := {
      if (version.value.trim.endsWith("SNAPSHOT"))
        Some("snapshots" at "https://oss.sonatype.org/content/repositories/snapshots")
      else
        Some("releases"  at "https://oss.sonatype.org/service/local/staging/deploy/maven2")},
    pomExtra := 
      <scm>
        <connection>scm:git:git@github.com:scalastuff/scalaleafs.git</connection>
        <url>https://github.com/scalastuff/scalaleafs</url>
      </scm>
      <developers>
        <developer>
          <id>ruudditerwich</id>
          <name>Ruud Diterwich</name>
          <url>http://ruud.diterwich.com</url>
        </developer>
      </developers>) 

    
  val jsonPullParser = Project(id = "json-pull-parser", base = file("."), settings = defaultSettings ++ publishSettings ++ Seq(
    libraryDependencies += "io.spray" %% "spray-json" % "1.2.6" % "optional" withSources(),
    libraryDependencies += "org.specs2" %% "specs2" % "2.3.12" % "test" withSources(),
    resolvers += "spray repo" at "http://repo.spray.io"))
}
