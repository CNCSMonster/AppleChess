package model;

import java.awt.Graphics;

import controller.ClickController;

public class EmptyPlace extends BoardComponent{

    public EmptyPlace(int boardX, int boardY,int size,ClickController clickController) {
        super(boardX, boardY,size,clickController);
        //TODO Auto-generated constructor stub
    }
    
    //不需要重绘
    @Override
    protected void paintComponent(Graphics g) {
        // TODO Auto-generated method stub
        super.paintComponent(g);
        
    }


}
