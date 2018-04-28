package refmeister.display.elements;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wesle on 4/28/2018
 */
public class InformationPane extends TabPane {
    private static InformationPane instance;

    private InformationPane(){
        super();
    }


    public List<Node> createTabs(String... titles){
        ArrayList<Node> panes = new ArrayList<>();

        Color[] colors = {Color.KHAKI, Color.CHOCOLATE};

        for(int i = 0; i < titles.length; i++){
            String title = titles[i];
            Pane p = new Pane();

            //todo remove penis
            Ellipse e = new Ellipse(45, 80, 10, 50);
            e.setFill(colors[i]);
            p.getChildren().addAll(new Circle(30, 120, 15, colors[i]), new Circle(60,120, 15,
                            colors[i]), e);
            Tab t = new Tab(title, p);
            t.setClosable(false);
            this.getTabs().add(t);
            //p.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
            panes.add(p);

        }

        return panes;
    }

    public static InformationPane getInstance() {
        if(instance == null)
            instance = new InformationPane();
        return instance;
    }
}
