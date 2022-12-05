## 목차

1. [@Test](#test)
2. [@BeforeEach, @BeforeAll](#beforeeach-beforeall)
3. [@AfterEach, @AfterAll](#aftereach-afterall)
4. [@Nested](#nested)
5. [@Timeout](#timeout)
6. [@Disabled](#disabled)
7. [@ParameterizedTest](#parameterizedtest)
8. [@RepeatedTest](#repeatedtest)
9. [@SpringBootTest](#springboottest)
10. [@ExtendWith](#extendwith)
11. [@WebMvcTest](#webmvctest)
12. [@MockMvc](#mockmvc)
13. [@MockBean, @Mock](#mockbean-mock)
14. [@AutoConfigureMockMvc](#autoconfiguremockmvc)
15. [@Import](#import)
16. [@DisplayName, @DisplayNameGeneration](#displayname-displaynamegeneration)


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
 - Dependency
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

## @SpringBootTest
 - 통합 테스트 용도
 - @SpringBootApplication을 찾아가 하위의 모든 Bean을 스캔하여 로드함
 - 그 후 Test용 Application Context를 만들어 Bean을 추가하고, MockBean을 찾아서 교체함
 - Dependency 추가, spring-boot-starter-test
	```java
	testImplementation group: 'org.springframework.boot', name: 'spring-boot-starter-test', version: '3.0.0'
	```
 - @SpringBootTest 하위 어노테이션
	 ```java
	 @Target(ElementType.TYPE)  
	@Retention(RetentionPolicy.RUNTIME)  
	@Documented  
	@Inherited  
	@BootstrapWith(SpringBootTestContextBootstrapper.class)  
	@ExtendWith(SpringExtension.class)  
	public @interface SpringBootTest {
	```

## @ExtendWith
 - JUnit4에서 @RunWith로 사용되던 어노테이션이 ExtendWith로 변경됨
 - 확장을 선언적으로 등록할 떄 사용
 - @ExtendWith는 메인으로 실행될 Class를 지정할 수 있음
 - @SpringBootTest는 기본적으로 @ExtendWith가 추가 되어 있음

## @WebMvcTest
 - @WebMvcTest(클래스명.class)
 - 괄호에 작성된 클래스만 실제로 로드하여 테스트를 진행
 - 매개변수를 지정해주지 않으면 @Controller, @RestController, @RestControllerAdvice등 컨트롤러와 연관된 Bean이 모두 로드됨
 - 스프링의 모든 Bean을 로드하는 @SpringBootTest 대신 컨트롤러 관련 코드만 테스트할 경우 사용

## @MockMvc
 - 서블릿 컨테이너의 구동 없이 시뮬레이션된 MVC 환경에 모의 HTTP 서블릿 요청을 전송하는 기능을 제공하는 유틸리티 클래스
 - Controller의 API를 테스트하는 용도인 MockMvc 객체를 주입 받음
 - perform() 메소드를 활용하여 컨트롤러의 동작을 확인할 수 있음
 - .andExpect(), .andDo(), .andReturn()등의 메소드를 같이 활용함
 - 테스트할 클래스에서 주입받고 있는 객체에 대해 가짜 객체를 생성해주는 어노테이션

## @MockBean, @Mock
- 

## @AutoConfigureMockMvc
 - spring, test, mockmvc의 설정을 로드하면서 MockMvc의 의존성을 자동으로 주입
 - MockMvc 클래스는 RestAPI 테스트를 할 수 있는 클래스

## @Import
 - 필요한 Class들을 Configuration으로 만들어 사용할 수 있음
 - Configuration Component 클래스도 의존성 설정할 수 있음
 - Import된 클래스는 주입으로 사용 가능

## @DisplayName, @DisplayNameGeneration
- @DisplayName
	``` java
	import org.junit.jupiter.api.DisplayName;
	import org.junit.jupiter.api.Test;

	@DisplayName("I'm a Test Class")
	public class DisplayNameCustomTest {

		@Test
		@DisplayName("Test with spaces, expected ok")
		void test_spaces_ok() {
		}

		@DisplayName("Test with spaces, expected failed")
		@Test
		void test_spaces_fail() {
		}
	}
	```
- @DisplayNameGeneration
	``` java
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@DisplayNameGeneration(DisplayNameGenerator.IndicativeSentences.class)
	@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
	@DisplayNameGeneration(DisplayNameGenerator.Standard.class)
	```
- @DisplayNameGeneration Custom
	- [링크](https://github.com/eugenp/tutorials/blob/950bbadc353bdca114befc98cf4a18476352220e/testing-modules/junit-5-advanced/src/test/java/com/baeldung/displayname/DisplayNameGeneratorUnitTest.java)

---
- @AggregatedWith
- @Tag
- @Enabledxxxx, @Disabledxxxx
- @TestInstance
- @TestMethodOrder
- @Order
- @RegisterExtension
- @Retention

