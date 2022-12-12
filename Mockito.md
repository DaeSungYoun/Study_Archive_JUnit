# Mockito Framework

- https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html

## Dependency
- spring-boot-starter-test
    ```XML
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <version>2.7.6</version>
        <scope>test</scope>
    </dependency>

    ```
- spring boot가 없는 경우
    ```XML
    <dependency>
        <groupId>org.mockito</groupId>
        <artifactId>mockito-core</artifactId>
        <version>3.1.0</version>
        <scope>test</scope>
    </dependency>


    <dependency>
        <groupId>org.mockito</groupId>
        <artifactId>mockito-junit-jupiter</artifactId>
        <version>3.1.0</version>
        <scope>test</scope>
    </dependency>
    ```
## Mockito
- 단위 테스트를 위한 Java Mocking Framework
    ### Mock 객체 만들기
    - mock()
        ```java
        MemberService memberService = Mockito.mock(MemeberService.class);
        StudyRepository studyRepository = mock(StudyRepository.class);
        ```
    - @Mock
        ```java
        @ExtendWith(MockitoExtension.class)
        class MockitoTest{
            @Mock
            MemberService memberService;

            @test
            void mockTest(@Mock StudyRepository studyRepository){
                ...
            }
        }
        ```
    ### Stubbing
    - when().thenReturn();
        ```java
        @Test
        void testCase9() {
            List mockList = mock(List.class);

            when(mockList.get(0)).thenReturn(10, 20);
            assertThat(mockList.get(0)).isEqualTo(10);
            assertThat(mockList.get(0)).isEqualTo(20);

            when(mockList.get(0)).thenReturn(30).thenReturn(40);;
            assertThat(mockList.get(0)).isEqualTo(30);
            assertThat(mockList.get(0)).isEqualTo(40);
        }
        ```
        ```java
        @Test
        void testCase10() {
            Iterator<Integer> iterator = mock(Iterator.class);
            when(iterator.hasNext()).thenReturn(true, true, true, false);
            when(iterator.next()).thenReturn(10, 20, 30);
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }
        }
        ```
    - when().thenThrow();
        ```java
        @Test
        void testCase11() {
            List mockList = mock(List.class);

            when(mockList.get(0)).thenThrow(new RuntimeException("error!"));

            assertThatThrownBy(() -> {
                mockList.get(0);
            }).isExactlyInstanceOf(RuntimeException.class).hasMessage("error!");
        }
        ```
    - doThrow().when().doSomething();
        ```java
        @Test
        void testCase12() {
            List mockList = mock(List.class);

            doThrow(new RuntimeException("doThrow error")).when(mockList).get(0);

            assertThatThrownBy(() -> {
                mockList.get(0);
            }).isExactlyInstanceOf(RuntimeException.class).hasMessage("doThrow error");
        }
        ```
    ### Argument Matchers
    - https://www.logicbig.com/tutorials/unit-testing/mockito/mockito-argument-matchers.html
    - https://www.baeldung.com/mockito-argument-matchers
    ### Verifying
    - 검증
        ```java
        public static <T> T verify(T mock) {
            return MOCKITO_CORE.verify(mock, times(1));
        }

        public static <T> T verify(T mock, VerificationMode mode) {
            return MOCKITO_CORE.verify(mock, mode);
        }
        ```
    - VerificationMode
        - times(int n) : 몇번 실행됐는지 검증
        - never : 한번도 실행되지 않았는지 검증
        - atLeastOnce : 최소 한번 실행됐는지 검증
        - atLeast(int n) : 최소 n번 실행됐는지 검증
        - atMostOnce : 최대 한번 실행됐는지 검증
        - atMost(int n) : 최대 n번 실행됐는지 검증
        - calls(int n) : n번 실행됐는지 검증(InOrder와 같이 사용)
        - only : 해당 메소드만 실행됐는지 검증
        - timeout(long millis) : millis 이상 걸리는지 확인, 시간이 지나면 바로 검증 종료
        - after(long millis) : millis 이상 걸리는지 확인, 시간이 지나도 검증 종료 되지 않음
        - desciption(String description) : 실패했을 경우 메세지
    - InOrder
        - 순서대로 검증할때 사용
    - verifyNoMoreInteractions
        - verify 이후 다른 검증이 없었다는 것을 검증할때 사용
    - verifyNoInteractions
        - 아무런 검증이 없었다는 것을 검증할때 사용
