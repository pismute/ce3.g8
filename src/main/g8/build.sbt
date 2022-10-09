ThisBuild / organization := "com.example"
ThisBuild / scalaVersion := $if(scala3.truthy)$"3.0.0"$else$"2.13.5"$endif$

lazy val root = (project in file(".")).settings(
  name := "$name;format="norm"$",
  tpolecatDevModeOptions ~= { opts =>
    opts.filterNot(Set(ScalacOptions.privateWarnUnusedImports))
  },
  libraryDependencies ++= Seq(
    $if(scala3.truthy)$$else$
    compilerPlugin(("org.typelevel" %% "kind-projector" % "0.13.2").cross(CrossVersion.full)),
    // better monadic for compiler plugin as suggested by documentation
    compilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1"),$endif$

    // "core" module - IO, IOApp, schedulers
    // This pulls in the kernel and std modules automatically.
    "org.typelevel" %% "cats-effect" % "3.3.12",
    // concurrency abstractions and primitives (Concurrent, Sync, Async etc.)
    "org.typelevel" %% "cats-effect-kernel" % "3.3.12",
    // standard "effect" library (Queues, Console, Random etc.)
    "org.typelevel" %% "cats-effect-std" % "3.3.12",
    "org.typelevel" %% "cats-mtl" % "1.2.0"$if(testlib-use-cats-effect-testing-specs2.truthy)$,
    "org.typelevel" %% "cats-effect-testing-specs2" % "1.4.0" % Test$else$$endif$$if(testlib-use-munit-cats-effect-3.truthy)$,
    "org.typelevel" %% "munit-cats-effect-3" % "1.0.7" % Test$else$$endif$

  )
)
