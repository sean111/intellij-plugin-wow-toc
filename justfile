gradle := 'GRADLE_USER_HOME=.gradle-user-home ./gradlew'

default:
  @just --list

generate:
  {{gradle}} generateLexer

build:
  {{gradle}} build

test:
  {{gradle}} test

check:
  {{gradle}} check

verify:
  {{gradle}} verifyPlugin

run:
  {{gradle}} runIde

package:
  {{gradle}} buildPlugin

clean:
  {{gradle}} clean

tasks:
  {{gradle}} tasks
