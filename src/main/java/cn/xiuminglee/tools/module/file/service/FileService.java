package cn.xiuminglee.tools.module.file.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.xiuminglee.tools.common.response.ServerResponse;
import cn.xiuminglee.tools.common.util.FileUtils;
import cn.xiuminglee.tools.module.file.entity.FileOperation;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

import java.io.File;

/**
 * @author Xiuming Lee
 * @date 2022/2/25 10:46
 * @desc
 */
@Service
public class FileService {

    public ServerResponse<String> operationFile(FileOperation fileOperation) {
        switch (fileOperation.getFileOperator()) {
            case RENAME:
                handleRenameOperator(fileOperation);
                break;
            case DELETE:
                handleDeleteOperator(fileOperation);
                break;
            default:
                return ServerResponse.createByErrorMessage("未找到当前操作符：" + fileOperation.getFileOperator().getValue());
        }
        return ServerResponse.createBySuccessMessage("操作完成！");
    }


    /**
     * 处理文件删除操作
     *
     * @param fileOperation 文件操作相关信息
     */
    private void handleDeleteOperator(FileOperation fileOperation) {
        File file = FileUtil.file(fileOperation.getFileName());
        FileUtils.traverseTheFolder(file, fileItem -> {
            String itemName = fileItem.getName();
            if (StrUtil.contains(itemName, fileOperation.getCondition())) {
                FileUtil.del(fileItem);
            }
        });
    }

    /**
     * 处理文件重命名操作,
     * 统一删除掉文件名中的指定字符。
     * @param fileOperation 文件操作相关信息
     */
    private void handleRenameOperator(FileOperation fileOperation) {
        File file = FileUtil.file(fileOperation.getFileName());
        FileUtils.traverseTheFolder(file, fileItem -> {
            String itemName = fileItem.getName();
            if (StrUtil.contains(itemName, fileOperation.getCondition())) {
                String newItemName = StrUtil.removeAll(itemName, fileOperation.getCondition());
                FileUtil.rename(fileItem,newItemName,true);
            }
        });
    }


}
