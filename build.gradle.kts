import java.io.ByteArrayOutputStream

plugins {
    application
    kotlin("jvm") version "1.8.10"
    alias(libs.plugins.multiJvmTesting) // Pre-configures the Java toolchains
    alias(libs.plugins.taskTree) // Helps debugging dependencies among gradle tasks
    scala
}

repositories {
    mavenCentral()
}

dependencies {
    // Check the catalog at gradle/libs.versions.gradle
    implementation(libs.bundles.alchemist)
    implementation(libs.bundles.scalacache)

    implementation("it.unibo.scafi:macro-swarm-core_2.13:1.5.0")
    implementation("com.lihaoyi:upickle_2.13:3.1.3")
}

val projectJavaVersion: Int = 17
multiJvm {
    jvmVersionForCompilation.set(projectJavaVersion)
}

// Heap size estimation for batches
val maxHeap: Long? by project
val heap: Long = maxHeap ?: if (System.getProperty("os.name").toLowerCase().contains("linux")) {
    ByteArrayOutputStream().use { output ->
        exec {
            executable = "bash"
            args = listOf("-c", "cat /proc/meminfo | grep MemAvailable | grep -o '[0-9]*'")
            standardOutput = output
        }
        output.toString().trim().toLong() / 1024
    }.also { println("Detected ${it}MB RAM available.") } * 9 / 10
} else {
    // Guess 16GB RAM of which 2 used by the OS
    14 * 1024L
}
val taskSizeFromProject: Int? by project
val taskSize = taskSizeFromProject ?: 512
val threadCount = maxOf(1, minOf(Runtime.getRuntime().availableProcessors(), heap.toInt() / taskSize))

val alchemistGroup = "Run Alchemist"
/*
 * This task is used to run all experiments in sequence
 */
val runAllGraphic by tasks.register<DefaultTask>("runAllGraphic") {
    group = alchemistGroup
    description = "Launches all simulations with the graphic subsystem enabled"
}
val runAllBatch by tasks.register<DefaultTask>("runAllBatch") {
    group = alchemistGroup
    description = "Launches all experiments"
}
val runAllEval by tasks.register<DefaultTask>("runAllEval") {
    group = alchemistGroup
    description = "Launches all experiments (eval)"
}
/*
 * Scan the folder with the simulation files, and create a task for each one of them.
 */
File(rootProject.rootDir.path + "/src/main/yaml").listFiles()
    ?.filter { it.extension == "yml" }
    ?.sortedBy { it.nameWithoutExtension }
    ?.forEach {
        fun basetask(name: String, additionalConfiguration: JavaExec.() -> Unit = {}) = tasks.register<JavaExec>(name) {
            group = alchemistGroup
            description = "Launches graphic simulation ${it.nameWithoutExtension}"
            mainClass.set("it.unibo.alchemist.Alchemist")
            classpath = sourceSets["main"].runtimeClasspath
            args("run", it.absolutePath)
            javaLauncher.set(
                javaToolchains.launcherFor {
                    languageVersion.set(JavaLanguageVersion.of(projectJavaVersion))
                },
            )
            if (System.getenv("CI") == "true") {
                args("--override", "terminate: { type: AfterTime, parameters: [2] } ")
                args("--override", "export: { type: CSVExporter, parameters: { fileNameRoot: noname, interval: 1.0, exportPath: nopath }, data: [time] }")
            } else {
                this.additionalConfiguration()
            }
            this.additionalConfiguration()
        }
        val capitalizedName = it.nameWithoutExtension.capitalize()
        val graphic by basetask("run${capitalizedName}Graphic") {
            args(
                "--override",
                "monitors: { type: SwingGUI, parameters: { graphics: effects/effect-simulation.json } }",
                "--override",
                "launcher: { parameters: { batch: [], autoStart: false } }",
            )
        }
        runAllGraphic.dependsOn(graphic)
        val batch by basetask("run${capitalizedName}Batch") {
            description = "Launches batch experiments for $capitalizedName"
            maxHeapSize = "${minOf(heap.toInt(), Runtime.getRuntime().availableProcessors() * taskSize)}m"
            File("data").mkdirs()
            args("--override",
                """
                    launcher: {
                        parameters: {
                            batch: [ random, fail_probability ],
                            showProgress: true,
                            autoStart: true,
                            parallelism: $threadCount,
                        }
                    }
                """.trimIndent()
            )
        }
        if(capitalizedName.contains("Eval")) {
            runAllEval.dependsOn(batch)
        }
        runAllBatch.dependsOn(batch)
    }

