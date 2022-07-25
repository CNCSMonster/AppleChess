package aistrategy;

import java.util.ArrayList;
import java.util.List;

import controller.ChessBoard;
import model.BoardComponentColor;
import model.BoardPoint;

/*
 * 翻棋优先策略。
 * 会在能够翻转最多敌人棋子的走法中随机选择一个
 * 也就是Swap First Strategy
 * 
 * 
 */
public class SwapFirstStrategy implements AIStrategy{

    @Override
    public BoardPoint getStep(ChessBoard chessBoard) {
        List<BoardPoint> term=new ArrayList<>();
        List<BoardPoint> points=chessBoard.getAvailablePoints();
        //统计能够翻转的棋子最多的
        BoardComponentColor currentColor=chessBoard.getCurrentColor();
        int len=0;
        for(BoardPoint boardPoint:points){
            List<BoardPoint> clipPoints=chessBoard.getClipBoardPoints(boardPoint, currentColor);
            int curLen=clipPoints.size();
            if(
               curLen>len
            ){
                len=curLen;
                term.clear();
                term.add(boardPoint);
            }
            else if(curLen==len){
                term.add(boardPoint);
            }
        }
        int index=(int)(Math.random()*term.size());
        return term.get(index);
    }
    
}
