package enum2;

import java.util.EnumSet;
import java.util.Set;

/**
 * Created by jiang on 17/11/15.
 */
public class Text {
    public enum Style{BOLD,ITALIC,UNDERLINE,STRIKETHROUGH}
    public void applyStyles(Set<Style> styles){
        for (Style style : styles) {
            System.out.println(style);
        }
    }

    public static void main(String[] args) {
        Text t = new Text();
        t.applyStyles(EnumSet.of(Style.BOLD,Style.ITALIC));
    }
}
