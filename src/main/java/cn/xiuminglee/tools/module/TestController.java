package cn.xiuminglee.tools.module;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author Xiuming Lee
 * @date 2022/2/15 10:18
 * @desc
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello")
    public Mono<String> hello() {
        return Mono.just("Hello World!");
    }

}
