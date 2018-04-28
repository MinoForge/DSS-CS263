package refmeister.display.elements;


import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.w3c.dom.css.Rect;


public class TitleBarPane extends AnchorPane {

    //This should be the very top pane on our window.
    //Needs title anchored to the left
    //Needs minimize, close buttons anchored to the right.
    //Needs click-and-drag window functionality.
    //Needs close, minimize functionality on press.

    private static final String DEFAULT_TITLE = "Java Window";

    private static final double BAR_HEIGHT = 20d;

    private static final double BUTTON_WIDTH = 50d;

    private static AnchorPane titleBar;

    private String title;

    private Node[] buttons;

    public TitleBarPane(Scene currentStage) {
        this(DEFAULT_TITLE, new boolean[]{true, false, true}, currentStage);
    }

    public TitleBarPane(String title, Scene currentStage) {
        this(title, new boolean[]{true, false, true}, currentStage);
    }

    /**
     *
     * @param title The text displayed at the left of the TitleBarPane.
     * @param windowOpts Whether the TitleBarPane should have exit, maximize, minimize.
     */
    public TitleBarPane(String title, boolean[] windowOpts, Scene currentStage) {

        Rectangle backNode = new Rectangle(currentStage.getWidth(), BAR_HEIGHT);
        backNode.setFill(Color.DARKGRAY);

        AnchorPane.setTopAnchor(backNode, 0d);
        AnchorPane.setLeftAnchor(backNode, 0d);
        AnchorPane.setRightAnchor(backNode, 0d);
        this.getChildren().add(backNode);

        this.title = title;
        Text textTitle = new Text(30d, BAR_HEIGHT-1, title);
        textTitle.setFont(new Font("Verdana", 14));
        Node titleNode = textTitle;

        AnchorPane.setTopAnchor(titleNode, 1d);
        AnchorPane.setLeftAnchor(titleNode, 10d);
        AnchorPane.setBottomAnchor(titleNode, 0d);
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

    private void setWindowOpts(boolean[] opts) {
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
        Node exitNode = new Rectangle(BUTTON_WIDTH, BAR_HEIGHT, Color.RED);

        AnchorPane.setRightAnchor(exitNode, 0d);
        AnchorPane.setTopAnchor(exitNode, 0d);

        Node crossOne = new Rectangle(20d, 4d, Color.WHITE);
        Node crossTwo = new Rectangle(20d, 4d, Color.WHITE);
        crossOne.setRotate(45);
        crossTwo.setRotate(135);

        AnchorPane.setTopAnchor(crossOne, BAR_HEIGHT/2 *Math.sin(45d));
        AnchorPane.setRightAnchor(crossOne, (offset+1) * BUTTON_WIDTH/2 - 20d * Math.cos(45d));
        AnchorPane.setTopAnchor(crossTwo, BAR_HEIGHT/2 * Math.sin(45d));
        AnchorPane.setRightAnchor(crossTwo, (offset+1) * BUTTON_WIDTH/2 - 20d * Math.cos(45d));


        Node[] exitButtons = new Node[]{exitNode, crossOne, crossTwo};
        setOpacity(exitButtons, .1f, 1);
        for(Node n: exitButtons) {
            n.setOnMouseReleased(e -> Platform.exit());
        }
        this.getChildren().addAll(exitButtons);
    }

    private void makeMaxiB(int offset) {
        Node maxiBackNode = new Rectangle(BUTTON_WIDTH, BAR_HEIGHT, Color.GREEN);
        AnchorPane.setRightAnchor(maxiBackNode, offset * BUTTON_WIDTH);
        AnchorPane.setTopAnchor(maxiBackNode, 0d);

        Node maxiNode = new Rectangle(BUTTON_WIDTH - 10, BAR_HEIGHT - 5d, Color.BLACK);
        AnchorPane.setRightAnchor(maxiNode, offset * BUTTON_WIDTH + 5d);
        AnchorPane.setTopAnchor(maxiNode, 5d);

        Node[] maxiButtons = setOpacity(new Node[]{maxiBackNode, maxiNode}, .1f, 1);
        for(Node n: maxiButtons) {
            //TODO need to figure out how to add maximization
        }

        this.getChildren().addAll(maxiButtons);
    }

    private void makeMiniB(int offset) {
        Node miniBackNode = new Rectangle(BUTTON_WIDTH, BAR_HEIGHT, Color.BLUE);
        AnchorPane.setRightAnchor(miniBackNode, offset * BUTTON_WIDTH);
        AnchorPane.setTopAnchor(miniBackNode, 0d);

        Node miniNode = new Rectangle(BUTTON_WIDTH, 5d, Color.BLACK);
        AnchorPane.setRightAnchor(miniNode, offset * BUTTON_WIDTH);
        AnchorPane.setTopAnchor(miniNode, BAR_HEIGHT - 5);

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

    public static Pane getPane(Scene currentStage) {
        if(titleBar != null) {
            return titleBar;
        } else {
            titleBar = new TitleBarPane(currentStage);
            return titleBar;
        }
    }

    public static Pane getPane(String windowTitle, Scene currentStage) {
        if(titleBar != null && titleBar instanceof TitleBarPane) {
            ((TitleBarPane)titleBar).setTitle(windowTitle);
            return titleBar;
        } else {
            titleBar = new TitleBarPane(windowTitle, currentStage);
            return titleBar;
        }
    }

    public static Pane getPane(String windowTitle, boolean[] windowOpts, Scene currentStage) {
        if(titleBar != null && titleBar instanceof TitleBarPane) {
            ((TitleBarPane)titleBar).setTitle(windowTitle);
            ((TitleBarPane)titleBar).setWindowOpts(windowOpts);

            return titleBar;
        } else {
            titleBar = new TitleBarPane(windowTitle, windowOpts, currentStage);
            return titleBar;
        }
    }
}
