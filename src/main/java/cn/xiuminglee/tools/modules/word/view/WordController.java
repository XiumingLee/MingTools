package cn.xiuminglee.tools.modules.word.view;

import cn.hutool.core.io.file.FileReader;
import cn.xiuminglee.tools.core.bean.FXMLController;
import cn.xiuminglee.tools.core.bean.annotation.FXMLView;
import cn.xiuminglee.tools.modules.Constant;
import cn.xiuminglee.tools.modules.word.biz.BaiduService;
import cn.xiuminglee.tools.modules.word.biz.OcrService;
import cn.xiuminglee.tools.modules.word.biz.State;
import cn.xiuminglee.tools.modules.word.biz.task.OcrServiceTask;
import javafx.concurrent.Worker;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Xiuming Lee
 * @description  文件处理页面控制器
 * 百度OCR：https://cloud.baidu.com/doc/OCR/s/zk3h7xz52
 */
@Slf4j
@FXMLView(fxmlPath = "fxml/word/word.fxml")
public class WordController extends FXMLController {


    // region 其他服务模块 --------------------------------------------------------------
    @Autowired
    private BaiduService baiduService;
    @Autowired
    private OcrService ocrService;
    // endregion 其他服务模块 --------------------------------------------------------------


    // region 页面中的Node元素 --------------------------------------------------------------
    /** 最外层的Node */
    public VBox wordBox;
    /** 用于存放OCR的结果和待翻译的文字 */
    public TextArea startTextArea;
    /** 存放翻译好的文字 */
    public TextArea resultTextArea;
    /** OCR状态 */
    public Label ocrState;
    /** 翻译状态 */
    public Label translateState;
    /** ocr的语言选择框 */
    public ChoiceBox ocrChoiceBox;
    // endregion  页面中的Node元素 --------------------------------------------------------------



    @Override
    protected void initController() {
        ocrService.initOcrService();
    }

    /**
     * 设置OCR的快捷键。
     * 利用OcrFXService任务，异步请求，完成。
     */
    public void setShortcuts(){
        OcrServiceTask ocrFXService = new OcrServiceTask(baiduService);
        ocrFXService.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                changLabelState(ocrState,State.SUCCEEDED);
                startTextArea.setText(newValue);
            }
        });
        ocrFXService.stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(Worker.State.SUCCEEDED)){
                // 重置
                ocrFXService.reset();
            }
            if (newValue.equals(Worker.State.FAILED)){
                changLabelState(ocrState,State.FAILED);
                ocrFXService.reset();
            }
        });
        // Ctrl + Shift + T
        KeyCombination keyCombination = new KeyCodeCombination(KeyCode.T,KeyCombination.CONTROL_DOWN,KeyCombination.SHIFT_DOWN);
        stage.getScene().getAccelerators().put(keyCombination,()->{
            changLabelState(ocrState,State.RUNNING);
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
                    changLabelState(ocrState,State.FAILED);
                    throw new RuntimeException(e);
                }
            }
            ocrFXService.setImageBytes(imageBytes);
            ocrFXService.start();
        });
    }


    /**
     * 修改OCR或翻译Label的状态
     * @param label OCR或翻译
     * @param value State值
     */
    private void changLabelState(Label label, State value){
        label.setText(value.getStateMessage());
        label.setTextFill(Paint.valueOf(value.getColor()));
    }

}
