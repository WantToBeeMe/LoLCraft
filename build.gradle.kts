import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "1.8.0" // kotlin!!

    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("xyz.jpenilla.resource-factory-bukkit-convention") version "1.2.0"

    // if you are using libraries, but they return errors when loading ingame, you may want to use this
    // id("com.github.johnrengelman.shadow") version "7.1.2"
    // and if you do, also build your jars like this ./gradle clean build shadowJar
}

group = "me.wanttobeeme"
version = "1.0"

bukkitPluginYaml  {
    // read more about this way of setting up the plugin.yml here:
    // https://github.com/jpenilla/resource-factory/tree/master

    main.set("me.wanttobee.lolcraft.MinecraftPlugin")
    apiVersion.set("1.21")

    // name.set("Template") // inherits from settings.gradle, but you can override it. same goes for version
    authors.add("WantToBeeMe")
    description.set("A plugin that adds the game League of Legends in to minecraft")

    libraries.add("org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.0.20") // kotlin !!

    commands {
        register("lol") {
            permission.set("admin.permission")
            usage.set("/lol")
            description.set("League of Legends plugin commands")
        }

        register("mylol") {
            usage.set("/mylol")
            description.set("League of Legends plugin commands")
        }
        // register("helloWorld") {
        //     permission.set("admin.permission")
        //     usage.set("/byeWorld reason")
        //     aliases.add("bw")
        //     aliases.add("bye")
        //     description.set("bye world command")
        // }
    }
}

// Ensures that both compileJava and compileKotlin tasks will be using Java 1.8,
// aligning Java versions with Kotlin. (because kotlin was automatically be 1.8, while java stayed 1.7 still)
java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
    // mavenLocal()
    maven {
        url = uri("https://jitpack.io") // Use JitPack as a resolver for GitHub releases
    }

    maven {
        name = "spigotmc-repo"
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        // here you can find the latest's spigot versions
        // https://hub.spigotmc.org/nexus/content/repositories/snapshots/org/spigotmc/spigot-api/
    }
}

dependencies {
    // compileOnly() will only be used while compiling the jar but will not be included in the jar (for example the spigot api is not needed in the jar, de server already has that implemented)
    // inplementation() will also compile, but also include it in the jar you are creating
    implementation("com.github.WantToBeeMe:CommandTree:2.213.0")
    implementation("com.github.WantToBeeMe:EverythingItems:3.213.1")

    compileOnly("org.spigotmc:spigot-api:1.21.3-R0.1-SNAPSHOT")
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.0.20") // kotlin!!
}

// Configures a task to process resources, such as a plugin.yml file,
// with version information for the project.
tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
}

// // this piece of code makes it so when building a jar with ./gradle clean build shadowJar
// // that the 2 jars you will get are ProjectName.jar and ProjectName-1.0.jar
// // here the ProjectName.jar is the correct one which you should be using
tasks.withType<ShadowJar> {
    archiveClassifier.set("")
    archiveVersion.set("")
    archiveBaseName.set(project.name)
}

