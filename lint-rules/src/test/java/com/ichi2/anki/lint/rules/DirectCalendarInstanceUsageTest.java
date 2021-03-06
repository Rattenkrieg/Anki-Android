package com.ichi2.anki.lint.rules;

import org.junit.Test;

import static com.android.tools.lint.checks.infrastructure.TestFile.JavaTestFile.create;
import static com.android.tools.lint.checks.infrastructure.TestLintTask.lint;
import static org.junit.Assert.assertTrue;

public class DirectCalendarInstanceUsageTest {

    private final String stubCalendar = "                                   \n" +
            "package java.util;                                             \n" +
            "                                                               \n" +
            "public class Calendar {                                        \n" +
            "                                                               \n" +
            "    public static Calendar getInstance() {                     \n" +
            "       return null;                                            \n" +
            "    }                                                          \n" +
            "}                                                              \n";

    private final String javaFileToBeTested = "                             \n" +
            "package com.ichi2.anki.lint.rules;                             \n" +
            "                                                               \n" +
            "import java.util.Calendar;                                     \n" +
            "                                                               \n" +
            "public class TestJavaClass {                                   \n" +
            "                                                               \n" +
            "    public static void main(String[] args) {                   \n" +
            "        Calendar c = Calendar.getInstance();                   \n" +
            "        c.clear();                                             \n" +
            "    }                                                          \n" +
            "}                                                              \n";


    @Test
    public void showsErrorForInvalidUsage() {
        lint()
                .allowMissingSdk()
                .allowCompilationErrors()
                .files(create(stubCalendar), create(javaFileToBeTested))
                .issues(DirectCalendarInstanceUsage.ISSUE)
                .run()
                .expectErrorCount(1)
                .check(output -> {
                    assertTrue(output.contains(DirectCalendarInstanceUsage.ID));
                    assertTrue(output.contains(DirectCalendarInstanceUsage.DESCRIPTION));
                });


    }

}