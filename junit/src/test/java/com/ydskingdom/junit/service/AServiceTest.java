package com.ydskingdom.junit.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AServiceTest {

    @InjectMocks
    AService aService;

    @Spy
    BService bService;

    @Spy
    CService cService;

    @Spy
    DService dService;

    @Test
    void aaa() {
        aService.logloglog();
    }

    @Test
    void bbb() {
        when(bService.loglog()).thenReturn("123123123");
        aService.logloglog();;
    }
}