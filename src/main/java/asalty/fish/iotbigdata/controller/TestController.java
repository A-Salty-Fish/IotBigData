package asalty.fish.iotbigdata.controller;

import asalty.fish.iotbigdata.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2021/12/27 20:45
 */
@RestController
@RequestMapping("test")
public class TestController {

    @Resource
    TestService testService;

    @GetMapping("test")
    public String test() {
        return testService.test();
    }
}
