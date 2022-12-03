## 목차

1. [@Test](#@Test)
2. [@BeforeEach, @BeforeAll](#@BeforeEach,-@BeforeAll)
3. [@AfterEach, @AfterAll](#@AfterEach,-@AfterAll)
4. [@Nested](#@Nested)
5. [@Timeout](#@Timeout)
6. [@Disabled](#@Disabled)
7. [@ParameterizedTest](#ParameterizedTest)
8. [@RepeatedTest](#RepeatedTest)


## @Test
 - 테스트 메서드임을 나타냄
## @BeforeEach, @BeforeAll
 - @BeforeEach : @Test를 실행하기전에 매번 실행
 - @BeforeAll : 클래스 내에 있는 모든 테스트를 실행하기 전 딱 한번만 실행
    - `static`으로 선언 필요
## @AfterEach, @AfterAll
 - @AfterEach : @Test 실행 후 매번 실행
 - @AfterAll : 클래스 내에 있는 모든 테스트들이 실행된 후 딱 한번만 실행
    - `static`으로 선언 필요
## @Nested
 - [https://junit.org/junit5/docs/current/user-guide/#writing-tests-nested](https://junit.org/junit5/docs/current/user-guide/#writing-tests-nested)
 - 테스트 코드를 묶어주는 기능
    ```java
    public class NestedTest {
    
        @Nested
        class TestGroupA{
            @Test
            void testSuccess() {}
    
            @Test
            void testFail() {}
        }
    
        @Nested
        class TestGroupB{
            @Test
            void testSuccess() {}
    
            @Test
            void testFail() {}
        }
    }
    ```
    
    ![Untitled](https://user-images.githubusercontent.com/38457303/204723836-8b57c098-8181-49ae-9921-6a9016e9004b.png)
    
## @Timeout
 - assertTimeout(Duration timeout, Executable executable)
    - 단점 : 제한시간이 지나도 테스트가 끝날때까지 기다려야함
 - assertTimeoutPreemptively(Duration timeout, Executable executable)
    - assertTimeout()과 다르게 제한시간이 지나면 바로 실패
    
    ```java
    @BeforeEach
    @Timeout(2)
    void setUp() {
        // fails if execution time exceeds 2 seconds
    }
    
    @Test
    @Timeout(3)
    void someTest() {
            TimeUnit.SECONDS.sleep(5); //실패
            TimeUnit.SECONDS.sleep(2); //실패
            TimeUnit.SECONDS.sleep(1); //성공
    }
    ```
    
    ```java
    @Timeout(3)
    public class TimeoutTests {
        @Test
        void testMethodOne() throws InterruptedException {
            TimeUnit.SECONDS.sleep(5);
        }
        @Test
        @Timeout(5)
        void testMethodTwo() throws InterruptedException {
            TimeUnit.SECONDS.sleep(4);
        }
    }
    ```
    
    ```java
    @Test
    void testGetValue() throws InterruptedException {
        Assertions.assertTimeout(Duration.ofSeconds(3), () -> {
                    Thread.sleep(3);
        });
    
            Assertions.assertTimeoutPreemptively(Duration.ofSeconds(3), () -> {
                    Thread.sleep(3);
        });
    }
    ```
    
## @Disabled
 - 테스트를 실행하지 않음

## @ParameterizedTest
 - https://www.baeldung.com/parameterized-tests-junit-5#first-impression
 - Dependencies
    ```java
    testImplementation group: 'org.junit.jupiter', name: 'junit-jupiter-params', version: '5.9.0'
    ```
    ```xml
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-params</artifactId>
        <version>5.8.1</version>
        <scope>test</scope>
    </dependency>
    ```
 - @ValueSource
    ```java
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, Integer.MAX_VALUE, Integer.MIN_VALUE})
    void valueSourceIntTest(int number) {
        assertThatCode(() -> {
            System.out.println(number);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"hi", "hello", "", " ", "!@#$%"})
    void valueSourceStringTest(String value) {
        assertThatCode(() -> {
            System.out.println(value);
        });
    }
    ```
 - @NullSource
    - null 값 한번 들어옴
    - primitive 타입은 null 값을 허용하지 않으므로 primitive 인수에는 @NullSource를 사용할 수 없음
        ```java
        @ParameterizedTest
        @NullSource
        void nullTest(String value) {
            assertThat(value).isNull();
        }
        ```
 - @EmptySource
    - empty 값 한번 들어옴
        ```java
        @ParameterizedTest
        @EmptySource
        void emptyTest(String value) {
            assertThat(value).isEmpty();
        }
        ```
 - @NullAndEmptySource
    - null, empty 각각 들어옴
        ```java
        @ParameterizedTest
        @NullAndEmptySource
        void nullAndEmptyTest(String value) {
            assertThat(true).isEqualTo(Strings.isBlank(value));
        }
        ```
 - @ValueSource + @NullAndEmptySource
    - @ValueSource, @NullSource, @EmptySource, @NullAndEmptySource 결합하여 사용 가능
        ```java
        @ParameterizedTest
        @ValueSource(strings = {"hi", "hello", "", " ", "!@#$%"})
        @NullAndEmptySource
        void Test(String value) {
            System.out.println("[" + value + "]");
        }
        ```
 - @EnumSource
    ```java
    @ParameterizedTest
    @EnumSource(Month.class)
    @DisplayName("Month.class들 모든 항목 사용")
    void getValueForAMonth_IsAlwaysBetweenOneAndTwelve(Month month) {
        int monthNumber = month.getValue();
        assertThat(true).isEqualTo(monthNumber >= 1 && monthNumber <= 12);
    }

    @ParameterizedTest
    @EnumSource(value = Month.class, names = {"APRIL", "JUNE", "SEPTEMBER", "NOVEMBER"})
    @DisplayName("Month.class중에서 지정한 항목들만 사용")
    void someMonths_Are30DaysLong(Month month) {
        final boolean isALeapYear = false;
        assertThat(30).isEqualTo(month.length(isALeapYear));
    }

    @ParameterizedTest
    @EnumSource(value = Month.class, names = {"APRIL", "JUNE", "SEPTEMBER", "NOVEMBER", "FEBRUARY"}, mode = EnumSource.Mode.EXCLUDE)
    @DisplayName("Month.class중에서 지정한 항목들을 제외하고 사용")
    void exceptFourMonths_OthersAre31DaysLong(Month month) {
        final boolean isALeapYear = false;
        assertThat(31).isEqualTo(month.length(isALeapYear));
    }

    @ParameterizedTest
    @EnumSource(value = Month.class, names = ".+BER", mode = EnumSource.Mode.MATCH_ANY)
    @DisplayName("Month.class중에서 조건에 맞는 항목들만 사용")
    void fourMonths_AreEndingWithBer(Month month) {
        EnumSet<Month> months = EnumSet.of(Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER);
        assertThat(months).contains(month);
    }
    ```

 - @CsvSource
    ```java
    @ParameterizedTest
    @CsvSource({"test,TEST", "tEst,TEST", "Java,JAVA"})
    @DisplayName("CsvSource default test")
    void toUpperCase_ShouldGenerateTheExpectedUppercaseValue(String input, String expected) {
        String actualValue = input.toUpperCase();
        assertThat(expected).isEqualTo(actualValue);
    }

    @ParameterizedTest
    @CsvSource(value = {"test:test", "tEst:test", "Java:java"}, delimiter = ':')
    @DisplayName("CsvSource delimiter test")
    void toLowerCase_ShouldGenerateTheExpectedLowercaseValue(String input, String expected) {
        String actualValue = input.toLowerCase();
        assertThat(expected).isEqualTo(actualValue);
    }
    ```

 - @MethodSource
    ```java
    //@MethodSource에 입력한 메소드명을 찾아서 값을 가져옴
    @ParameterizedTest
    @MethodSource("provideStringsForIsBlank")
    void isBlank_ShouldReturnTrueForNullOrBlankStrings(String input, boolean expected) {
        assertThat(expected).isEqualTo(Strings.isBlank(input));
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
        assertThat(true).isEqualTo(Strings.isBlank(input));
    }

    private static Stream<String> isBlank_ShouldReturnTrueForNullOrBlankStringsOneArgument() {
        return Stream.of(null, "", "  ");
    }

    //다른 클래스의 메소드를 찾아서 값을 가져옴
    @ParameterizedTest
    @MethodSource("com.baeldung.parameterized.StringParams#blankStrings")
    @Disabled
    void isBlank_ShouldReturnTrueForNullOrBlankStringsExternalSource(String input) {
        assertThat(true).isEqualTo(Strings.isBlank(input));
    }
    ```
 - @ArgumentsSource
 - @VariableSource

## @RepeatedTest
 - 반복 테스트 용도
 - RepetitionInfo 인자를 받을 수 있음(@RepeatedTest 어노테이션이 있어야만 받을 수 있음)

---

- @Tag

