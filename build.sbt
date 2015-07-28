name := "kanjam-kanata"

version := "1.0"

lazy val `kanjam-kanata` = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq( javaJdbc , javaEbean , cache , javaWs )

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )

libraryDependencies += "org.apache.httpcomponents" % "httpclient" % "4.5"

libraryDependencies += "org.apache.httpcomponents" % "httpcore" % "4.4.1"
