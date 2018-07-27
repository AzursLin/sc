package com.lxz;

import org.springframework.stereotype.Component;

/**
 * Created by sg on 2018/7/24.
 */
@Component
public class SchedualServiceHiHystric implements SchedualServiceHi {
    @Override
    public String sayHiFromClientOne(String name) {
        return "sorry "+name;
    }
}
