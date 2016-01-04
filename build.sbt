name := "scala-commons"

organization := "org.lolczak"

version := "0.2.0"

scalaVersion := "2.11.7"

resolvers += "Tim Tennant's repo" at "http://dl.bintray.com/timt/repo/"

resolvers += "bintray/non" at "http://dl.bintray.com/non/maven"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.2" % "test",
  "org.scalacheck" %% "scalacheck" % "1.11.6" % "test",
  "org.scalaz" %% "scalaz-core" % "7.1.2",
  "org.apache.commons" % "commons-lang3" % "3.3.2"
)

credentials += Credentials(Path.userHome / ".nexus_credentials")

publishTo := {
  val nexus = "http://nexus.blocker.ra:8081/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "content/repositories/releases")
}

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { _ => false }