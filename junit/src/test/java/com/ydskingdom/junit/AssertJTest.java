package com.ydskingdom.junit;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AssertJTest {

    //중간에 테스트가 실패해도 모든 테스트 후 결과를 알려줌
    @DisplayName("assertj Soft Assertions Test")
    @Test
    void soft_assertion_assertJ_test() {
        int number = 1;

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(number + number).as("test1").isEqualTo(2);
        softAssertions.assertThat(number * number).as("test2").isEqualTo(1);
        softAssertions.assertThat(number - number).as("test3").isEqualTo(0);
        softAssertions.assertAll();
    }
}
