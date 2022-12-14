package com.ydskingdom.junit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MockitoTest {

    @DisplayName("Let's verify some behaviour!")
    @Test
    void testCase1(){
        //mock 생성
        List mockedList = mock(List.class);

        mockedList.add("one");
        mockedList.clear();

        //.add("one")가 호출 되었는지 검증, OK
        verify(mockedList).add("one");

        //.clear()가 호출 되었는지 검증, OK
        verify(mockedList).clear();
    }

    @DisplayName("How about some stubbing?")
    @Test
    void testCase2(){
        //mock 생성
        LinkedList mockedList = mock(LinkedList.class);

        //stubbing
        when(mockedList.get(0)).thenReturn("first");
        when(mockedList.get(1)).thenThrow(new RuntimeException());

        //first 값 출력 되어야함
        assertThat(mockedList.get(0)).isEqualTo("first");

        //runtimeException 발생해야함
        assertThatThrownBy(() -> {
            System.out.println(mockedList.get(1));
        }).isInstanceOf(RuntimeException.class);

        //null이여야 함, becasuse get(999) was not stubbed
        assertThat(mockedList.get(999)).isNull();

        //get(0)을 실행했기 때문에 검증 성공
        verify(mockedList).get(0);
    }

    @DisplayName("Argument matchers")
    @Test
    void testCase3(){
        //mock 생성
        LinkedList mockedList = mock(LinkedList.class);

        when(mockedList.get(anyInt())).thenReturn("element");

        assertThat(mockedList.get(987)).isEqualTo("element");
        verify(mockedList).get(anyInt());
    }

    @DisplayName("Verifying exact number of invocations / at least x / never")
    @Test
    void testCase4() {
        //mock 생성
        LinkedList mockedList = mock(LinkedList.class);

        mockedList.add("once");

        mockedList.add("twice");
        mockedList.add("twice");

        mockedList.add("three times");
        mockedList.add("three times");
        mockedList.add("three times");

        //.add("once")가 실행됐는지 검증
        verify(mockedList).add("once");

        //.add("once")가 1번 실행됐는지 검증
        verify(mockedList, times(1)).add("once");

        //.add("twice")가 2번 실행됐는지 검증
        verify(mockedList, times(2)).add("twice");

        //.add("three times")가 3번 실행됐는지 검증
        verify(mockedList, times(3)).add("three times");

        //.add("never happened")가 한번도 실행되지 않았는지 검증
        verify(mockedList, never()).add("never happened");

        //.add("once")가 최대 한 번은 실행됐는지 검증
        verify(mockedList, atMostOnce()).add("once");

        //.add("three times")가 최소 한 번은 실행됐는지 검증
        verify(mockedList, atLeastOnce()).add("three times");

        //.add("three times")가 최소 두 번은 실행됐는지 검증
        verify(mockedList, atLeast(2)).add("three times");

        //.add("three times")가 최대 세 번은 실행됐는지 검증
        verify(mockedList, atMost(5)).add("three times");

        /*
        times(1)은 기본값이므로 생략될 수 있습니다.
         */
    }

    @DisplayName("Stubbing void methods with exceptions")
    @Test
    void testCase5() {
        //mock 생성
        LinkedList mockedList = mock(LinkedList.class);

        doThrow(new RuntimeException()).when(mockedList).clear();

        assertThatThrownBy(() -> {
            mockedList.clear();
        }).isExactlyInstanceOf(RuntimeException.class);

        /*
        처음에 stubVoid(Object)가 void 메소드를 stubbing 하기 위해 사용되었습니다.
        현재는 doThrow(Throwable)이 대신해서 사용되도록 stubVoid는 deprecated 되었습니다.
        doThrow를 사용함으로써 가독성이 좋아지고 doAnswer(Answer) 메소드군과 일관성도 지킬 수 있습니다.
         */
    }

    @DisplayName("Verification in order")
    @Test
    void testCase6() {
        List singleMock = mock(List.class);

        singleMock.add("was added first");
        singleMock.add("was added second");

        InOrder inOrder = inOrder(singleMock);

        inOrder.verify(singleMock).add("was added first");
        inOrder.verify(singleMock).add("was added second");

        List firstMock = mock(List.class);
        List secondMock = mock(List.class);

        firstMock.add("was called first");
        secondMock.add("was called second");

        InOrder inOrder1 = inOrder(firstMock, secondMock);
        inOrder1.verify(firstMock).add("was called first");
        inOrder1.verify(secondMock).add("was called second");

        /*
        mock들이 순서대로 실행되는지 검증하는 것은 유연한 검증 방식입니다.
        모든 method가 실행되는지 검증할 필요는 없고 관심 있는 method만 순서대로 실행되는지 검증하면 됩니다.

        또, 검증하려고 하는 mock만 InOrder를 통해 생성한 뒤 차례대로 실행되는지 검사할 수 있습니다.
         */
    }

    @DisplayName("Making sure interaction(s) never happened on mock")
    @Test
    void testCase7() {
        List mockOne = mock(List.class);

        //using mocks - only mockOne is interacted
        mockOne.add("one");

        //ordinary verification
        verify(mockOne).add("one");

        //verify that method was never called on a mock
        verify(mockOne, never()).add("two");
    }

    @DisplayName("Finding redundant invocations")
    @Test
    void testCase8() {
        //mock 생성
        LinkedList mockedList = mock(LinkedList.class);

        //using mocks
        mockedList.add("one");
        mockedList.add("two");

        verify(mockedList, never()).add("one");
        verify(mockedList).add("two");

        verifyNoMoreInteractions(mockedList);

        /*
        경고 : 고전적인 방법인 expect-run-verify 방식의 테스트를 하는 사람들은
        verifyNoMoreInteractions()를 너무 자주, 심지어는 모든 테스트에서 사용하는 경향이 있습니다.
        verifyNoMoreInteractions()는 아무 테스트에서나 무분별하게 사용되지 않았으면 합니다.
        verifyNoMoreInteractions()는 실행 여부를 검사하는 테스트 툴에서 간편하게 사용될 수 있는 assertion 방법입니다.
        정말 적절한 상황에서만 사용하세요.
        이 method를 불필요하게 많이 사용하게 되면 유지보수가 힘든 테스트가 만들어집니다.
        여기를 참조하면 더 많은 정보를 얻을 수 있습니다.

        never()에 대해서도 읽어보세요.
        코드의 의도를 잘 드러낼 수 있는 method입니다.
         */
    }

    @Test
    void testCase10(@Mock MockTest1 mockTest1) {
        when(mockTest1.someMethod("some arg"))
                .thenThrow(new RuntimeException())  // someMethod()를 처음 호출하면 RuntimeException을 던져라
                .thenReturn("foo");                 // someMethod() 두번째 호출부터는 "foo"를 반환시켜라

        assertThatThrownBy(() -> {
            mockTest1.someMethod("some arg");
        }).isExactlyInstanceOf(RuntimeException.class);

        assertThat(mockTest1.someMethod("some arg")).isEqualTo("foo");
        assertThat(mockTest1.someMethod("some arg")).isEqualTo("foo");

        // 연속적인 호출에 대한 Stubbing을 간략화할 수 있다.
        when(mockTest1.someMethod("some arg")).thenReturn("Hi", "Hello", "Hellllllllo");
        assertThat(mockTest1.someMethod("some arg")).isEqualTo("Hi");
        assertThat(mockTest1.someMethod("some arg")).isEqualTo("Hello");
        assertThat(mockTest1.someMethod("some arg")).isEqualTo("Hellllllllo");
    }

    @Test
    void testCase11(@Mock MockTest1 mockTest1) {
        /*
        generic Answer interface를 이용해 stubbing 하기
        이 기능은 초기의 Mockito에는 포함되지 않았고 여전히 논쟁의 대상인 기능입니다.
        우리는 단순한 stubbing인 thenReturn()이나 thenThrow()만 사용할 것을 추천합니다.
        이 두 개의 method 만으로도 테스트와 테스트 주도로 만들어지는 코드를 깔끔하게 유지할 수 있습니다.

        뭔지 잘 모르겠음, thenReturn, thenThrow를 추천한다고 함
         */
        when(mockTest1.someMethod(anyString())).thenAnswer((Answer) invocation -> {
            Object[] args = invocation.getArguments();
            Object mock = invocation.getMock();
            return "called with arguments : " + args;
        });

        System.out.println(mockTest1.someMethod("foo"));
    }

    @Test
    void testCase12() {
        /*
        컴파일러가 괄호 안에 void 메소드가 들어가는 것을 좋아하지 않기 때문에 when(Object)와 같은 형태로 void 메소드의 호출을 stubbing 하기 위해서는 다른 접근방법을 사용해야 합니다.
        doThrow(Throwable)은 stubVoid(Object) 메소드를 대신해서 사용할 수 있습니다. 이렇게 하는 주된 이유는 가독성을 높이고, doAnswer() 계열의 메소드와 일관성을 유지하기 위해서입니다.
        void 메소드에서 예외를 던지도록 stubbing 하고 싶으면 doThrow() 사용하세요.
         */

        List mockedList = mock(List.class);

        doThrow(new RuntimeException()).when(mockedList).clear();

        assertThatThrownBy(() -> {
            mockedList.clear();
        }).isExactlyInstanceOf(RuntimeException.class);
    }

    @Test
    void testCase13() {
        /*
        spy는 가끔식만, 주의해서 사용해야 합니다.
        method가 stubbing 되지 않았으면 실제 method를 호출
        method가 stubbing 되어 있으면 stubbing 값으로 리턴

        final method는 조심해야 합니다.
        Mockito는 final method를 mock 으로 만들지 않기 때문에 real object를 감시하면서 final 메소드를 stub하면 문제가 발생합니다.
        실제 객체가 아닌 spy에 넘겨준 mock의 method가 호출됩니다.
        mock 객체는 필드를 초기화 하지 않았기 때문에 일반적으로 NullPointerException이 발생합니다.
         */
        List list = new LinkedList();
        List spy = spy(list);

        when(spy.size()).thenReturn(100);

        spy.add("one");
        spy.add("two");

        // Stubbing과 관련없이 실제 spy에서 가져옴
        System.out.println(spy.get(0));

        // 위에 Stubbing 된 결과값 100을 반환
        System.out.println(spy.size());

        // 검증도 가능
        verify(spy).add("one");
        verify(spy).add("two");
    }

    @Test
    void testCase14() {
        List mockedList1 = mock(List.class);
        System.out.println(mockedList1.get(0));

        List mockedList2 = mock(List.class, RETURNS_SMART_NULLS);
        System.out.println(mockedList2.get(0));
    }

    @Test
    void testCase999() {
        List mockList = mock(List.class);

        when(mockList.get(0)).thenReturn(10, 20);
        assertThat(mockList.get(0)).isEqualTo(10);
        assertThat(mockList.get(0)).isEqualTo(20);

        when(mockList.get(0)).thenReturn(30).thenReturn(40);;
        assertThat(mockList.get(0)).isEqualTo(30);
        assertThat(mockList.get(0)).isEqualTo(40);
    }

    @Test
    void testCase999_1() {
        Iterator<Integer> iterator = mock(Iterator.class);
        when(iterator.hasNext()).thenReturn(true, true, true, false);
        when(iterator.next()).thenReturn(10, 20, 30);
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    @Test
    void testCase999_2() {
        List mockList = mock(List.class);

        when(mockList.get(0)).thenThrow(new RuntimeException("error!"));

        assertThatThrownBy(() -> {
            mockList.get(0);
        }).isExactlyInstanceOf(RuntimeException.class).hasMessage("error!");
    }

    @Test
    void testCase999_3() {
        List mockList = mock(List.class);

        doThrow(new RuntimeException("doThrow error")).when(mockList).get(0);

        assertThatThrownBy(() -> {
            mockList.get(0);
        }).isExactlyInstanceOf(RuntimeException.class).hasMessage("doThrow error");
    }

    static class MockTest1{
        public String someMethod(String value) {
            return "foo";
        }
    }
}
