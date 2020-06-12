package cn.xiuminglee.tools.modules.home.element;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * @author Xiuming Lee
 * @description 左侧工具列表中每个工具元素
 */
public class ElementTool {

    private Pane pane;
    /** 工具图标 */
    private Label toolIcon;
    /** 工具名称 */
    private Label toolName;

    public ElementTool() {
        pane = new Pane();
        pane.setPrefSize(270, 80);
        pane.getStyleClass().add("toolElement");
        ObservableList<Node> children = pane.getChildren();

        // 工具图标区域
        toolIcon = new Label();
        toolIcon.setPrefSize(50, 50);
        toolIcon.setLayoutX(15);
        toolIcon.setLayoutY(15);
        toolIcon.getStyleClass().add("element_toolIcon");
        toolIcon.setStyle(String.format("-fx-background-image: url('fxml/home/img/tool/%s.png')", "04_100"));
        children.add(toolIcon);

        // 工具名称区域
        toolName = new Label();
        toolName.setPrefSize(140, 25);
        toolName.setLayoutX(80);
        toolName.setLayoutY(15);
        toolName.setText("应用名称");
        toolName.getStyleClass().add("element_toolName");
        children.add(toolName);
    }

    public Pane getElement(){
        return pane;
    }
}
