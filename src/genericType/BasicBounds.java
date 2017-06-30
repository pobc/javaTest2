package genericType;

import java.awt.*;

/**
 *
 * Created by jiang on 17/6/8.
 */
    interface HasColor{
        java.awt.Color getColor();
    }
    class Colored<T extends HasColor>{
        T item;
        Colored(T item){
            this.item = item;
        }
        java.awt.Color color(){
           return item.getColor();
        }
    }
    class Dimension{public int x,y,z;}
    // this won't work --  class must be first,then interfaces
    // class ColoredDimension<T extends HasColor & Dimension>
    class ColoredDimension<T extends Dimension & HasColor>{
        T item;
        ColoredDimension(T item){this.item = item;}
        T getItem(){return item;}
        java.awt.Color color(){return item.getColor();}
        int getX(){return item.x;}
        int getY(){return item.y;}
        int getZ(){return item.z;}

    }
   interface Weight{int weight();}

    class Solid<T extends Dimension & HasColor & Weight>{
        T item;
        Solid(T item){this.item = item;}
        T getItem(){return item;}
        java.awt.Color color(){return item.getColor();}
        int getX(){return item.x;}
        int getY(){return item.y;}
        int getZ(){return item.z;}
        int weight(){return item.weight();}

    }
    class Bounded extends Dimension implements HasColor, Weight{

        public Color getColor() {
            return null;
        }
        public int weight() {
            return 0;
        }
    }
    public class BasicBounds{
        public static void main(String[] args){
            Solid<Bounded> solid = new Solid<Bounded>(new Bounded());
            solid.color();
            solid.getY();
            solid.weight();
            System.out.println(1);
        }
    }






