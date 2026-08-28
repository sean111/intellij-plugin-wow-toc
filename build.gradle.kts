import org.jetbrains.intellij.platform.gradle.TestFrameworkType

plugins {
    java
    id("org.jetbrains.intellij.platform")
    id("org.jetbrains.intellij.platform.grammarkit")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

dependencies {
    testImplementation("junit:junit:4.13.2")

    intellijPlatform {
        intellijIdea("2026.2.0.1")
        testFramework(TestFrameworkType.Platform)
        pluginVerifier()
    }
}

sourceSets {
    main {
        java.srcDir("gen")
    }
}

intellijPlatform {
    pluginConfiguration {
        id.set("com.github.sean111.wowtoc")
        name.set("WoW TOC")
        version.set(provider { project.version.toString() })
        description.set("""
            <p>Language support for World of Warcraft add-on <code>.toc</code> files.</p>
            <ul>
              <li>Syntax highlighting and code style settings</li>
              <li>TOC metadata completion and validation</li>
              <li>File references, navigation, rename, and quick fixes</li>
              <li>Formatting, comments, and file templates</li>
            </ul>
        """.trimIndent())
        changeNotes.set("<h3>2.0.0</h3><p>Modernized for the latest IntelliJ Platform, with an English codebase and reproducible build.</p>")
        ideaVersion {
            sinceBuild.set("262")
        }
        vendor {
            name.set("sean111")
            email.set("sean111@gmail.com")
            url.set("https://github.com/sean111/intellij-plugin-wow-toc")
        }
    }
}

tasks {
    generateLexer {
        sourceFile.set(file("src/main/grammar/Toc.flex"))
        targetRootOutputDir.set(layout.projectDirectory.dir("gen"))
        pathToClass.set("com/github/sean111/wowtoc/lexer/TocLexer.java")
    }
    compileJava {
        options.release.set(25)
    }
    check {
        dependsOn(verifyPluginProjectConfiguration)
    }
}
