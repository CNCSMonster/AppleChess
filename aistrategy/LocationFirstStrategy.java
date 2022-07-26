package aistrategy;

import java.util.ArrayList;
import java.util.List;

import model.BoardComponentColor;
import model.BoardPoint;
import model.ChessBoard;

/*
 * 地理位置优先策略
 * 角>边>吃子。
 * 如果能够占角，就在能够占角的走法中随机选一个
 * 如果能够占边，就在能够占边的走法中随机选一个
 * 否则在能够吃子的走法中选一个
 */
public class LocationFirstStrategy implements AIStrategy{

   //判断棋盘上某个位置棋子的价值,根据位置来判，角>边>中间
   public static int valueOfStep(BoardPoint boardPoint){
    //如果是中间
    if(
        (boardPoint.getX()>1&&boardPoint.getX()<ChessBoard.numOfLines)
        &&(boardPoint.getY()>1&&boardPoint.getY()<ChessBoard.numOfLines)
    ){
        return 1;
    }
    //如果是角
    if(
        (boardPoint.getX()==1||boardPoint.getX()==ChessBoard.numOfLines)
        &&(boardPoint.getY()==1||boardPoint.getY()==ChessBoard.numOfLines)
    ){
        return 3;
    }
    //剩下的情况只可能是边
    return 2;
}


    @Override
    public BoardPoint getStep(ChessBoard chessBoard) {
        List<BoardPoint> term=new ArrayList<>();
        List<BoardPoint> points=chessBoard.getAvailablePoints();
        //统计能够翻转的棋子最多的
        BoardComponentColor currentColor=chessBoard.getCurrentColor();
        int value=0;
        for(BoardPoint boardPoint:points){
            int curValue=valueOfStep(boardPoint);
            if(
               curValue>value
            ){
                value=curValue;
                term.clear();
                term.add(boardPoint);
            }
            else if(curValue==value){
                term.add(boardPoint);
            }
        }
        int index=(int)(Math.random()*term.size());
        return term.get(index);
    }
    


}
