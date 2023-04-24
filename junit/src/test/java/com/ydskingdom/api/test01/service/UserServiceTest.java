package com.ydskingdom.api.test01.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserService userService;

    @Test
    void test() {
        when(userService.testMethod01()).thenReturn("test test");

        when(userService.testMethod02(any())).thenReturn("test test test!!!");

        System.out.println(userService.userServiceTest());
    }
}