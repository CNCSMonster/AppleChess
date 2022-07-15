package aistrategy;

import java.util.ArrayList;
import java.util.List;

import model.BoardComponentColor;
import model.BoardPoint;
import view.ChessBoard;

public class RandomStrategy implements AIStrategy{




    @Override
    public BoardPoint getStep(ChessBoard chessBoard) {
        List<BoardPoint> points=chessBoard.getAvailablePoints();
        int index=(int)(Math.random()*points.size());
        return points.get(index);
    }
    
    
}
