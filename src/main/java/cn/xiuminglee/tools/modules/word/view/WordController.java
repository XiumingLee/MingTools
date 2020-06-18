package cn.xiuminglee.tools.modules.word.view;

import cn.hutool.core.io.file.FileReader;
import cn.xiuminglee.tools.core.bean.FXMLController;
import cn.xiuminglee.tools.core.bean.annotation.FXMLView;
import cn.xiuminglee.tools.modules.Constant;
import cn.xiuminglee.tools.modules.word.biz.BaiduService;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
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
 */
@Slf4j
@FXMLView(fxmlPath = "fxml/word/word.fxml")
public class WordController extends FXMLController {


    @Autowired
    private BaiduService baiDuService;

    public VBox wordBox;
    /** 用于存放OCR的结果和待翻译的文字 */
    public TextArea startTextArea;
    /** 存放翻译好的文字 */
    public TextArea resultTextArea;

    public void setShortcuts(){
        // Ctrl + Shift + t
        KeyCombination keyCombination = new KeyCodeCombination(KeyCode.T,KeyCombination.CONTROL_DOWN,KeyCombination.SHIFT_DOWN);
        stage.getScene().getAccelerators().put(keyCombination,()->{
            // 运行在UI线程
            if (Constant.System.CLIPBOARD.hasFiles()){ // 复制本地的图片。如果是文件只取最后一个文件
                log.info("这次OCR走的是：hasFiles()");
                List<File> files = Constant.System.CLIPBOARD.getFiles();
                File file = files.get(files.size() - 1);
                FileReader fileReader = new FileReader(file);
                byte[] imageBytes = fileReader.readBytes();
                String s = baiDuService.generalBasic(imageBytes);
                startTextArea.setText(s);
            }
            if (Constant.System.CLIPBOARD.hasImage()){ // 页面复制的图片或截图的图片
                log.info("这次OCR走的是：hasImage()");
                Image image = Constant.System.CLIPBOARD.getImage();
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
                ByteArrayOutputStream byteArrayInputStream = new ByteArrayOutputStream();
                try {
                    ImageIO.write(bufferedImage, "png", byteArrayInputStream);
                    byte[] imageBytes = byteArrayInputStream.toByteArray();
                    String s = baiDuService.generalBasic(imageBytes);
                    startTextArea.setText(s);
                } catch (IOException e) {
                    log.error("图片转换错误！",e);
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
