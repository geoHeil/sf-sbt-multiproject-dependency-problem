name := "sparkMulti"
organization in ThisBuild := "com.geoheil"
scalaVersion in ThisBuild := "2.11.12"

// PROJECTS

lazy val global = project
  .in(file("."))
  .settings(
    settings,
    libraryDependencies ++= commonDependencies
  )
  .aggregate(
    common
  )
  .dependsOn(
    common
  )

lazy val common = project
  .settings(
    name := "common",
    settings,
    libraryDependencies ++= commonDependencies
  )

// DEPENDENCIES

lazy val dependencies =
  new {
    val sparkV    = "2.3.0"

    val sparkBase           = "org.apache.spark" %% "spark-core"                 % sparkV % "provided"
    val sparkSql            = "org.apache.spark" %% "spark-sql"                  % sparkV % "provided"
    val sparkHive           = "org.apache.spark" %% "spark-hive"                 % sparkV % "provided"
  }

lazy val commonDependencies = Seq(
  dependencies.sparkBase,
  dependencies.sparkHive,
  dependencies.sparkSql
)

// SETTINGS
lazy val settings = commonSettings

lazy val commonSettings = Seq(
  //The default SBT testing java options are too small to support running many of the tests
  // due to the need to launch Spark in local mode.
  javacOptions ++= Seq("-source", "1.8", "-target", "1.8"),
  javaOptions ++= Seq("-Xms512M", "-Xmx2048M", "-XX:MaxPermSize=2048M", "-XX:+CMSClassUnloadingEnabled"),
  scalacOptions ++= Seq(
    "-target:jvm-1.8",
    "-encoding",
    "UTF-8",
    "-feature",
    "-unchecked",
    "-deprecation",
    "-Xfuture",
    "-Xlint:missing-interpolator",
    "-Yno-adapted-args",
    "-Ywarn-dead-code",
    "-Ywarn-numeric-widen",
    "-Ywarn-value-discard",
    "-Ywarn-dead-code",
    "-Ywarn-unused"
  ),
  parallelExecution in Test := false,
  resolvers ++= Seq(
    "Artima Maven Repository" at "http://repo.artima.com/releases",
    "hortonworks public" at "http://nexus-private.hortonworks.com/nexus/content/groups/public",
    "sonatype-releases" at "https://oss.sonatype.org/content/repositories/releases/",
    "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
    "Second Typesafe repo" at "http://repo.typesafe.com/typesafe/maven-releases/",
    Resolver.sonatypeRepo("public")
  ),
  fork := true,
  run in Compile := Defaults
    .runTask(fullClasspath in Compile, mainClass.in(Compile, run), runner.in(Compile, run))
    .evaluated
)
