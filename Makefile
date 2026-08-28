.DEFAULT_GOAL := help

GRADLE := GRADLE_USER_HOME=.gradle-user-home ./gradlew

.PHONY: help generate build test check verify run package clean tasks

help:
	@$(MAKE) --no-print-directory -f Makefile help-text

help-text:
	@printf '%s\n' 'Targets: generate build test check verify run package clean tasks'

generate:
	$(GRADLE) generateLexer

build:
	$(GRADLE) build

test:
	$(GRADLE) test

check:
	$(GRADLE) check

verify:
	$(GRADLE) verifyPlugin

run:
	$(GRADLE) runIde

package:
	$(GRADLE) buildPlugin

clean:
	$(GRADLE) clean

tasks:
	$(GRADLE) tasks
