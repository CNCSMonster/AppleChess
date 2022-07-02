package model;

import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.event.MouseInputAdapter;

import controller.ClickController;

import java.awt.*;


public class BoardComponent extends JComponent{
    private BoardPoint boardPoint; //在棋盘上的相对位置，横纵坐标
    // private int size;   //棋盘中棋子或者空格组件的实际大小
    private Border border;
    
    protected ClickController clickController;    //棋子需要一个控制者的引用，它需要通知控制者

    private BoardComponentColor bgColor=BoardComponentColor.BACKGROUND; //背景颜色

    public BoardComponent(int boardX,int boardY,int size,ClickController clickController){
        super();
        border=BorderFactory.createLineBorder(Color.BLACK);
        setBorder(border);
        boardPoint=new BoardPoint(boardX, boardY);
        // this.size=size;
        setSize(size,size);

        this.clickController=clickController;

        //根据大小和在棋盘中的布局计算绝对布局中的位置
        int x=(boardX-1)*size;
        int y=(boardY-1)*size;
        setLocation(x,y);
       
        addMouseListener(new MouseInputAdapter(){
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("发生鼠标事件");
                if(e.getID()==MouseEvent.MOUSE_PRESSED){
                    System.out.println("点击棋子:"+boardPoint);
                    if(clickController!=null) clickController.handleClick(boardPoint);    //把点击信息传给棋盘控制者
                }
            }
        });

    }

    public BoardComponent(BoardPoint boardPoint,int size,ClickController clickController){
        this(boardPoint.getX(),boardPoint.getY(),size,clickController);
    }
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //填充底部颜色，填充为白色
        g.setColor(bgColor.getColor());
        g.fillRect(0, 0, getWidth(), getHeight());
        
    }

    public BoardPoint getBoardPoint(){
        return new BoardPoint(boardPoint.getX(), boardPoint.getY());
    }

    public void setBoardPoint(BoardPoint boardPoint) {
        this.boardPoint = boardPoint;
    }

    
    
    
    


}
