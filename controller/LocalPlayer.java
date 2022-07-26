package controller;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.naming.ldap.SortKey;

import model.BoardComponentColor;
import model.BoardPoint;
import model.ChessBoard;

public class LocalPlayer extends Player{
    //localPlayer使
    private Socket socket;
    private Player player;

    public LocalPlayer(Player player,Socket socket){
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
        if(socket!=null) this.socket=socket;
        else
            try {
                throw new Exception("null argument wrong in LocalPlayer's constructor");
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
        //TODO 获取上一步棋，并传递给远程玩家
        BoardPoint boardPoint=chessBoard.lastStep();
        try {
            OutputStream ops=socket.getOutputStream();
            BufferedWriter bfw=new BufferedWriter(new OutputStreamWriter(ops));
            System.out.println("push "+chessBoard.lastStep().toString());
            bfw.write(chessBoard.lastStep().toString()+"\n");
            bfw.flush();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            System.out.println("push to remote wrong");
            e1.printStackTrace();
        }
    }
    
}
