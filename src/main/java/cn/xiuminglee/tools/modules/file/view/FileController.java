package cn.xiuminglee.tools.modules.file.view;

import cn.xiuminglee.tools.core.bean.FXMLController;
import cn.xiuminglee.tools.core.bean.annotation.FXMLView;
import cn.xiuminglee.tools.modules.common.State;
import cn.xiuminglee.tools.modules.file.biz.FileChangeNameService;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Xiuming Lee
 * @description  文件处理页面控制器
 *
 * @see FileChangeNameService
 */
@FXMLView(fxmlPath = "fxml/file/file.fxml")
public class FileController extends FXMLController {

    private FileChangeNameService fileChangeNameService;

    public VBox fileBox;
    // region 修改文件名称 --------------------------------------------------------------
    public Button fileChooserBtn;
    public ToggleGroup radioToggleGroup = new ToggleGroup();
    public RadioButton radioButtonRemove;
    public RadioButton radioButtonPinyin;
    public Label changeNameStateLabel;
    public State changeNameState = State.READY;
    public Label selectedFileName;
    public HBox changeNameOtherBox;
    public TextField changeNameRemoveStr;
    // endregion 修改文件名称 --------------------------------------------------------------


    /** 点击修改文件名称页面的执行按钮 */
    public void handleFileChangeNameButtonAction(ActionEvent event) {
        fileChangeNameService.handleFileChangeNameButtonAction(event);
    }

    /**
     * 修改状态
     * @param label OCR或翻译
     * @param value State值
     */
    public void changLabelState(Label label, State state, State value){
        state = value;
        label.setText(value.getStateMessage());
        label.setTextFill(Paint.valueOf(value.getColor()));
    }


    @Override
    protected void initController() {
        this.fileChangeNameService = new FileChangeNameService(this);
    }
}
