package com.ydskingdom.junit.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DService {
    Logger log = LoggerFactory.getLogger(DService.class);

    public String loglog() {
        log.info("DServcie");

        return "DDDD";
    }
}
