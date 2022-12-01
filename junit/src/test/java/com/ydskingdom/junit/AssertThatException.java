package com.ydskingdom.junit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class AssertThatException {

    @Test
    @DisplayName("assertThatThrownBy -> Exception이 발생 체크")
    void assertThatThrownBy1() {
        assertThatThrownBy(() -> {
            List<String> list = Arrays.asList("String one", "String two");
            list.get(2);
        });
    }

    @Test
    @DisplayName("assertThatThrownBy -> Exception 타입 체크")
    void assertThatThrownBy2() {
        assertThatThrownBy(() -> {
            List<String> list = Arrays.asList("String one", "String two");
            list.get(2);
        }).isExactlyInstanceOf(ArrayIndexOutOfBoundsException.class);
    }

    @Test
    @DisplayName("assertThatThrownBy -> 메세지 체크")
    void assertThatThrownBy3() {
        assertThatThrownBy(() -> {
            List<String> list = Arrays.asList("String one", "String two");
            list.get(2);
        }).isExactlyInstanceOf(ArrayIndexOutOfBoundsException.class)
                .hasMessage("Array index out of range: 2");
    }

    @Test
    @DisplayName("assertThatThrownBy -> 메세지 관련 체크")
    void assertThatThrownBy4() {
        assertThatThrownBy(() -> {
            List<String> list = Arrays.asList("String one", "String two");
            list.get(2);
        }).isExactlyInstanceOf(ArrayIndexOutOfBoundsException.class)
                .hasMessage("Array index out of range: 2")
                .hasMessageStartingWith("Array")
                .hasMessageContaining("2")
                .hasMessageEndingWith("range: 2")
                .hasStackTraceContaining("java.lang.ArrayIndexOutOfBoundsException");
    }

    @Test
    @DisplayName("assertThatThrownBy -> 부모 클래스 Exception 체크")
    void assertThatThrownBy5() {
        assertThatThrownBy(() -> {
            List<String> list = Arrays.asList("String one", "String two");
            list.get(2);
        }).isExactlyInstanceOf(ArrayIndexOutOfBoundsException.class)
                .isInstanceOf(IndexOutOfBoundsException.class); //부모 클래스 타입 체크
    }

    @Test
    @DisplayName("assertThatExceptionOfType 테스트")
    void assertThatExceptionOfTypeTest() {
        assertThatExceptionOfType(ArrayIndexOutOfBoundsException.class).isThrownBy(() -> {
                    List<String> list = Arrays.asList("String one", "String two");
                    list.get(2);
                }).withMessage("Array index out of range: 2")
                .withMessageContaining("2")
                .withNoCause();
    }

    @Test
    @DisplayName("assertThatNoException 테스트")
    void assertThatNoExceptionTest() {
        assertThatNoException().isThrownBy(() -> {
            System.out.println("Hi");
        });
    }

    @Test
    @DisplayName("assertThatNullPointerException 테스트")
    void assertThatNullPointerExceptionTest() {
        assertThatNullPointerException().isThrownBy(() -> {
            throw new NullPointerException("boom");
        }).withMessage("boom")
                .withMessageContaining("boom")
                .withNoCause();
    }

    @Test
    @DisplayName("assertThatIllegalArgumentException 테스트")
    void assertThatIllegalArgumentExceptionTest() {
        assertThatIllegalArgumentException().isThrownBy(() -> {
                    throw new IllegalArgumentException("boom");
                }).withMessage("boom")
                .withMessageContaining("boom")
                .withNoCause();
    }

    @Test
    @DisplayName("assertThatException 테스트")
    void assertThatExceptionTest() {
        assertThatException().isThrownBy(() -> {
                    throw new Exception("boom");
                }).withMessage("boom")
                .withMessageContaining("boom")
                .withNoCause();
    }


    @Test
    @DisplayName("assertThatIllegalStateException 테스트")
    void assertThatIllegalStateExceptionTest() {
        assertThatIllegalStateException().isThrownBy(() -> {
                    throw new IllegalStateException("boom");
                }).withMessage("boom")
                .withMessageContaining("boom")
                .withNoCause();
    }

    @Test
    @DisplayName("assertThatIOException 테스트")
    void assertThatIOExceptionTest() {
        assertThatIOException().isThrownBy(() -> {
                    throw new IOException("boom");
                }).withMessage("boom")
                .withMessageContaining("boom")
                .withNoCause();
    }


    @Test
    @DisplayName("assertThatRuntimeException 테스트")
    void assertThatRuntimeExceptionTest() {
        assertThatRuntimeException().isThrownBy(() -> {
                    throw new RuntimeException("boom");
                }).withMessage("boom")
                .withMessageContaining("boom")
                .withNoCause();
    }
}
