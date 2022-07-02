package controller;


import java.util.List;

import javax.swing.JOptionPane;

import model.BoardComponentColor;
import model.BoardPoint;
import model.Chess;
import view.ChessBoard;

public class ClickController {
    private ChessBoard chessBoard;

    private BoardComponentColor currentColor=BoardComponentColor.BLACK;


    public ClickController(ChessBoard chessBoard){
        this.chessBoard=chessBoard;
    }

    //处理点击事件
    public void handleClick(BoardPoint boardPoint){
        //判断该位置是否可以下棋
        //然后判断当前该颜色是否还能落子
        if(!chessBoard.ifAvailableColor(currentColor)){
            JOptionPane.showMessageDialog(chessBoard,currentColor+"没有翻棋机会，自动切换行棋方!");
            return;
        }

        //如果该位置已经有棋了,不能下
        if(!chessBoard.ifAvailablePoint(boardPoint)){
            System.out.println(boardPoint+"已经有棋了");
            return ;    //如果不能落子，直接退出
        }


        Chess chess=new Chess(boardPoint.getX(),boardPoint.getY(),1,currentColor,this);
        List<Chess> chesses=chessBoard.getClipChesses(chess);
        //如果没有办法捕获棋子,则不能下
        if(chesses==null||chesses.size()==0){
            System.out.println(boardPoint+"处落子不能翻棋，所以不能下");
            return;
        }
        //否则下棋，并翻转包夹的棋子
        chessBoard.putChess(boardPoint.getX(),boardPoint.getY(), currentColor);
        for(Chess chess2:chesses){
            chess2.swapColor();
        }
        chessBoard.repaint();   //操作完之后重绘制
        swapCurrentColor();

        //判断是否游戏结束!
        //如果游戏结束了，判断得分和胜负
        if(chessBoard.isGameOver()){
            int nb=chessBoard.getNumOfBlackChesse();
            int nw=chessBoard.getNumOfWhiteChesses();
            String show;
            //如果平局
            if(nb==nw){
                show=",平局!";
            }   
            //如果黑方胜利
            else if(nb>nw){
                show="，黑方获胜!";
            }
            //如果白方胜利
            else{
                show="，白方获胜!";
            }
            show="黑棋"+nb+"子，白棋"+nw+"子"+show;
            JOptionPane.showMessageDialog(chessBoard, show);
        }

    }

    public void swapCurrentColor(){
        currentColor=(currentColor==BoardComponentColor.BLACK?BoardComponentColor.WHITE:BoardComponentColor.BLACK);
    }



}
