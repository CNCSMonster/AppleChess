package controller;

import java.net.Socket;
import java.util.concurrent.ExecutionException;

import model.BoardComponentColor;
import view.ChessBoard;

public class RemotePlayer extends Player{
    private Socket socket;

    public RemotePlayer(BoardComponentColor playerColor,ChessBoard chessBoard,Socket socket){
        super(playerColor,chessBoard);
        if(socket==null)
            try {
                throw new Exception("wrong ,the socket of RemotePlayer can't be wrong");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        this.socket=socket;
    }


    public RemotePlayer(BoardComponentColor playerColor, ChessBoard chessBoard) {
        super(playerColor, chessBoard);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void singleStep() {
        // TODO Auto-generated method stub
        //远程玩家需要封锁棋盘，防止被本地触发



    }
    
}
