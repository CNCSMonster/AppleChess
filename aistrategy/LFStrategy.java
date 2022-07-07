package aistrategy;

import model.BoardPoint;
import view.ChessBoard;

/*
 * 地理位置优先策略
 * 角>边>吃子。
 * 如果能够占角，就在能够占角的走法中随机选一个
 * 如果能够占边，就在能够占边的走法中随机选一个
 * 否则在能够吃子的走法中选一个
 */
public class LFStrategy implements AIStrategy{

   

    @Override
    public BoardPoint getStep(ChessBoard chessBoard) {
        // TODO Auto-generated method stub
        return null;
    }
    


}
