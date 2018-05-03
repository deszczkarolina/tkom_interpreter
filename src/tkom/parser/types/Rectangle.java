package tkom.parser.types;

import tkom.parser.Scope;
import tkom.parser.Value;
import tkom.parser.statements.FunctionDefinition;

import java.util.HashMap;

/**
 * Created by karolina on 03.05.18.
 */
public class Rectangle extends Value {

    private int x;
    private int y;
    private int width;
    private int length;
    private int area;
    private int perimeter;

    public Rectangle(int x, int y, int width, int length) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.length = length;
        calculateAreaAndPerimeter();
    }

    public void calculateAreaAndPerimeter(){
        this.area = width*length;
        this.perimeter = 2*width+ 2*length;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
        calculateAreaAndPerimeter();
    }

    public void setLength(int length) {
        this.length = length;
        calculateAreaAndPerimeter();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public int getArea() {
        return area;
    }

    public int getPerimeter() {
        return perimeter;
    }

    public Rectangle add(Rectangle other){
        int x,y,width,length;
        if (this.x <= other.x) {
            x = this.x;
            length = Math.max(other.x - this.x + other.length, this.x + this.length);
        }
        else {
            x = other.x;
            length = Math.max(this.x - other.x + this.length, other.x + other.length);
        }
        if (this.y <= other.y) {
            y = this.y;
            width = Math.max(other.y - this.y + other.width, this.y + this.width);
        }
        else {
            y = other.y;
            width = Math.max(this.y - other.y + this.width, other.y + other.width);
        }
       return new Rectangle(x,y,width,length);
    }

    public Rectangle intersect(Rectangle other){
        int x,y, tmpX, tmpY, width,length;
        if (this.x < (other.x + other.length) && (this.x + this.length) > other.x &&
                this.y < (other.y + other.width) && (this.y + this.width) > other.y)
        {
           x = Math.max(this.x, other.x);
           y = Math.max(this.y, other.y);
           tmpX = Math.min(this.x + this.length, other.x + other.length);
           tmpY = Math.min(this.y + this.width, other.y + other.width);
           length = tmpX - x;
           width = tmpY - y;
            return new Rectangle(x,y,width,length);
        }
        else {
             return new Rectangle(0,0,0,0);
        }
    }

    public Rectangle scale(int c){
        width *= c;
        length *= c;
        calculateAreaAndPerimeter();
        return new Rectangle(this.x, this.y, width, length);
    }

    public int compareRectangles(Rectangle other){
        if (this.area == other.area)
            return 0;
        if (this.area < other.area)
            return -1;
        else
            return 1;
    }


    @Override
    public boolean execute(Scope scope, HashMap<String, FunctionDefinition> functions) throws Exception {
        return true;
    }

    @Override
    public Object getValue() {
        return this;
    }

    @Override
    public Type getType() {
        return Type.Rectangle;
    }
}