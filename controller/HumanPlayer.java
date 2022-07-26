package controller;

import model.BoardComponentColor;
import model.ChessBoard;

public class HumanPlayer extends Player{

    //中止下棋标志
    private Boolean ifStop=false;

    public HumanPlayer(BoardComponentColor playeColor, ChessBoard chessBoard) {
        super(playeColor, chessBoard);

    }

    //TODO 人类棋手单步要做的事情
    @Override
    public void singleStep() {
        //让棋盘处于可以点击状态
        chessBoard.setEnabled(true);
        // chessBoard.notifyAll();
        //等待一定的时间等待人类作出反应
        while(true){
            //如果人类作出了操作棋盘颜色改变则退出棋盘
            if(chessBoard.getCurrentColor()!=playerColor){
                break;
            }
            //否则睡眠30ms
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

   
    public static void main(String[] args) {
        
    }
    
    
}
