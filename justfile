default:
  @just --list

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
