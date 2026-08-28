package com.github.sean111.wowtoc.spec;

import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Current public TOC metadata and conditional-loading vocabulary. */
public final class TocSpec {
    public static final String[] LOCALES = {"enUS", "enGB", "frFR", "deDE", "esES", "esMX", "itIT", "ptBR",
            "ruRU", "koKR", "zhTW", "zhCN"};
    public static final String[] METADATA = {"Interface", "Title", "Notes", "Category", "Group", "IconTexture",
            "IconAtlas", "AddonCompartmentFunc", "AddonCompartmentFuncOnEnter", "AddonCompartmentFuncOnLeave",
            "LoadOnDemand", "Dependencies", "OptionalDeps", "LoadWith", "LoadManagers", "AllowLoadGameType",
            "OnlyBetaAndPTR", "DefaultState", "LoadSavedVariablesFirst", "SavedVariables",
            "SavedVariablesPerCharacter", "Author", "Version", "AllowAddOnTableAccess", "AllowLoad",
            "EscalateErrorDuringLoad", "LoadFirst", "SavedVariablesMachine", "UseSecureEnvironment", "Secure"};
    public static final String[] GAME_TYPES = {"standard", "mists", "cata", "wrath", "tbc", "vanilla",
            "plunderstorm", "wowhack", "mainline", "classic"};
    public static final String[] CONDITIONS = {"AllowLoad", "AllowLoadGameType", "AllowLoadTextLocale"};
    public static final String[] FILE_VARIABLES = {"Family", "Game", "TextLocale"};

    private static final Set<String> LOCALIZED_METADATA = Set.of("title", "notes", "category");
    private static final Set<String> BOOLEAN_METADATA = Set.of("loadondemand", "onlybetaandptr",
            "loadsavedvariablesfirst", "allowaddontableaccess", "escalateerrorduringload", "loadfirst",
            "usesecureenvironment", "secure");
    private static final Pattern CONDITION = Pattern.compile("\\s*\\[([A-Za-z]+)(?:\\s+([^\\]]*?))?\\]\\s*");
    private static final Pattern FILE_VARIABLE = Pattern.compile("\\[([A-Za-z]+)]");

    private TocSpec() {
    }

    public static boolean isMetadata(String name) {
        if (name == null || name.isBlank()) {
            return false;
        }
        String normalized = name.toLowerCase(Locale.ROOT);
        if (normalized.startsWith("x-") || normalized.startsWith("dep")) {
            return true;
        }
        int localeSeparator = normalized.lastIndexOf('-');
        if (localeSeparator > 0 && LOCALIZED_METADATA.contains(normalized.substring(0, localeSeparator))) {
            return isLocale(name.substring(localeSeparator + 1));
        }
        return Arrays.stream(METADATA).anyMatch(metadata -> metadata.equalsIgnoreCase(name))
                || normalized.equals("requireddep") || normalized.equals("requireddeps");
    }

    public static boolean isLocale(String value) {
        return Arrays.stream(LOCALES).anyMatch(locale -> locale.equalsIgnoreCase(value));
    }

    public static boolean isGameType(String value) {
        return Arrays.stream(GAME_TYPES).anyMatch(gameType -> gameType.equalsIgnoreCase(value));
    }

    public static boolean isBooleanMetadata(String name) {
        return BOOLEAN_METADATA.contains(name.toLowerCase(Locale.ROOT));
    }

    public static boolean isCondition(String name) {
        return Arrays.stream(CONDITIONS).anyMatch(condition -> condition.equalsIgnoreCase(name));
    }

    public static boolean isFileVariable(String name) {
        return Arrays.stream(FILE_VARIABLES).anyMatch(variable -> variable.equalsIgnoreCase(name));
    }

    public static String stripConditions(String line) {
        Matcher matcher = CONDITION.matcher(line);
        StringBuilder result = new StringBuilder();
        int previous = 0;
        while (matcher.find()) {
            if (isCondition(matcher.group(1))) {
                result.append(line, previous, matcher.start());
                previous = matcher.end();
            }
        }
        return result.append(line.substring(previous)).toString().trim();
    }

    public static Matcher conditions(String line) {
        return CONDITION.matcher(line);
    }

    public static Matcher fileVariables(String line) {
        return FILE_VARIABLE.matcher(line);
    }
}
