package com.ydskingdom.junit.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CService {
    Logger log = LoggerFactory.getLogger(CService.class);

    public String loglog() {
        log.info("CServcie");

        return "CCCC";
    }
}
