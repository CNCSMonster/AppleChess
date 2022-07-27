package model;

import java.awt.*;


//使用ChessColor对棋子的颜色进行封装
public enum BoardComponentColor {
    BLACK(Color.BLACK),WHITE(Color.pink),BACKGROUND(Color.WHITE);  //定义枚举成员常量

    //枚举类型的成员的
    private Color color;
    
    private BoardComponentColor(Color color){
        this.color=color;
    }
    public Color getColor(){
        return color;
    }

}
