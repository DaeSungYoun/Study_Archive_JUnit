package com.ydskingdom.junit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class AssertionTest {

    @DisplayName("assertAll Test")
    @Test
    void assertAll_Test() {
        int number = 1;

        assertAll(
                () -> assertThat(number + number).as("test1").isEqualTo(1),
                () -> assertThat(number * number).as("test2").isEqualTo(1),
                () -> assertThat(number - number).as("test3").isEqualTo(1)

        );
    }


}
