package controller;

import view.ChessBoard;

public class AiPlayer {

    // 
    private BaseClickController controller;
    private ChessBoard chessBoard;

    public AiPlayer(ChessBoard chessBoard){
        this.chessBoard=chessBoard;
        controller=new BaseClickController(chessBoard);
        //初始化策略

    }


}
