package com.ydskingdom.junit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.InOrder;
import org.mockito.Mock;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

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

        verify(mockedList).add("one");
        verify(mockedList).add("two");

        verifyNoMoreInteractions(mockedList);
    }
}
