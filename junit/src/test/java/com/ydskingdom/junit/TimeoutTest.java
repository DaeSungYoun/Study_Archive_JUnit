package com.ydskingdom.junit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class TimeoutTest {

    @Test
    @DisplayName("메소드에 @Timeout 테스트")
    @Timeout(3)
    void methodTimeoutTest() {
        assertThatThrownBy(() -> {
            List<String> list = Arrays.asList("String one", "String two");
            list.get(2);
        }).isInstanceOf(IndexOutOfBoundsException.class)
                .hasMessage("Array index out of range: 2");
//                .hasMessageContaining("Index: 2, Size: 2");

//
//        assertThatThrownBy(() -> {
//
//        }).isInstanceOf(IndexOutOfBoundsException.class)


    }
}
