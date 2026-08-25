.DEFAULT_GOAL := help

.PHONY: help generate build test check verify run package clean tasks

help:
	@$(MAKE) --no-print-directory -f Makefile help-text

help-text:
	@printf '%s\n' 'Targets: generate build test check verify run package clean tasks'

generate:
	./gradlew generateLexer

build:
	./gradlew build

test:
	./gradlew test

check:
	./gradlew check

verify:
	./gradlew verifyPlugin

run:
	./gradlew runIde

package:
	./gradlew buildPlugin

clean:
	./gradlew clean

tasks:
	./gradlew tasks
