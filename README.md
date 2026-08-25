# WoW TOC

Language support for World of Warcraft add-on `.toc` files in IntelliJ-based IDEs.

## Features

- Create `.toc` files from a template
- Syntax highlighting and configurable color settings
- Tag-name and referenced-file validation with quick fixes
- Official tag-name and referenced-file completion
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

## Installation

Search for "WoW TOC" in JetBrains Marketplace, or download it from the [plugin page](https://plugins.jetbrains.com/plugin/13375-wow-toc/) and install it from disk.
