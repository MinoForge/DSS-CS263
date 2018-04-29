package refmeister.display.specialHandlers;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by wesle on 4/28/2018
 */
public class ImageBuilder {
    public static Node buildImage(InputStream input, double scale){
        ImageView iv =  new ImageView(new Image(input));
        iv.setScaleX(scale);
        iv.setScaleY(scale);
        return iv;
    }
}
