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
     * 如果该位置已经有棋子了，就更改棋子的颜色
     *
     *  传入的参数是实际上棋子的序号，实际上需要x对应下标x-1,序号y对应下标y-1*/
    public void putChess(int x,int y,BoardComponentColor chessColor){
        if(boardComponents[x-1][y-1]==null){
            boardComponents[x-1][y-1]=new Chess(x, y, boardComponentSize, chessColor,clickController);
            add(boardComponents[x-1][y-1]);
            return;
        }
        if(boardComponents[x-1][y-1] instanceof EmptyPlace){
            remove(boardComponents[x-1][y-1]);  //移除原本部件
            boardComponents[x-1][y-1]=new Chess(x, y, boardComponentSize, chessColor,clickController);
            add(boardComponents[x-1][y-1]);
            return ;
        }
        ((Chess)boardComponents[x-1][y-1]).setChessColor(chessColor);
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

        return chesses;
    }

    //(xs,ys)为搜索的方向的方向向量
    public List<Chess> sortClipChessesOneDirection(Chess chess,int xs,int ys){
        List<Chess> chesses=new ArrayList<>();

        //搜索的路径长度不会大于棋盘宽高
        for(int i=0;i<numOfLines;i++){

        }


        return chesses;
    }


    


}
