package cn.xiuminglee.tools.modules.file.view;

import cn.xiuminglee.tools.core.bean.FXMLController;
import cn.xiuminglee.tools.core.bean.annotation.FXMLView;
import javafx.scene.layout.VBox;

/**
 * @author Xiuming Lee
 * @description  文件处理页面控制器
 */
@FXMLView(fxmlPath = "fxml/file/file.fxml")
public class FileController extends FXMLController {

    public VBox fileBox;
}
