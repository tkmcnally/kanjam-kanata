name := "kanjam-kanata"

version := "1.0"

scalaVersion := "2.11.6"

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )

val appDependencies = Seq(
  "be.objectify"  %% "deadbolt-java"     % "2.4.0",
  "com.feth"      %% "play-authenticate" % "0.7.0-SNAPSHOT",
  "postgresql"    %  "postgresql"        % "9.1-901.jdbc4",
  cache,
  javaWs,
  javaJdbc,
  "org.webjars" % "bootstrap" % "3.2.0",
  "org.easytesting" % "fest-assert" % "1.4" % "test",
  "org.apache.httpcomponents" % "httpcore" % "4.4.1",
  "org.apache.httpcomponents" % "httpclient" % "4.5"
)

resolvers += Resolver.sonatypeRepo("snapshots")


lazy val `kanjam-kanata` = project.in(file("."))
  .enablePlugins(PlayJava, PlayEbean)
  .settings(
    libraryDependencies ++= appDependencies
  )
