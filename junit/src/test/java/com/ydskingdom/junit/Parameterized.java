package com.ydskingdom.junit;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.time.Month;
import java.util.EnumSet;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class Parameterized {

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, Integer.MAX_VALUE, Integer.MIN_VALUE})
    @DisplayName("ValueSource int Test")
    void intValueSourceTest(int number) {
        assertThatCode(() -> System.out.println(number));
    }

    @ParameterizedTest
    @ValueSource(strings = {"hi", "hello", "", " ", "!@#$%"})
    @DisplayName("ValueSource string Test")
    void stringValueSourceTest(String value) {
        assertThatCode(() -> System.out.println("[" + value + "]"));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("NullSource string Test")
    void nullTest(String value) {
        assertThat(value).isNull();
    }

    @ParameterizedTest
    @EmptySource
    @DisplayName("EmptySource string Test")
    void emptyTest(String value) {
        assertThat(value).isEmpty();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("NullAndEmptySource string Test")
    void nullAndEmptyTest(String value) {
        assertThat(Strings.isBlank(value)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"hi", "hello", "", " ", "!@#$%"})
    @NullAndEmptySource
    void nullSourceAndNullAndEmptySourceTest(String value) {
        System.out.println("[" + value + "]");
    }

    @ParameterizedTest
    @EnumSource(Month.class) // passing all 12 months
    @DisplayName("Month.class들 모든 항목 사용")
    void getValueForAMonth_IsAlwaysBetweenOneAndTwelve(Month month) {
        int monthNumber = month.getValue();
        assertThat(monthNumber >= 1 && monthNumber <= 12).isTrue();
    }

    @ParameterizedTest
    @EnumSource(value = Month.class, names = {"APRIL", "JUNE", "SEPTEMBER", "NOVEMBER"})
    @DisplayName("Month.class중에서 지정한 항목들만 사용")
    void someMonths_Are30DaysLong(Month month) {
        final boolean isALeapYear = false;
        assertThat(month.length(isALeapYear)).isEqualTo(30);
    }

    @ParameterizedTest
    @EnumSource(value = Month.class, names = {"APRIL", "JUNE", "SEPTEMBER", "NOVEMBER", "FEBRUARY"}, mode = EnumSource.Mode.EXCLUDE)
    @DisplayName("Month.class중에서 지정한 항목들을 제외하고 사용")
    void exceptFourMonths_OthersAre31DaysLong(Month month) {
        final boolean isALeapYear = false;
        assertThat(month.length(isALeapYear)).isEqualTo(31);
    }

    @ParameterizedTest
    @EnumSource(value = Month.class, names = ".+BER", mode = EnumSource.Mode.MATCH_ANY)
    @DisplayName("Month.class중에서 조건에 맞는 항목들만 사용")
    void fourMonths_AreEndingWithBer(Month month) {
        EnumSet<Month> months = EnumSet.of(Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER);
        assertThat(months).contains(month);
    }

    @ParameterizedTest
    @CsvSource({"test,TEST", "tEst,TEST", "Java,JAVA"})
    @DisplayName("CsvSource default test")
    void toUpperCase_ShouldGenerateTheExpectedUppercaseValue(String input, String expected) {
        assertThat(input.toUpperCase()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"test:test", "tEst:test", "Java:java"}, delimiter = ':')
    @DisplayName("CsvSource delimiter test")
    void toLowerCase_ShouldGenerateTheExpectedLowercaseValue(String input, String expected) {
        assertThat(input.toLowerCase()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    @DisplayName("CsvFileSource test")
    @Disabled
    void toUpperCase_ShouldGenerateTheExpectedUppercaseValueCSVFile(String input, String expected) {
        assertThat(input.toUpperCase()).isEqualTo(expected);
    }

    //@MethodSource에 입력한 메소드명을 찾아서 값을 가져옴
    @ParameterizedTest
    @MethodSource("provideStringsForIsBlank")
    void isBlank_ShouldReturnTrueForNullOrBlankStrings(String input, boolean expected) {
        assertThat(Strings.isBlank(input)).isEqualTo(expected);
    }

    private static Stream<Arguments> provideStringsForIsBlank() {
        return Stream.of(
                Arguments.of(null, true),
                Arguments.of("", true),
                Arguments.of("  ", true),
                Arguments.of("not blank", false)
        );
    }

    //@MethodSource에 메소드명을 입력하지 않으면 기본적으로 동일한 메소드를 찾아서 값을 가져옴
    @ParameterizedTest
    @MethodSource
    void isBlank_ShouldReturnTrueForNullOrBlankStringsOneArgument(String input) {
        assertThat(Strings.isBlank(input)).isTrue();
    }

    private static Stream<String> isBlank_ShouldReturnTrueForNullOrBlankStringsOneArgument() {
        return Stream.of(null, "", "  ");
    }

    //다른 클래스의 메소드를 찾아서 값을 가져옴
    @ParameterizedTest
    @MethodSource("com.baeldung.parameterized.StringParams#blankStrings")
    @Disabled
    void isBlank_ShouldReturnTrueForNullOrBlankStringsExternalSource(String input) {
        assertThat(Strings.isBlank(input)).isTrue();
    }
}
