package view;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import controller.ClickController;
import model.BoardComponent;
import model.BoardComponentColor;
import model.BoardPoint;
import model.Chess;
import model.EmptyPlace;

public class ChessBoard extends JPanel{

    private static final int numOfLines=8;
    private static int boardSize;
    private static int boardComponentSize;
    BoardComponent[][] boardComponents=new BoardComponent[numOfLines][numOfLines];
    private ClickController clickController;

    private int numOfWhiteChesses=2;
    private int numOfBlackChesse=2;

    @Override
    public void repaint() {
        super.repaint();

        
    }


    public ChessBoard(int size){

        //初始化棋盘

        //设置大小
        boardSize=size;
        boardComponentSize=boardSize/numOfLines;
        setSize(boardSize,boardSize);
        //设置棋盘边框
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        //设计组件布局为绝对布局
        setLayout(null);

        //设置可见性为真
        setVisible(true);
        
        clickController =new ClickController(this);

        //初始化棋盘棋子和空格组件
        initEmptyPlace();
        initChess();

        repaint();

    }

    //初始化棋盘棋子
    public void initChess(){
        //寻找到中间四个棋子的位置
        int lu=numOfLines/2    ;   //left and up       //左上角棋子坐标
        
        putChess(lu, lu,BoardComponentColor.WHITE);
        putChess(lu+1, lu+1, BoardComponentColor.WHITE);
        putChess(lu+1, lu, BoardComponentColor.BLACK);
        putChess(lu, lu+1, BoardComponentColor.BLACK);
    }

    //初始化棋盘空格
    public void initEmptyPlace(){
        for(int i=1;i<=numOfLines;i++){
            for(int j=1;j<=numOfLines;j++){
                putEmptyPlace(i, j);
            }
        }
    }





    
    /*
     * 
     * 在空格区域放上某个颜色棋子,如果该位置是空的，
     * 就放上棋子，如果是空格，就抛去空格，放上棋子
     * 如果该位置已经有棋子了，就不落子
     *
     *  传入的参数是实际上棋子的序号，实际上需要x对应下标x-1,序号y对应下标y-1*/
    public void putChess(int x,int y,BoardComponentColor chessColor){
        
        if(boardComponents[x-1][y-1]==null){
            boardComponents[x-1][y-1]=new Chess(x, y, boardComponentSize, chessColor,clickController);
            add(boardComponents[x-1][y-1]);
            numOfBlackChesse+=chessColor==BoardComponentColor.BLACK?1:0;
            numOfWhiteChesses+=chessColor==BoardComponentColor.BLACK?0:1;
        }
        else if(boardComponents[x-1][y-1] instanceof EmptyPlace){
            remove(boardComponents[x-1][y-1]);  //移除原本部件
            boardComponents[x-1][y-1]=new Chess(x, y, boardComponentSize, chessColor,clickController);
            add(boardComponents[x-1][y-1]);
            numOfBlackChesse+=chessColor==BoardComponentColor.BLACK?1:0;
            numOfWhiteChesses+=chessColor==BoardComponentColor.BLACK?0:1;
        }
        else{
            return;
        }
        
    }


    //在某个区域放上空格
    public void putEmptyPlace(int x,int y){
        if(boardComponents[x-1][y-1]==null){
            boardComponents[x-1][y-1]=new EmptyPlace(x, y, boardComponentSize,clickController);
            add(boardComponents[x-1][y-1]);
            return;
        }
        if(boardComponents[x-1][y-1] instanceof EmptyPlace){
            return ;    //如果已经是空格了，直接退出，不用重复放置
        }
        remove(boardComponents[x-1][y-1]);
        boardComponents[x-1][y-1]=new EmptyPlace(x, y, boardComponentSize,clickController);
        add(boardComponents[x-1][y-1]);
    }


    


    public BoardComponent[][] getBoardComponents() {
        return boardComponents;
    }

    //判断这个位置是否能够落子，如果能够落子，返回true,否则返回false
    public boolean ifAvailablePoint(BoardPoint boardPoint){
        if(boardComponents[boardPoint.getX()-1][boardPoint.getY()-1]!=null
        &&
        !(boardComponents[boardPoint.getX()-1][boardPoint.getY()-1] instanceof EmptyPlace)
        ){
            return false;
        }
        return true;
    }




    //判断如果落子在该处，会包夹的棋子
    public List<Chess> getClipChesses(Chess chess){
        List<Chess> chesses=new ArrayList<>();
        //往八个方向搜索

        //往下搜索
        chesses.addAll(sortClipChessesOneDirection(chess, 0, 1));

        //往上搜索
        chesses.addAll(sortClipChessesOneDirection(chess, 0, -1));

        //往左搜索
        chesses.addAll(sortClipChessesOneDirection(chess, -1, 0));
        
        //往右搜索
        chesses.addAll(sortClipChessesOneDirection(chess, 1, 0));

        //往左上搜索
        chesses.addAll(sortClipChessesOneDirection(chess, -1, -1));

        //往右上搜索
        chesses.addAll(sortClipChessesOneDirection(chess, 1, -1));

        //往左下搜索
        chesses.addAll(sortClipChessesOneDirection(chess, -1, 1));

        //往右下搜索
        chesses.addAll(sortClipChessesOneDirection(chess, 1, 1));
        return chesses;
    }


    //(xs,ys)为搜索的方向的方向向量
    public List<Chess> sortClipChessesOneDirection(Chess chess,int xs,int ys){
        List<Chess> chesses=new ArrayList<>();
        int x=chess.getBoardPoint().getX();
        int y=chess.getBoardPoint().getY();
        int i;
        //搜索的路径长度不会大于棋盘宽高,找到空格或者下一个棋子为止
        for(i=1;x+i*xs>0&&x+i*xs<=numOfLines&&y+i*ys>0&&y+i*ys<=numOfLines;i++){
            if(boardComponents[x+i*xs-1][y+i*ys-1] instanceof Chess
            &&((Chess)boardComponents[x+i*xs-1][y+i*ys-1]).getChessColor()!=chess.getChessColor()
            ){
                chesses.add((Chess)boardComponents[x+i*xs-1][y+i*ys-1]);
            }
            else{
                break;
            }
        }
        //如果搜索到边界而退出搜索的，那么没有没有可翻转的棋子
        if(
            x+i*xs==0||x+i*xs==numOfLines+1
            ||y+i*ys==0||y+i*ys==numOfLines+1
        )
            chesses.clear();
        else if(boardComponents[x+i*xs-1][y+i*ys-1] instanceof EmptyPlace)
            chesses.clear();
        return chesses;
    }

    
    



    //判断某个颜色的棋子是否还有棋可下，
    public boolean ifAvailableColor(BoardComponentColor chessColor){
        for(int i=0;i<numOfLines;i++){
            for(int j=0;j<numOfLines;j++){
                BoardPoint boardPoint=new BoardPoint(i+1, j+1);
                if(ifAvailablePoint(boardPoint)){
                    Chess mode=new Chess(boardPoint, 1, chessColor, null);
                    List<Chess> chesses=getClipChesses(mode);
                    if(chesses!=null&&chesses.size()!=0) return true;
                }
            }
        }
        return false;
    }
    
    //判断游戏是否结束
    public boolean isGameOver(){
        for(BoardComponent[] bcs:boardComponents){
            //只要还有棋子还是空棋，游戏就没有结束
            for(BoardComponent boardComponent:bcs){
                if(boardComponent instanceof EmptyPlace) return false;
            }
        }
        return true;
    }



    public int getNumOfWhiteChesses() {
        return numOfWhiteChesses;
    }

    public int getNumOfBlackChesse() {
        return numOfBlackChesse;
    }

}
