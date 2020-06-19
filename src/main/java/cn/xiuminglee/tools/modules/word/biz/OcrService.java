package cn.xiuminglee.tools.modules.word.biz;

import cn.hutool.core.io.file.FileReader;
import cn.xiuminglee.tools.modules.Constant;
import cn.xiuminglee.tools.modules.word.biz.element.LanguageType;
import cn.xiuminglee.tools.modules.word.biz.task.OcrServiceTask;
import cn.xiuminglee.tools.modules.word.view.WordController;
import javafx.concurrent.Worker;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.util.StringConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Xiuming Lee
 * @description OCR相关的方法，承接WordController的业务操作。
 */
@Service
@Slf4j
public class OcrService {

    // region 其他服务模块 --------------------------------------------------------------
    /** 百度相关 */
    @Autowired
    private BaiduService baiduService;
    @Autowired
    private OcrService ocrService;
    // endregion 其他服务模块 --------------------------------------------------------------

    @Autowired
    private WordController wordController;

    public void initOcrService(){
        wordController.ocrChoiceBox.setItems(LanguageType.buildBaiduLanguageType());
        wordController.ocrChoiceBox.setValue(new LanguageType("CHN_ENG","中英文混合"));
        wordController.ocrChoiceBox.setConverter(new StringConverter<LanguageType>() {
            @Override
            public String toString(LanguageType student) {
                return student.getValue();  // 这里将想要 看到的内容显示出来
            }
            @Override
            public LanguageType fromString(String s) {
                return null;
            }
        });
    }

    /** 设置OCR的快捷键
     *
     * 利用OcrFXService任务，异步请求，完成。
     */
    public void setOcrShortcuts(){
        OcrServiceTask ocrFXService = new OcrServiceTask(baiduService);
        ocrFXService.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                wordController.changLabelState(wordController.ocrState,State.SUCCEEDED);
                wordController.startTextArea.setText(newValue);
            }
        });
        ocrFXService.stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(Worker.State.SUCCEEDED)){
                // 重置
                ocrFXService.reset();
            }
            if (newValue.equals(Worker.State.FAILED)){
                wordController.changLabelState(wordController.ocrState,State.FAILED);
                ocrFXService.reset();
            }
        });
        // Ctrl + Shift + T
        KeyCombination keyCombination = new KeyCodeCombination(KeyCode.T,KeyCombination.CONTROL_DOWN,KeyCombination.SHIFT_DOWN);
        wordController.getWindow().getScene().getAccelerators().put(keyCombination,()->{
            wordController.changLabelState(wordController.ocrState,State.RUNNING);
            byte[] imageBytes = null;
            if (Constant.System.CLIPBOARD.hasFiles()){ // 复制本地的图片。如果是文件只取最后一个文件
                List<File> files = Constant.System.CLIPBOARD.getFiles();
                File file = files.get(files.size() - 1);
                FileReader fileReader = new FileReader(file);
                imageBytes = fileReader.readBytes();
            }
            if (Constant.System.CLIPBOARD.hasImage()){ // 页面复制的图片或截图的图片
                Image image = Constant.System.CLIPBOARD.getImage();
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
                ByteArrayOutputStream byteArrayInputStream = new ByteArrayOutputStream();
                try {
                    ImageIO.write(bufferedImage, "png", byteArrayInputStream);
                    imageBytes = byteArrayInputStream.toByteArray();
                } catch (IOException e) {
                    log.error("图片转换错误！",e);
                    wordController.changLabelState(wordController.ocrState,State.FAILED);
                    throw new RuntimeException(e);
                }
            }
            ocrFXService.setImageBytes(imageBytes);
            ocrFXService.start();
        });
    }
}
