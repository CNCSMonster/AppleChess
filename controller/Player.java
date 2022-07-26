package controller;

import java.util.List;

import model.BoardComponentColor;
import model.BoardPoint;
import model.ChessBoard;

/*
 * 玩家角色基础类型，总共两种:人类玩家和ai玩家
 */
public abstract class Player {

    
    //棋手的阵营，或者说棋手所执棋子的颜色
    protected BoardComponentColor playerColor;

    //棋手通过controller控制棋盘
    protected BaseClickController controller;

    //棋手需要观察棋盘
    protected ChessBoard chessBoard;

    //中止下棋标志
    private Boolean ifStop=false;

    public Player(){
        
    }

    public Player(BoardComponentColor playerColor,ChessBoard chessBoard){
        this.playerColor=playerColor;
        this.chessBoard=chessBoard;
        controller=new BaseClickController(chessBoard);
    }

    //玩家开始观察棋盘下棋
    public void play(){
        ifStop=false;
        new Thread(()->{
            while(true){
                synchronized(ifStop){
                    if(ifStop) break;
                }
                synchronized(chessBoard){
                    if(chessBoard.isGameOver()) break;
                    if(chessBoard.getCurrentColor()!=playerColor) continue;
                    singleStep();
                }
            }
        }).start();
    }

    //玩家停止下棋
    public void stop(){
        synchronized(ifStop){
            ifStop=true;
        }
    }

    //棋手单走一步的行为
    public abstract void singleStep();
    
    
}
