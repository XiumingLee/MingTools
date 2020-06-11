package cn.xiuminglee.tools.modules;

import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @author Xiuming Lee
 * @description
 */
public abstract class AbstractUiObject extends Stage {
    protected Parent root;
    private double xOffset;
    private double yOffset;


    /** 辅助方法：通过元素id获取元素 */
    public  <T> T $(String id, Class<T> clazz) {
        return (T) root.lookup("#" + id);
    }


    /** 清除已选中的 */
    public void clearViewListSelectedAll(ListView<Pane>... listViews) {
        for (ListView<Pane> listView : listViews) {
            listView.getSelectionModel().clearSelection();
        }
    }

    /** 窗体移动 */
    public void move() {
        root.setOnMousePressed(event -> {
            xOffset = getX() - event.getScreenX();
            yOffset = getY() - event.getScreenY();
            root.setCursor(Cursor.CLOSED_HAND);
        });
        root.setOnMouseDragged(event -> {
            setX(event.getScreenX() + xOffset);
            setY(event.getScreenY() + yOffset);
        });
        root.setOnMouseReleased(event -> {
            root.setCursor(Cursor.DEFAULT);
        });
    }

    public double x(){
        return getX();
    }

    public double y(){
        return getY();
    }

    public double width(){
        return getWidth();
    }

    public double height(){
        return getHeight();
    }

    /** 初始化页面 */
    public abstract void initView();

    /** 初始化事件定义 */
    public abstract void initEventDefine();
}
