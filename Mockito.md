# Mockito Framework

- https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html

- Dependency
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
- Mockito
    - 단위 테스트를 위한 Java Mocking Framework
- Mock 객체 만들기
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
- Stubbing
    - verify() : 스터빙한 메소드가 실행 됐는지 등등 체크
        ```java
        verify(T mock, VerificationMode mode)
        ```
        ```text
        - times(n) : 몇번 호출 됐는지 검증
        - never : 한번도 호출되지 않았는지 검증
        - atLeastOne : 최소 한 번은 호출됐는지 검증
        - atMostOnce : 최대 한 번이 호출됐는지 검증
        - atMost(n) : 최대 n번이 호출됐는지 검증
        - calls(n) : n번이 호출됐는지 검증(InOrder랑 같이 사용)
        - only : 해당 검증 메소드만 실행됐는지 검증
        - timeout(long mills) : n ms 이상 걸리면 Fail, 그리고 바로 검증 종료
        - after(long mills) : n ms 이상 걸리는지 확인, timeout()과 다르게 바로 검증 종료 되지 않음
        - description : 실패할 경우 출력 문구
        ```
