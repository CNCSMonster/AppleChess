package controller;

import java.util.Currency;
import java.util.List;

import model.BoardComponent;
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
        
        if(!chessBoard.ifAvailablePoint(boardPoint)){
            System.out.println("不能落子");
            return ;    //如果不能落子，直接退出
        }

        //下棋到该处   
        chessBoard.putChess(boardPoint.getX(), boardPoint.getY(),currentColor);

        //并翻转与它夹着的棋子
        List<Chess> chesses=chessBoard.getClipChesses(boardPoint);
        if(chesses==null||chesses.size()==0){
            chessBoard.putEmptyPlace(boardPoint.getX(), boardPoint.getY());
            chessBoard.repaint();
            return;
        }
        for(Chess chess:chesses){
            chess.turnUpSide();
        }
        chessBoard.repaint();   //操作完之后重绘制
        swapCurrentColor();
    }

    public void swapCurrentColor(){
        currentColor=(currentColor==BoardComponentColor.BLACK?BoardComponentColor.WHITE:BoardComponentColor.BLACK);
    }

}
