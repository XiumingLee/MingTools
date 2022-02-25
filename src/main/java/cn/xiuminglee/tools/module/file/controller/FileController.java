package cn.xiuminglee.tools.module.file.controller;

import cn.xiuminglee.tools.common.response.ServerResponse;
import cn.xiuminglee.tools.module.file.entity.FileOperation;
import cn.xiuminglee.tools.module.file.service.FileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author Xiuming Lee
 * @date 2022/2/25 10:46
 * @desc
 */
@RequestMapping("/file")
@RestController
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    /** 删除文件名中存在指定字符的文件，是否包含文件夹 */
    @PostMapping("/operationFile")
    public Mono<ServerResponse<String>> operationFile(@RequestBody FileOperation fileOperation) {
        return Mono.justOrEmpty(fileService.operationFile(fileOperation));
    }





}
