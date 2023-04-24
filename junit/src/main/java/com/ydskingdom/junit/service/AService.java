package com.ydskingdom.junit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AService {
    private final BService bService;
    private final CService cService;
    private final DService dService;

    public void logloglog() {
        String loglog = bService.loglog();
        System.out.println("loglog = " + loglog);;

        String loglog1 = cService.loglog();
        System.out.println("loglog1 = " + loglog1);

        String loglog2 = dService.loglog();
        System.out.println("loglog2 = " + loglog2);

    }


}
