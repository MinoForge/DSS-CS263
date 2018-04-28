package refmeister.display.elements;


import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import javax.swing.*;

public class TitleBarPane extends AnchorPane {

    //This should be the very top pane on our window.
    //Needs title anchored to the left
    //Needs minimize, close buttons anchored to the right.
    //Needs click-and-drag window functionality.
    //Needs close, minimize functionality on press.

    private static final String DEFAULT_TITLE = "Java Window";

    private static AnchorPane titleBar;

    private String title;

    private Node[] buttons;

    public TitleBarPane() {
        this(DEFAULT_TITLE, new boolean[]{true, false, true});
    }

    public TitleBarPane(String title) {
        this(title, new boolean[]{true, false, true});
    }

    /**
     *
     * @param title The text displayed at the left of the TitleBarPane.
     * @param windowOpts Whether the TitleBarPane should have exit, maximize, minimize.
     */
    public TitleBarPane(String title, boolean[] windowOpts) {

        this.title = title;
        Node titleNode = new Text(10, 50, this.title);
        AnchorPane.setTopAnchor(titleNode, 5d);
        AnchorPane.setLeftAnchor(titleNode, 10d);
        AnchorPane.setBottomAnchor(titleNode, 5d);
        this.getChildren().add(titleNode);
        this.buttons = new Node[3];
        setWindowOpts(windowOpts);
        for(int i = 0; i < buttons.length; i++) {
            if(buttons[i] != null) {
                this.getChildren().add(buttons[i]);
            }
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setWindowOpts(boolean[] opts) {
        int offset = 0;
        int bIndex = 0;
        if(opts.length != 3) {
            return;
        }
        if(opts[0]) {
            makeExitB(offset++);
        }
        if(opts[1]) {
            makeMaxiB(offset++);
        }
        if(opts[2]) {
            makeMiniB(offset);
        }

    }

    private void makeExitB(int offset) {
        Node exitNode = new Rectangle(60d, 30d, Color.RED);

        AnchorPane.setRightAnchor(exitNode, offset * 30d);
        AnchorPane.setTopAnchor(exitNode, 0d);

        Node crossOne = new Rectangle(20d, 4d, Color.BLACK);
        Node crossTwo = new Rectangle(20d, 4d, Color.BLACK);
        crossOne.setRotate(45);
        crossTwo.setRotate(135);

        AnchorPane.setTopAnchor(crossOne, 15d*Math.sin(45d));
        AnchorPane.setRightAnchor(crossOne, (offset+1) * 30d - 20d * Math.cos(45d));
        AnchorPane.setTopAnchor(crossTwo, 15d*Math.sin(45d));
        AnchorPane.setRightAnchor(crossTwo, (offset+1) * 30d - 20d * Math.cos(45d));


        Node[] exitButtons = new Node[]{exitNode, crossOne, crossTwo};
        setOpacity(exitButtons, .1f, 1);
        for(Node n: exitButtons) {
            n.setOnMouseReleased(e -> Platform.exit());
        }
        this.getChildren().addAll(exitButtons);
    }

    private void makeMaxiB(int offset) {
        Node maxiBackNode = new Rectangle(60d, 30d, Color.GREEN);
        AnchorPane.setRightAnchor(maxiBackNode, offset * 60d);
        AnchorPane.setTopAnchor(maxiBackNode, 0d);

        Node maxiNode = new Rectangle(50d, 20d, Color.BLACK);
        AnchorPane.setRightAnchor(maxiNode, offset * 60d + 5d);
        AnchorPane.setTopAnchor(maxiNode, 5d);

        Node[] maxiButtons = setOpacity(new Node[]{maxiBackNode, maxiNode}, .1f, 1);
        for(Node n: maxiButtons) {
            //TODO need to figure out how to add maximization
        }

        this.getChildren().addAll(maxiButtons);
    }

    private void makeMiniB(int offset) {
        Node miniBackNode = new Rectangle(60d, 30d, Color.BLUE);
        AnchorPane.setRightAnchor(miniBackNode, offset * 60d);
        AnchorPane.setTopAnchor(miniBackNode, 0d);

        Node miniNode = new Rectangle(60d, 5d, Color.BLACK);
        AnchorPane.setRightAnchor(miniNode, offset * 60d);
        AnchorPane.setTopAnchor(miniNode, 25d);

        Node[] miniButtons = setOpacity(new Node[]{miniBackNode, miniNode}, .1f, 1);
        for(Node n: miniButtons) {
            //TODO need to figure out how to add maximization
        }
        this.getChildren().addAll(miniButtons);

    }

    private Node[] setOpacity(Node[] nodes, float mouseOff, float mouseOn) {
        for(int i = 0; i < nodes.length; i++) {
            nodes[i].setOpacity(mouseOff);
            nodes[i].setOnMouseEntered(e -> {
                for(Node n: nodes) {
                    n.setOpacity(mouseOn);
                }
            });
            nodes[i].setOnMouseExited(e -> {
                for(Node n: nodes) {
                    n.setOpacity(mouseOff);
                }
            });
        }
        return nodes;
    }

    public static Pane getPane() {
        if(titleBar != null) {
            return titleBar;
        } else {
            titleBar = new TitleBarPane();
            return titleBar;
        }
    }

    public static Pane getPane(String windowTitle) {
        if(titleBar != null && titleBar instanceof TitleBarPane) {
            ((TitleBarPane)titleBar).setTitle(windowTitle);
            return titleBar;
        } else {
            titleBar = new TitleBarPane(windowTitle);
            return titleBar;
        }
    }

    public static Pane getPane(String windowTitle, boolean[] windowOpts) {
        if(titleBar != null && titleBar instanceof TitleBarPane) {
            ((TitleBarPane)titleBar).setTitle(windowTitle);
            ((TitleBarPane)titleBar).setWindowOpts(windowOpts);

            return titleBar;
        } else {
            titleBar = new TitleBarPane(windowTitle, windowOpts);
            return titleBar;
        }
    }
}
