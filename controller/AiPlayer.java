package controller;

import aistrategy.AIStrategy;
import model.BoardComponentColor;
import model.BoardPoint;
import view.ChessBoard;

public class AIPlayer extends Player{
    private AIStrategy aiStrategy;

    public AIPlayer(BoardComponentColor playeColor, ChessBoard chessBoard,AIStrategy aiStrategy) {
        super(playeColor, chessBoard);
        this.aiStrategy=aiStrategy;
    }

    
    //棋手单走一步的行为
    public void singleStep() {
        if (aiStrategy == null)
            return;
        BoardPoint boardPoint = aiStrategy.getStep(chessBoard);
        controller.handleClick(boardPoint);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        
    }
}
