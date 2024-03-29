package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.sql.rowset.spi.SyncResolver;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controller.BaseClickController;
import controller.HumanPlayer;
import controller.Player;

public class ChessBoard extends JPanel{

    public static final int numOfLines=8;
    private static int boardSize;
    private static int boardComponentSize;
    BoardComponent[][] boardComponents=new BoardComponent[numOfLines][numOfLines];
    private BaseClickController clickController;
    private List<String> boardVersions=new ArrayList<>();
    

    private BoardComponentColor currentColor=BoardComponentColor.BLACK;
    private int numOfWhiteChesses=0;
    private int numOfBlackChesse=0;

    @Override
    public void repaint() {
        super.repaint();

        
    }
    //初始化棋盘，如果要重启游戏，应该使用该方法
    public void initBoard(){
        //初始化棋盘
        //设置初始棋子数量
        numOfBlackChesse=0;
        numOfWhiteChesses=0;
        //初始化开始棋手
        currentColor=BoardComponentColor.BLACK;
        //初始化棋盘棋子和空格组件
        initEmptyPlace();
        initChess();
        boardVersions.clear();
        boardVersions.add(toString());  //初始棋局版本为开始时的棋局信息
        repaint();
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
        clickController =new BaseClickController(this);
        initBoard();
    }

    

    //用来适应窗口大小变化,棋盘大小改变后每个棋子的大小也都要改变
    public void setSize(int size){
        this.setSize(size, size);
        boardSize=size;
        boardComponentSize=size/numOfLines;
        for(BoardComponent[] bcs:boardComponents){
            if(bcs==null) continue;
            for(BoardComponent boardComponent:bcs){
                if(boardComponent==null) continue;
                boardComponent.setSize(boardComponentSize, boardComponentSize);
            }
        }
    }


    @Override
    public void setEnabled(boolean enabled) {
        // TODO Auto-generated method stub
        super.setEnabled(enabled);
        for(BoardComponent[] bcs:boardComponents){
            for(BoardComponent boardComponent:bcs){
                if(boardComponent!=null) boardComponent.setEnabled(enabled);
            }
        }
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





    
    /*在某个区域落子或者翻转
     * 
     * 在空格区域放上某个颜色棋子,如果该位置是空的，
     * 就放上棋子，如果是空格，就抛去空格，放上棋子
     * 如果该位置已经有棋子了，如果是同色棋子，就不操作，如果是异色棋子，就翻转
     *
     *  传入的参数是实际上棋子的序号，实际上需要x对应下标x-1,序号y对应下标y-1*/
    public void putChess(int x,int y,BoardComponentColor chessColor){
        //如果是在空处下新的棋子，则仅仅对应颜色棋子数量+1
        if(boardComponents[x-1][y-1]==null||boardComponents[x-1][y-1] instanceof EmptyPlace){
            if(boardComponents[x-1][y-1]!=null) remove(boardComponents[x-1][y-1]);  //移除原本部件
            boardComponents[x-1][y-1]=new Chess(x, y, boardComponentSize, chessColor,clickController);
            add(boardComponents[x-1][y-1]);
            numOfBlackChesse+=chessColor==BoardComponentColor.BLACK?1:0;
            numOfWhiteChesses+=chessColor==BoardComponentColor.BLACK?0:1;
        }
        //如果是翻转棋子，一个棋子数量增加，另一个棋子数量就减少
        else if(((Chess)boardComponents[x-1][y-1]).getChessColor()!=chessColor){
            ((Chess)boardComponents[x-1][y-1]).swapColor();
            numOfBlackChesse+=chessColor==BoardComponentColor.BLACK?1:-1;
            numOfWhiteChesses+=chessColor==BoardComponentColor.BLACK?-1:1;
        }
        //剩余情况只可能是该位置处是同色棋子，不用操作
        
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
    public boolean isAvailablePoint(BoardPoint boardPoint){
        if(boardComponents[boardPoint.getX()-1][boardPoint.getY()-1]!=null
        &&
        !(boardComponents[boardPoint.getX()-1][boardPoint.getY()-1] instanceof EmptyPlace)
        ){
            return false;
        }
        return true;
    }




    //判断如果落子在该处，会夹住的棋子
    public List<BoardPoint> getClipBoardPoints(BoardPoint boardpoint,BoardComponentColor chessColor){
        List<BoardPoint> boardpoints=new ArrayList<>();
        //往八个方向搜索

        //往下搜索
        boardpoints.addAll(sortClipBoardPointsOneDirection(boardpoint,chessColor, 0, 1));

        //往上搜索
        boardpoints.addAll(sortClipBoardPointsOneDirection(boardpoint,chessColor, 0, -1));

        //往左搜索
        boardpoints.addAll(sortClipBoardPointsOneDirection(boardpoint,chessColor, -1, 0));
        
        //往右搜索
        boardpoints.addAll(sortClipBoardPointsOneDirection(boardpoint,chessColor, 1, 0));

        //往左上搜索
        boardpoints.addAll(sortClipBoardPointsOneDirection(boardpoint,chessColor, -1, -1));

        //往右上搜索
        boardpoints.addAll(sortClipBoardPointsOneDirection(boardpoint,chessColor, 1, -1));

        //往左下搜索
        boardpoints.addAll(sortClipBoardPointsOneDirection(boardpoint,chessColor, -1, 1));

        //往右下搜索
        boardpoints.addAll(sortClipBoardPointsOneDirection(boardpoint,chessColor, 1, 1));
        return boardpoints;
    }


    //(xs,ys)为搜索的方向的方向向量
    public List<BoardPoint> sortClipBoardPointsOneDirection(BoardPoint boardpoint,BoardComponentColor chessColor,int xs,int ys){
        List<BoardPoint> boardpoints=new ArrayList<>();
        int x=boardpoint.getX();
        int y=boardpoint.getY();
        int i;
        //搜索的路径长度不会大于棋盘宽高,找到空格或者下一个棋子为止
        for(i=1;x+i*xs>0&&x+i*xs<=numOfLines&&y+i*ys>0&&y+i*ys<=numOfLines;i++){
            if(boardComponents[x+i*xs-1][y+i*ys-1] instanceof Chess
            &&((Chess)boardComponents[x+i*xs-1][y+i*ys-1]).getChessColor()!=chessColor
            ){
                boardpoints.add(boardComponents[x+i*xs-1][y+i*ys-1].getBoardPoint());
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
            boardpoints.clear();
        //搜索到空格而结束搜索的，也没有可翻转的棋子
        else if(boardComponents[x+i*xs-1][y+i*ys-1] instanceof EmptyPlace)
            boardpoints.clear();
        return boardpoints;
    }

    
    
    //判断某个颜色的棋子是否还有棋可下，
    public boolean ifAvailableColor(BoardComponentColor chessColor){
        for(int i=0;i<numOfLines;i++){
            for(int j=0;j<numOfLines;j++){
                BoardPoint boardPoint=new BoardPoint(i+1, j+1);
                if(isAvailablePoint(boardPoint)){
                    List<BoardPoint> boardPoints=getClipBoardPoints(boardPoint,chessColor);
                    if(boardPoints!=null&&boardPoints.size()!=0) return true;
                }
            }
        }
        return false;
    }
    
    //判断游戏是否结束
    public boolean isGameOver(){
        //只要两个颜色的棋子中的一个还有子可下，游戏就没有结束
        if(ifAvailableColor(BoardComponentColor.BLACK)) return false;
        if(ifAvailableColor(BoardComponentColor.WHITE)) return false;
        return true;
    }



    public int getNumOfWhiteChesses() {
        return numOfWhiteChesses;
    }

    public int getNumOfBlackChesse() {
        return numOfBlackChesse;
    }

    //增加版本信息
    public void addVersion(String string){
        boardVersions.add(string);
    }

    //获取上一步,如果没有上一步就返回null
    public BoardPoint lastStep(){
        int numOfVersions=boardVersions.size();
        if(numOfVersions<=1) return null;
        //
        String curVersion=boardVersions.get(numOfVersions-1);
        String lastVersion=boardVersions.get(numOfVersions-2);
        //把两个棋局信息转化为棋盘比较
        int[][] curModel=parseChessBoardModel(curVersion);
        int[][] lastModel=parseChessBoardModel(lastVersion);
        //如果找到一个位置，上个棋形为空该棋形为非空，则就是要走的那一步的位置
        for(int i=0;i<numOfLines;i++){
            for(int j=0;j<numOfLines;j++){
                if(lastModel[i][j]==0&&curModel[i][j]!=0){
                    //注意i,j只是行列下标，与实际棋局位置中的行序和列序的数值差1
                    return new BoardPoint(i+1, j+1);
                }
            }
        }
        return null;
    }

    public BoardComponentColor getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(BoardComponentColor currentColor) {
        this.currentColor = currentColor;
    }

    public void swapCurrentColor(){
        //交换当前下棋者角色
        currentColor=((currentColor==BoardComponentColor.BLACK)?BoardComponentColor.WHITE:BoardComponentColor.BLACK);
    }


    //把棋局转化为一个字符串
    @Override
    public String toString() {
        //用一个三进制数来表示棋盘的状态
        //0表示空格，1表示黑棋，2表示白棋
        //因为一个long只有64位，不够表示棋盘64个位置的所有棋子
        //所以结果用long数组表示，一个long存储16位数据
        
        /*
         * 棋盘的棋子排列顺序：
         * 1 2 3 。。。8
         * 9 10 11 。。。16
         * 
         * 
         * 
         */
        //计算最多要使用数组的数量
        int numOfPoints=numOfLines*numOfLines;
        int pointsPerLong=16;
        int numOfLongs=numOfPoints%pointsPerLong==0?numOfPoints/pointsPerLong:numOfPoints/pointsPerLong+1;
        //定义合适大小的long数组
        long toS[]=new long[numOfLongs];
        int ordOfToS=0; //记录当前使用的long在toS数组中的下标
        int curSize=0;  //用来记录当前使用的long已经记录的信息的位数
        int weight=1;  //权重
        int base=3; //基数

        for(int i=0;i<numOfLines;i++){
            for(int j=0;j<numOfLines;j++){
                if(
                    boardComponents[i][j]!=null
                    &&boardComponents[i][j] instanceof Chess
                ){
                    toS[ordOfToS]+=((((Chess)boardComponents[i][j]).getChessColor()==BoardComponentColor.BLACK)?1:2)*weight;
                }
                curSize++;
                weight*=base;
                //判断当前是否更新到尽头,如果是，启动新的数组
                if(curSize==pointsPerLong){
                    curSize=0;
                    weight=1;
                    ordOfToS++;
                }
            }
        }
        String out="";
        for(int i=0;i<numOfLongs-1;i++){
            out+=toS[i]+"_";
        }
        out+=toS[numOfLongs-1];
        return out;
    }

    //私有方法，用来将棋局字符串转为矩阵形式
    private static int[][] parseChessBoardModel(String string){
        int[][] out=new int[numOfLines][numOfLines];
        String[] sa=string.split("_");

        //计算需要的long数量
        int numOfPoints=numOfLines*numOfLines;
        int pointsPerLong=16;
        int numOfLongs=numOfPoints%pointsPerLong==0?numOfPoints/pointsPerLong:numOfPoints/pointsPerLong+1;

        //比较从字符串中取出的long数量是否符合要求，如果不符合要求返回null
        if(sa.length!=numOfLongs)
            return null;
        long[] la=new long[numOfLongs];
        for(int i=0;i<numOfLongs;i++){
            try {
                la[i]=Long.valueOf(sa[i]);
            } catch (Exception e) {
                return null;
            }
        }
        //根据读取到的字符串来获取棋盘文件
        int ordOfLa=0; //记录当前使用的long在toS数组中的下标
        int curSize=0;  //用来记录当前使用的long已经记录的信息的位数
        int weight=1;  //权重
        int base=3; //基数
        try {
            for(int i=0;i<numOfLines;i++){
                for(int j=0;j<numOfLines;j++){
                    out[i][j]=(int)((la[ordOfLa]/weight)%base);
                    curSize++;
                    weight*=base;
                    //判断当前是否更新到尽头,如果是，启动新的数组
                    if(curSize==pointsPerLong){
                        curSize=0;
                        weight=1;
                        ordOfLa++;
                    }
                }
            }
        } catch (Exception e) {
            return null;
        }
        return out;
    }
    



   
    /**
     * 如果输入的字符串是合法的记录棋局信息的字符串,则根据该
     * 信息生成棋盘对象
     * <p>其中{@code string}是传入的记录有棋局信息的字符串，
     * {@code size}指定生成的棋盘的大小
     */
    public static ChessBoard valueOf(String string,int size) throws Exception{
        //取出两个整数
        ChessBoard chessBoard=new ChessBoard(size);
        int[][] chessBoardModel=parseChessBoardModel(string);
        if(chessBoardModel==null) return null;
        try {
            for(int i=0;i<numOfLines;i++){
                for(int j=0;j<numOfLines;j++){
                    switch(chessBoardModel[i][j]){
                        case 0:
                            chessBoard.putEmptyPlace(i+1, j+1);
                        break;
                        case 1:
                            chessBoard.putChess(i+1, j+1, BoardComponentColor.BLACK);
                        break;
                        case 2:
                            chessBoard.putChess(i+1, j+1, BoardComponentColor.WHITE);
                        break;
                        default:
                        throw new Exception("不是合法的棋局信息");
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("不是合法的棋局信息");
        }
        return chessBoard;
    }

    



    //把棋盘自身改变成这个值
    public void changeTo(String string,int size) throws Exception{
        //先初始化棋盘
        initBoard();
        //然后按照信息要求转化成指定形式
        //取出两个整数
        setSize(size);
        int[][] chessBoardModel=parseChessBoardModel(string);
        if(chessBoardModel==null) throw new Exception("wrong input string of chessBoard");
        try {
            for(int i=0;i<numOfLines;i++){
                for(int j=0;j<numOfLines;j++){
                    switch(chessBoardModel[i][j]){
                        case 0:
                            putEmptyPlace(i+1, j+1);
                        break;
                        case 1:
                            putChess(i+1, j+1, BoardComponentColor.BLACK);
                        break;
                        case 2:
                            putChess(i+1, j+1, BoardComponentColor.WHITE);
                        break;
                        default:
                        throw new Exception("不是合法的棋局信息");
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("不是合法的棋局信息");
        }
    }

    //把棋盘自身改变成string对应的棋盘,没有传递size参数则默认保持原来的棋盘大小
    public void changeTo(String string) throws Exception{
        //没有传入要改变成的棋盘大小参数size，则默认使用原本的棋盘大小
        changeTo(string,boardSize);
    }

    public void changeTo(ChessBoard chessBoard) throws Exception{
        //改变当前颜色为对应棋盘颜色
        changeTo(chessBoard.toString());
        setCurrentColor(chessBoard.getCurrentColor());
    }

    


    //获取当前棋盘上能够自由走的位置
    public List<BoardPoint> getAvailablePoints(){
        List<BoardPoint> out=new ArrayList<>();
        BoardComponentColor chessColor=getCurrentColor();
        for(int i=0;i<numOfLines;i++){
            for(int j=0;j<numOfLines;j++){
                BoardPoint boardPoint=new BoardPoint(i+1, j+1);
                if(isAvailablePoint(boardPoint)){
                    List<BoardPoint> boardPoints=getClipBoardPoints(boardPoint,chessColor);
                    if(boardPoints!=null&&boardPoints.size()!=0)
                        out.add(boardPoint);
                }   
            }
        }
        return out;
    }

    
    //TODO 测试双界面

    public static void main(String[] args) throws Exception {
        //测试两个棋盘同步更新
        // ChessBoard chessBoard=new ChessBoard(1);
        // chessBoard.clickController.handleClick(new BoardPoint(6, 5));
        // ChessBoard chessBoard2=new ChessBoard(1);
        // System.out.println(chessBoard);
        // System.out.println(chessBoard2);
        // chessBoard2.changeTo(chessBoard.toString(), 1);
        // System.out.println(chessBoard2);

        //
        
        
        JFrame jFrame=new JFrame();
        jFrame.setBounds(100,100,600,600);
        jFrame.setVisible(true);
        ChessBoard chessBoard1=new ChessBoard(560);
        jFrame.add(chessBoard1);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JFrame jFrame2=new JFrame();
        jFrame2.setBounds(600,100,600,600);
        jFrame2.setVisible(true);
        ChessBoard chessBoard2=new ChessBoard(560);
        jFrame2.add(chessBoard2);
        jFrame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        
       
        //建立一个内部类，用来对棋盘进行操作
        class CurPlayer extends HumanPlayer{
            public CurPlayer(BoardComponentColor playeColor, ChessBoard chessBoard) {
                super(playeColor, chessBoard);
                //TODO Auto-generated constructor stub
            }

            @Override
            public void singleStep() {
                // TODO Auto-generated method stub
                chessBoard.notifyAll();
                super.singleStep();
                SwingUtilities.invokeLater(()->{
                    try {
                        if(this.chessBoard==chessBoard2){
                            chessBoard1.changeTo(chessBoard);
                            System.out.println(chessBoard1.getCurrentColor());
                            
                        }
                        if(this.chessBoard==chessBoard1){
                            chessBoard2.changeTo(chessBoard);
                            System.out.println(chessBoard2.getCurrentColor());
                        }
                    } catch (Exception e) {
                        //TODO: handle exception
                    }
                });
            }
        }
        Player whitePlayer = new CurPlayer(BoardComponentColor.WHITE, chessBoard1);
        Player blackPlayer = new CurPlayer(BoardComponentColor.BLACK, chessBoard1);
        Player whitePlayer2 = new CurPlayer(BoardComponentColor.WHITE, chessBoard2);
        Player blackPlayer2 = new CurPlayer(BoardComponentColor.BLACK, chessBoard2);
        whitePlayer.play();
        blackPlayer.play();
        whitePlayer2.play();
        blackPlayer2.play();
    
    }

}
