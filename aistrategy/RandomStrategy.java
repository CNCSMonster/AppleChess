package aistrategy;

import java.util.List;

import model.BoardPoint;
import view.ChessBoard;

public class RandomStrategy implements AIStrategy{

    @Override
    public BoardPoint getStep(ChessBoard chessBoard) {
        //走棋
        List<BoardPoint> points=chessBoard.getAvailablePoints();
        int len=points.size();
        //随机获取一步
        int random=(int)(Math.random()*len);

        return points.get(random) ;
    }
    
    
}
