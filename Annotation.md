
- @Test
    - 테스트 메서드임을 나타냄
- @BeforeEach, @BeforeAll
    - @BeforeEach : @Test를 실행하기전에 매번 실행
    - @BeforeAll : 클래스 내에 있는 모든 테스트를 실행하기 전 딱 한번만 실행
        - `static`으로 선언 필요
- @AfterEach, @AfterAll
    - @AfterEach : @Test 실행 후 매번 실행
    - @AfterAll : 클래스 내에 있는 모든 테스트들이 실행된 후 딱 한번만 실행
        - `static`으로 선언 필요
- @Nested
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
    
- @Timeout
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
    
- @Disabled
    - 테스트를 실행하지 않음



---
- @ParameterizedTest
- @CsvSource
- @ValueSource
- @DomainArgumentsSource
- @RepeatedTest
- @Tag

