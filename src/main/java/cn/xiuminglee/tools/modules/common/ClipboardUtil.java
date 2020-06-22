package cn.xiuminglee.tools.modules.common;

import cn.hutool.core.io.file.FileReader;
import cn.xiuminglee.tools.modules.Constant;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Xiuming Lee
 * @description 系统粘贴板工具
 */
public class ClipboardUtil {

    /**
     * 获取粘贴板中的图片文件
     * @return
     * @throws IOException
     */
    public static byte[] getImageBytes() throws IOException {
        byte[] imageBytes = null;
        if (Constant.System.CLIPBOARD.hasFiles()) {
            // 复制本地的图片。如果是文件只取最后一个文件
            List<File> files = Constant.System.CLIPBOARD.getFiles();
            File file = files.get(files.size() - 1);
            FileReader fileReader = new FileReader(file);
            imageBytes = fileReader.readBytes();
        }
        if (Constant.System.CLIPBOARD.hasImage()) {
            // 页面复制的图片或截图的图片
            Image image = Constant.System.CLIPBOARD.getImage();
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
            ByteArrayOutputStream byteArrayInputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", byteArrayInputStream);
            imageBytes = byteArrayInputStream.toByteArray();
        }
        return imageBytes;
    }
}
