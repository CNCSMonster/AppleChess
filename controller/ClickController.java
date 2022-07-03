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
        //如果该位置无空落子，则不反应
        if(!chessBoard.isAvailablePoint(boardPoint)){
            return;
        }
        List<BoardPoint> boardPoints=chessBoard.getClipBoardPoints(boardPoint, currentColor);
        //如果该位置如果落子的话，无法翻棋，则不反应
        if(boardPoints==null||boardPoints.size()==0){
            return;
        }
        //否则，下棋
        chessBoard.putChess(boardPoint.getX(), boardPoint.getY(), currentColor);
        for(BoardPoint boardPoint2:boardPoints){
            if(boardPoint2!=null) chessBoard.putChess(boardPoint2.getX(), boardPoint2.getY(), currentColor);
        }
        chessBoard.repaint();   //操作完之后重绘制
        swapCurrentColor();
        //每次下棋后判断是否游戏结束!
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
            return;
        }
        //然后判断切换后的颜色是否无子可下
        if(!chessBoard.ifAvailableColor(currentColor)){
            String toShow=currentColor+"方无子可下";
            swapCurrentColor();
            toShow+=",轮到"+currentColor+"下棋!";
            JOptionPane.showMessageDialog(chessBoard,toShow);
        }

    }

    public void swapCurrentColor(){
        currentColor=(currentColor==BoardComponentColor.BLACK?BoardComponentColor.WHITE:BoardComponentColor.BLACK);
    }



}
