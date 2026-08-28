# WoW TOC

Language support for World of Warcraft add-on `.toc` files in IntelliJ-based IDEs.

WARNING: The updates to this repo are primarily done using AI for me to test different models and harnesses. I haven't touched Java in over 20 years and don't have the time atm to properly get up to date. 

## Features

- Create `.toc` files from a template
- Syntax highlighting and configurable color settings
- Tag-name and referenced-file validation with quick fixes
- Official tag-name and referenced-file completion
- Current Retail and Classic metadata, comma-separated interface versions, conditional directives, and file variables
- Navigate to and rename referenced files
- Find usages, formatting, code style settings, and comments

## Development

Requires Java 25. The Gradle wrapper downloads it automatically when needed.

Both task runners store Gradle's cache in `.gradle-user-home`.

### Make Commands

| Command              | Description                                                                                                    |
|----------------------|----------------------------------------------------------------------------------------------------------------|
| `make` / `make help` | List the available Makefile targets.                                                                           |
| `make generate`      | Generate the TOC lexer from `src/main/grammar/Toc.flex`.                                                       |
| `make build`         | Compile the plugin, run its tests, and perform Gradle's standard verification lifecycle.                       |
| `make test`          | Run the automated test suite.                                                                                  |
| `make check`         | Run the standard verification lifecycle, including tests and IntelliJ plugin-project configuration validation. |
| `make verify`        | Run JetBrains Plugin Verifier against the built plugin.                                                        |
| `make run`           | Launch a sandbox IntelliJ IDEA instance with the plugin installed.                                             |
| `make package`       | Build the distributable plugin ZIP.                                                                            |
| `make clean`         | Remove Gradle build outputs.                                                                                   |
| `make tasks`         | Display all Gradle tasks, including tasks not wrapped by the Makefile.                                         |

### Just Commands

| Command         | Description                                                                                                    |
|-----------------|----------------------------------------------------------------------------------------------------------------|
| `just`          | List the available Justfile recipes.                                                                           |
| `just generate` | Generate the TOC lexer from `src/main/grammar/Toc.flex`.                                                       |
| `just build`    | Compile the plugin, run its tests, and perform Gradle's standard verification lifecycle.                       |
| `just test`     | Run the automated test suite.                                                                                  |
| `just check`    | Run the standard verification lifecycle, including tests and IntelliJ plugin-project configuration validation. |
| `just verify`   | Run JetBrains Plugin Verifier against the built plugin.                                                        |
| `just run`      | Launch a sandbox IntelliJ IDEA instance with the plugin installed.                                             |
| `just package`  | Build the distributable plugin ZIP.                                                                            |
| `just clean`    | Remove Gradle build outputs.                                                                                   |
| `just tasks`    | Display all Gradle tasks, including tasks not wrapped by the Justfile.                                         |

The packaged plugin ZIP is written to `build/distributions`.

## TOC Specification

The plugin tracks the public TOC format documented by [Warcraft Wiki](https://warcraft.wiki.gg/wiki/TOC_format), including
current display, loading, saved-variable, addon-compartment, and informational metadata. It recognizes localized `Title`,
`Notes`, and `Category` metadata, `[AllowLoad ...]`, `[AllowLoadGameType ...]`, and `[AllowLoadTextLocale ...]` conditions,
and `[Family]`, `[Game]`, and `[TextLocale]` variables in file entries.

File navigation and missing-file quick fixes apply to static Lua and XML paths. Variable-expanded paths are accepted without
an unresolved-file error because their concrete target depends on the active WoW client.

## Installation

Search for "WoW TOC" in JetBrains Marketplace, or download it from the [plugin page](https://plugins.jetbrains.com/plugin/13375-wow-toc/) and install it from disk.
