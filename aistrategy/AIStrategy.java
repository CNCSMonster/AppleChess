package aistrategy;

import controller.ChessBoard;
import model.BoardComponent;
import model.BoardPoint;

public interface AIStrategy {

    //获取一种该策略下棋盘的走法
    public BoardPoint getStep(ChessBoard chessBoard);



}
