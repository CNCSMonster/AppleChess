package aistrategy;

import model.BoardComponent;
import model.BoardPoint;
import view.ChessBoard;

public interface AIStrategy {

    //获取一种该策略下棋盘的走法
    public BoardPoint getStep(ChessBoard chessBoard);



}
