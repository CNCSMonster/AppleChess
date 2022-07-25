package controller;

import model.BoardComponentColor;

public class LocalPlayer extends Player{
    //localPlayer使
    private Player player;


    public LocalPlayer(Player player){
        // super(null, null);
        if(player instanceof AIPlayer ||player instanceof HumanPlayer){
            this.player=player;
            this.chessBoard=player.chessBoard;
            this.controller=player.controller;
            this.playerColor=player.playerColor;
        } else
            try {
                throw new Exception("wrong in LocalPlayer Constructor");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    public LocalPlayer(BoardComponentColor playerColor, ChessBoard chessBoard) {
        super(playerColor, chessBoard);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void singleStep() {
        // TODO Auto-generated method stub
        player.singleStep();
        //TODO 获取上一步棋，保存在棋盘中

    }
    
}
