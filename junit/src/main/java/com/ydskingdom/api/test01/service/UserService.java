package com.ydskingdom.api.test01.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    public String userServiceTest() {
        String s = testMethod01();

        return testMethod02(s);
    }

    public String testMethod01() {
        return "hi";
    }

    public String testMethod02(String s) {
        return s + " " + "hello";
    }

}
