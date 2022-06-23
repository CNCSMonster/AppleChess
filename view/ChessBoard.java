package view;

import java.awt.Color;
import java.awt.Event;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicTabbedPaneUI.MouseHandler;

import controller.ClickController;
import model.BoardComponent;
import model.BoardComponentColor;
import model.BoardPoint;
import model.Chess;
import model.EmptyPlace;

public class ChessBoard extends JPanel{

    private static final int numOfLines=8;
    private static int boardSize;
    private static int boardComponentSize;
    BoardComponent[][] boardComponents=new BoardComponent[numOfLines][numOfLines];
    private ClickController clickController;

    @Override
    public void repaint() {
        // TODO Auto-generated method stub
        super.repaint();
        
    }


    public ChessBoard(int size){

        //初始化棋盘

        //设置大小
        boardSize=size;
        boardComponentSize=boardSize/numOfLines;
        setSize(boardSize,boardSize);
        //设置棋盘边框
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        //设计组件布局为绝对布局
        setLayout(null);

        //设置可见性为真
        setVisible(true);
        
        clickController =new ClickController(this);

        //初始化棋盘棋子和空格组件
        initEmptyPlace();
        initChess();

        repaint();

    }

    //初始化棋盘棋子
    public void initChess(){
        //寻找到中间四个棋子的位置
        int lu=numOfLines/2    ;   //left and up       //左上角棋子坐标
        
        putChess(lu, lu,BoardComponentColor.WHITE);
        putChess(lu+1, lu+1, BoardComponentColor.WHITE);
        putChess(lu+1, lu, BoardComponentColor.BLACK);
        putChess(lu, lu+1, BoardComponentColor.BLACK);
    }

    //初始化棋盘空格
    public void initEmptyPlace(){
        for(int i=1;i<=numOfLines;i++){
            for(int j=1;j<=numOfLines;j++){
                putEmptyPlace(i, j);
            }
        }
    }





    
    /*
     * 
     * 在空格区域放上某个颜色棋子,如果该位置是空的，
     * 就放上棋子，如果是空格，就抛去空格，放上棋子
     * 如果该位置已经有棋子了，就更改棋子的颜色
     *
     *  传入的参数是实际上棋子的序号，实际上需要x对应下标x-1,序号y对应下标y-1*/
    public void putChess(int x,int y,BoardComponentColor chessColor){
        if(boardComponents[x-1][y-1]==null){
            boardComponents[x-1][y-1]=new Chess(x, y, boardComponentSize, chessColor,clickController);
            add(boardComponents[x-1][y-1]);
            return;
        }
        if(boardComponents[x-1][y-1] instanceof EmptyPlace){
            remove(boardComponents[x-1][y-1]);  //移除原本部件
            boardComponents[x-1][y-1]=new Chess(x, y, boardComponentSize, chessColor,clickController);
            add(boardComponents[x-1][y-1]);
            return ;
        }
        ((Chess)boardComponents[x-1][y-1]).setChessColor(chessColor);
    }


    //在某个区域放上空格
    public void putEmptyPlace(int x,int y){
        if(boardComponents[x-1][y-1]==null){
            boardComponents[x-1][y-1]=new EmptyPlace(x, y, boardComponentSize,clickController);
            add(boardComponents[x-1][y-1]);
            return;
        }
        if(boardComponents[x-1][y-1] instanceof EmptyPlace){
            return ;    //如果已经是空格了，直接退出，不用重复放置
        }
        remove(boardComponents[x-1][y-1]);
        boardComponents[x-1][y-1]=new EmptyPlace(x, y, boardComponentSize,clickController);
        add(boardComponents[x-1][y-1]);
    }


    //获取某个位置的棋子以及他与其他位置的棋子之间夹着的棋子的列表
    public List<Chess> getClipChesses(BoardPoint boardPoint){
        if(!(boardComponents[boardPoint.getX()-1][boardPoint.getY()-1] instanceof Chess)){
            return null;    //如果该位置不是棋子，返回null
        }
        List<Chess> out=new ArrayList<>();
        
        List<Chess> term=new ArrayList<>();

        //总共四种夹方向，横竖各一种，斜着又两种，共四条线，要做8次检查
        BoardComponentColor chessColor=((Chess)boardComponents[boardPoint.getX()-1][boardPoint.getY()-1]).getChessColor();

        //往上找到最近的同色棋子的坐标，空格跳出
        int i;
        for(i=boardPoint.getY();i<numOfLines;i++){
            if(boardComponents[boardPoint.getX()-1][i] instanceof Chess){
                Chess chess=(Chess)boardComponents[boardPoint.getX()-1][i];
                if(chess.getChessColor()==chessColor){
                    break;
                }else{
                    term.add(chess);
                    continue;
                }
            }
            break;  //如果该位置不是棋子，应该是空格，则跳出循环
        }
        if(
            i==numOfLines||
            !(boardComponents[boardPoint.getX()-1][i] instanceof Chess)||
            ((Chess)boardComponents[boardPoint.getX()-1][i]).getChessColor()!=chessColor

        ){  //如果不是棋子,又或者棋子颜色不对
            term.clear();
        }else{
            out.addAll(term);
            term.clear();
        }



        //往下找到最近的同色棋子坐标，空格跳出
        for(i=boardPoint.getY()-2;i>=0;i--){
            if(boardComponents[boardPoint.getX()-1][i] instanceof Chess){
                Chess chess=(Chess)boardComponents[boardPoint.getX()-1][i];
                if(chess.getChessColor()==chessColor){
                    break;
                }else{
                    term.add(chess);
                    continue;
                }
            }
            break;  //如果该位置不是棋子，应该是空格，则跳出循环
        }
        if(
            i==numOfLines||
            !(boardComponents[boardPoint.getX()-1][i] instanceof Chess)||
            ((Chess)boardComponents[boardPoint.getX()-1][i]).getChessColor()!=chessColor

        ){  //如果不是棋子,又或者棋子颜色不对
            term.clear();
        }else{
            out.addAll(term);
            term.clear();
        }

        //往左找到最近的同色棋子坐标，空格跳出
        for(i=boardPoint.getX()-2;i>=0;i--){
            if(boardComponents[i][boardPoint.getY()-1] instanceof Chess){
                Chess chess=(Chess)boardComponents[i][boardPoint.getY()-1];
                if(chess.getChessColor()==chessColor){
                    break;
                }else{
                    term.add(chess);
                    continue;
                }
            }
            break;  //如果该位置不是棋子，应该是空格，则跳出循环
        }
        //仅仅该位置是同色棋子时才可能有包夹的棋子
        if(
            i==numOfLines||
            !(boardComponents[i][boardPoint.getY()-1] instanceof Chess)||
            ((Chess)boardComponents[i][boardPoint.getY()-1]).getChessColor()!=chessColor

        ){  //如果不是棋子,又或者棋子颜色不对
            term.clear();
        }else{
            out.addAll(term);
            term.clear();
        }


        //往右找到最近的同色棋子坐标，空格跳出
        for(i=boardPoint.getX();i<numOfLines;i++){
            if(boardComponents[i][boardPoint.getY()-1] instanceof Chess){
                Chess chess=(Chess)boardComponents[i][boardPoint.getY()-1];
                if(chess.getChessColor()==chessColor){
                    break;
                }else{
                    term.add(chess);
                    continue;
                }
            }
            break;  //如果该位置不是棋子，应该是空格，则跳出循环
        }
        if(
            i==numOfLines||
            !(boardComponents[i][boardPoint.getY()-1] instanceof Chess)||
            ((Chess)boardComponents[i][boardPoint.getY()-1]).getChessColor()!=chessColor

        ){  //如果不是棋子,又或者棋子颜色不对
            term.clear();
        }else{
            out.addAll(term);
            term.clear();
        }


        //检查该点到左上，

        //检查该点到右上


        //检查该点到左下
        

        //检查该点到右下









        return out;
    }
  
    public BoardComponent[][] getBoardComponents() {
        return boardComponents;
    }

    //判断这个位置是否能够落子，如果能够落子，返回true,否则返回false
    public boolean ifAvailablePoint(BoardPoint boardPoint){
        if(boardComponents[boardPoint.getX()-1][boardPoint.getY()-1]!=null
        &&
        !(boardComponents[boardPoint.getX()-1][boardPoint.getY()-1] instanceof EmptyPlace)
        ){
            return false;
        }
        return true;
    }

    


}
