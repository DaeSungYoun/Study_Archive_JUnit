package com.ydskingdom.junit;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Repeated {

    private static final Logger LOGGER = LoggerFactory.getLogger(Repeated.class);

    @RepeatedTest(3)
    void repeatedTest(TestInfo testInfo) {
        LOGGER.debug("Executing repeated test");
        assertThat(Math.addExact(1, 1)).isEqualTo(2);
    }

    @BeforeEach
    void beforeEachTest() {
        LOGGER.debug("Before Each Test");
    }

    @AfterEach
    void afterEachTest() {
        LOGGER.debug("After Each Test");
        LOGGER.debug("=====================");
    }

    @RepeatedTest(value = 3, name = RepeatedTest.LONG_DISPLAY_NAME)
    void repeatedTestWithLongName() {
        LOGGER.debug("Executing repeated test with long name");
        assertThat(Math.addExact(1, 1)).isEqualTo(2);
    }

    @RepeatedTest(value = 3, name = RepeatedTest.SHORT_DISPLAY_NAME)
    void repeatedTestWithShortName() {
        LOGGER.debug("Executing repeated test with short name");
        assertThat(Math.addExact(1, 1)).isEqualTo(2);
    }

    @RepeatedTest(value = 3, name = "Custom name {currentRepetition}/{totalRepetitions}")
    void repeatedTestWithCustomDisplayName(TestInfo testInfo) {
        assertThat(Math.addExact(1, 1)).isEqualTo(2);
    }

    @RepeatedTest(3)
    void repeatedTestWithRepetitionInfo(RepetitionInfo repetitionInfo) {
        LOGGER.debug("Repetition # {}", repetitionInfo.getCurrentRepetition());
        assertThat(repetitionInfo.getTotalRepetitions()).isEqualTo(3);
    }
}
