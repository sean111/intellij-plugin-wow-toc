# WoW TOC Modernization Plan

## Scope

- Target the latest IntelliJ Platform release line only, using Java 25.
- Keep support for IntelliJ-based IDEs that provide `com.intellij.modules.lang`.
- Translate all Chinese text, including documentation, Javadocs, source comments, and grammar comments.
- Migrate handwritten code and resources to the standard Gradle source layout. Keep generated PSI sources committed because Grammar-Kit's Gradle generator cannot support this plugin's mixin-based PSI model.

## Work

1. Move handwritten code and resources to `src/main`, provide an explicit lexer-generation task, and retain mixin-based generated sources until Grammar-Kit supports two-pass Gradle generation.
2. Add a Gradle 9 build using IntelliJ Platform Gradle Plugin 2.x, Java 25, packaging, testing, and verification tasks.
3. Replace obsolete platform APIs and verify the plugin against the target IDE.
4. Consolidate user-facing strings in an English resource bundle and translate all remaining documentation and comments.
5. Add Makefile and Justfile wrappers for common development, verification, and packaging workflows.
6. Add parser, inspection, reference, completion, formatting, and quick-fix tests.
7. Package the plugin and validate the distribution ZIP with Plugin Verifier and a sandbox IDE.

## Completion Criteria

- `make check` and `just check` complete successfully.
- `make package` and `just package` produce an installable ZIP under `build/distributions`.
- No Chinese text remains in tracked source or documentation files.
- Lexer generation is available through `make generate` or `just generate`; mixin-based generated sources remain committed as required by Grammar-Kit.
