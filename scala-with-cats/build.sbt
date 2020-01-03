name := "domain-cats"

version := "0.1"

scalaVersion := "2.13.1"

scalacOptions += "-Ypartial-unification"

libraryDependencies += "org.typelevel" %% "cats-core" % "2.0.0"
libraryDependencies += "org.typelevel" %% "cats-effect" % "2.0.0"