package com.github.sean111.wowtoc.spec;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TocSpecTest {
    @Test
    public void recognizesCurrentAndLocalizedMetadata() {
        assertTrue(TocSpec.isMetadata("AddonCompartmentFuncOnLeave"));
        assertTrue(TocSpec.isMetadata("Category-deDE"));
        assertTrue(TocSpec.isMetadata("X-Website"));
        assertTrue(TocSpec.isMetadata("Dependencies"));
        assertFalse(TocSpec.isMetadata("Category-xxXX"));
    }

    @Test
    public void removesOnlyKnownConditionsFromFileReferences() {
        assertEquals("Localization\\[TextLocale].lua",
                TocSpec.stripConditions("Localization\\[TextLocale].lua [AllowLoadTextLocale enUS, frFR]"));
        assertEquals("[Unknown]\\File.lua", TocSpec.stripConditions("[Unknown]\\File.lua"));
    }

    @Test
    public void recognizesCurrentConditionalValues() {
        assertTrue(TocSpec.isGameType("mainline"));
        assertTrue(TocSpec.isGameType("mists"));
        assertTrue(TocSpec.isLocale("enUS"));
        assertTrue(TocSpec.isFileVariable("Family"));
        assertFalse(TocSpec.isGameType("retail"));
    }

    @Test
    public void doesNotTreatConditionalClausesAsFileVariables() {
        assertFalse(TocSpec.fileVariables("SpellsLive.lua [AllowLoadGameType mainline, standard]").find());
        assertTrue(TocSpec.conditions("SpellsLive.lua [AllowLoadGameType mainline, standard]").find());
    }
}
