package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.*;

import controller.ClickController;

public class Chess extends BoardComponent{

    //棋子需要保存棋子自身的颜色
    private BoardComponentColor chessColor;


    public Chess(int boardX, int boardY, int size,BoardComponentColor chessColor,ClickController clickController) {
        super(boardX, boardY, size,clickController);
        this.chessColor=chessColor;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //使用父中同名方法绘制了背景之后，接下来绘制的是棋子

        //绘制棋子
        Graphics2D g2=(Graphics2D)g;
        BasicStroke basicStroke=new BasicStroke(5.0f);
        g2.setStroke(basicStroke);
        g2.setColor(Color.BLACK);    //黑线描边
        g2.drawOval(0, 0, getWidth(),getHeight());
        
        // //填充棋子
        g2.setColor(chessColor.getColor());
        g2.fillOval(0, 0,getWidth(), getHeight());

    }   

    public void setChessColor(BoardComponentColor chessColor) {
        this.chessColor = chessColor;

    }

    public BoardComponentColor getChessColor() {
        return chessColor;
    }
    

    //翻转棋子
    public void swapColor(){
        this.chessColor=(chessColor==BoardComponentColor.BLACK?BoardComponentColor.WHITE:BoardComponentColor.BLACK);
        repaint();
    }
    
}
