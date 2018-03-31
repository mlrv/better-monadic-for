name := "better-monadic-for"
organization := "com.olegpy"
version := "0.1.0"
licenses += ("MIT", url("http://opensource.org/licenses/MIT"))
homepage := Some(url("http://github.com/oleg-py/better-monadic-for"))

scalaVersion := "2.12.5"
crossScalaVersions := Seq("2.11.12", "2.12.5")
libraryDependencies ++= Seq(
  scalaOrganization.value % "scala-compiler" % scalaVersion.value,
  "com.lihaoyi" %% "utest" % "0.5.3" % Test,
  "io.monix" %% "monix-eval" % "3.0.0-RC1" % Test,
  "org.ensime" %% "pcplod" % "1.2.1" % Test,
)

// WORKAROUND https://github.com/ensime/pcplod/issues/12
fork in Test := true

javaOptions in Test ++= Seq(
  s"""-Dpcplod.settings=${(scalacOptions in Test).value.filterNot(_.contains(",")).mkString(",")}""",
  s"""-Dpcplod.classpath=${(fullClasspath in Test).value.map(_.data).mkString(",")}"""
)

scalacOptions in Test ++= {
  val jar = (packageBin in Compile).value
  Seq(s"-Xplugin:${jar.getAbsolutePath}", s"-Jdummy=${jar.lastModified}") // ensures recompile
}


//scalacOptions in Test += "-Xfatal-warnings"
testFrameworks += new TestFramework("utest.runner.Framework")