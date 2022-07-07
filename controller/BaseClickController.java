package controller;


import java.util.List;

import javax.swing.JOptionPane;

import model.BoardPoint;
import view.ChessBoard;


/*
 * 该控制者用于双人对战
 */
public class BaseClickController {
    private ChessBoard chessBoard;

    public BaseClickController(ChessBoard chessBoard){
        this.chessBoard=chessBoard;
    }

    //处理点击事件
    public void handleClick(BoardPoint boardPoint){
        //访问公共资源chessBoard.要加锁
        synchronized(chessBoard){
            //判断该位置是否可以下棋
            //如果该位置无空落子，则不反应
            if(!chessBoard.isAvailablePoint(boardPoint)){
                return;
            }
            List<BoardPoint> boardPoints=chessBoard.getClipBoardPoints(boardPoint, chessBoard.getCurrentColor());
            //如果该位置如果落子的话，无法翻棋，则不反应
            if(boardPoints==null||boardPoints.size()==0){
                return;
            }
            //否则，下棋
            chessBoard.putChess(boardPoint.getX(), boardPoint.getY(), chessBoard.getCurrentColor());
            for(BoardPoint boardPoint2:boardPoints){
                if(boardPoint2!=null) chessBoard.putChess(boardPoint2.getX(), boardPoint2.getY(), chessBoard.getCurrentColor());
            }
            chessBoard.repaint();   //操作完之后重绘制
            chessBoard.swapCurrentColor();
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
            //然后判断切换后的颜色是否无子可下,如果是，则切换颜色
            if(!chessBoard.ifAvailableColor(chessBoard.getCurrentColor())){
                String toShow=chessBoard.getCurrentColor()+"方无子可下";
                chessBoard.swapCurrentColor();
                toShow+=",轮到"+chessBoard.getCurrentColor()+"下棋!";
                JOptionPane.showMessageDialog(chessBoard,toShow);
            }
        }
    }


    
        


}
