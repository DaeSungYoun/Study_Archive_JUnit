package com.ydskingdom.junit.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BService {
    Logger log = LoggerFactory.getLogger(BService.class);

    public String loglog() {
        log.info("BServcie");

        return "BBBBBB";
    }
}
