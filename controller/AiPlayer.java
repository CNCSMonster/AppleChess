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
    public void singleStep(){
        BoardPoint boardPoint=aiStrategy.getStep(chessBoard);
        controller.handleClick(boardPoint);
    }
}
