package controller;

import aistrategy.AIStrategy;
import model.BoardComponentColor;
import model.BoardPoint;
import model.ChessBoard;

public class AIPlayer extends Player{
    private AIStrategy aiStrategy;
    private static int thinkTime=3000;

    public AIPlayer(BoardComponentColor playeColor, ChessBoard chessBoard,AIStrategy aiStrategy) {
        super(playeColor, chessBoard);
        this.aiStrategy=aiStrategy;
    }

    
    //棋手单走一步的行为
    public void singleStep() {
        chessBoard.setEnabled(false);
        try {
            Thread.sleep(thinkTime);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (aiStrategy == null)
            return;
        BoardPoint boardPoint = aiStrategy.getStep(chessBoard);
        controller.handleClick(boardPoint);
    }


    public static void main(String[] args) {
        
    }
}
