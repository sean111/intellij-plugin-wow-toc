# WoW TOC

Language support for World of Warcraft add-on `.toc` files in IntelliJ-based IDEs.

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

```sh
make check     # or: just check
make package   # or: just package
make run       # or: just run
```

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
