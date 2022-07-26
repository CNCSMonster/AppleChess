package controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.ExecutionException;

import model.BoardComponentColor;
import model.BoardPoint;
import model.ChessBoard;

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

    @Override
    public void singleStep() {
        try {
            //远程玩家下棋的时候禁止本地人类玩家操作，尤其是通过点击事件操作
            //通过对chessBoard的锁禁止本地的电脑玩家操作
            chessBoard.setEnabled(false);
            InputStream inputStream=socket.getInputStream();
            InputStreamReader ipsr=new InputStreamReader(inputStream);
            BufferedReader bfr=new BufferedReader(ipsr);
            String s=bfr.readLine();
            System.out.println("remote play"+s);
            BoardPoint boardPoint=BoardPoint.valueOf(s);
            controller.handleClick(boardPoint);
            // socket.shutdownInput();

        } catch (Exception e) {
            //
            System.out.println("wrong in remote play");
        }
    }
    
}
