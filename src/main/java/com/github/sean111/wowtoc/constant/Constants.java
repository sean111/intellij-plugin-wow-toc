package com.github.sean111.wowtoc.constant;

import com.github.sean111.wowtoc.spec.TocSpec;

public final class Constants {
    public static final String DEMO_TEXT = "# This is a comment.\n"
            + "## Interface: 120100, 50504, 11509\n"
            + "## Title: WlkUI\n"
            + "\n"
            + "## Notes: WlkUI\n"
            + "ActionBar.lua\n"
            + "\n"
            + "Auction.lua [AllowLoadGameType mainline]\n"
            + "Localization\\[TextLocale].lua";

    public static final String[] TAG_NAMES = TocSpec.METADATA;
    public static final String[] LOCALIZATION = TocSpec.LOCALES;
    public static final String REGEX_FILE_NAME = ".*\\.([lL][uU][aA]|[xX][mM][lL])$";

    private Constants() {
    }
}
