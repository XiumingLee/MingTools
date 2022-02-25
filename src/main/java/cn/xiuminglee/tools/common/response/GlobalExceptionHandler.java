package cn.xiuminglee.tools.common.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

/**
 * @Author: Xiuming Lee
 * @Date: 2019/4/27 20:20
 * @Version 1.0
 * @Describe: 全局异常处理
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 全局异常处理，
     * 所有的异常都会被处理，但是，当该错误不是前台发请求引起时，则前端没有返回值。
     * 比如说：前端发起文件上传操作，问价大小超出了Spring MVC设置的大小，但是这个错误不会返回给前端。
     * （补充：不加这个处理，上面的错误也不会返回给前端，前端收不到任何响应）
     * 因为该请求没有到达相应的Controller之前已经被拦截，发生错误，被这里处理，不知道该返回到哪。前端就收不到错误信息
     * 准确的说是收不到任何相应。只有请求进入了相应的Controller之后发生的错误，被这里处理后才会返回到前端。
     *
     * 换句话说，只有该请求进入了Controller层，发生的错误才会处理后返回前端。未进入Controller层产生的错误，会被处理，
     * 但是前端收不到任何响应。
     * @param ex 错误信息
     * @return Mono<ServerResponse<String>>
     */
    @ExceptionHandler(Exception.class)
    public Mono<ServerResponse<String>> exceptionHandler(Exception ex) {
        ex.printStackTrace();
        LOGGER.error(ex.getMessage());
        return Mono.just(ServerResponse.createByErrorMessage(ex.getMessage()));
    }
}
