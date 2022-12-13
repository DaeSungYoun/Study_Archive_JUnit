package com.ydskingdom.junit;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;

public class AssertJTest {

    @DisplayName("문자열 관련 테스트")
    @Test
    void stringTest() {
        String value = "Hello world! Nice to meet you";

        assertThat("Hello world! Nice to meet you")
                .isNotEmpty()
                .contains("Nice")
                .contains("world")
                .doesNotContain("aaaaaa")
                .startsWith("Hello")
                .endsWith("u")
                .isEqualTo(value);
    }

    @DisplayName("숫자 관련 테스트")
    @Test
    void numberTest() {
        assertThat(3.14d)
                .isPositive() //양수인지 체크
                .isGreaterThan(3) //3보다 큰지 체크
                .isLessThan(4) //4보다 작은지 체크
                .isEqualTo(3, offset(1d)) //오프셋 1 기준으로 3과 같은지 체크
                .isEqualTo(3.1, offset(0.1d)) // 오프셋 0.1 기준으로 3.1과 같은지 체크
                .isEqualTo(3.14); // 오프셋 없이 3.14와 같은지 체크
    }

    @DisplayName("assertThat().as() Test")
    @Test
    void testCase1() {
        int number = 1;

        assertThat(number + number).as("test1").isEqualTo(2);
        assertThat(number * number).as("test2").isEqualTo(1);
        assertThat(number - number).as("test3").isEqualTo(0);
    }

    //중간에 테스트가 실패해도 모든 테스트 후 결과를 알려줌
    @DisplayName("SoftAssertions Test")
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
