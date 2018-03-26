package test;

import refmeister.entity.Library;
import refmeister.entity.Theme;
import refmeister.entity.Topic;

/**
 * Created by wesle on 3/25/2018.
 */
public class XMLSaveTest {
    public static void main(String[] args){
        Library lib = new Library("GenericLibrary", "A demo library");
        Topic top = lib.addTopic("Topic1", "Description");
        Theme t1 = top.addTheme("Theme1", "Desc");
        Theme t2 = top.addTheme("Theme2", "desc2");
        t1.addReference("Green Eggs and Ham", "Dr .Seuss");
        t2.addReference("1984", "George Orwell");

        System.out.println(lib.getSaveString());
    }
}
