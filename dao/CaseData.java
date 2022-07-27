package dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Exchanger;

import javax.lang.model.element.Element;
import javax.lang.model.util.ElementScanner14;

import model.BoardComponentColor;
import model.ChessBoard;

/*
 * 棋形的定义:
 * 1.当前棋子的排布
 * 2.当前的行棋方的颜色
 * 该类的对象不需要也不应该允许修改成员。
 * 棋形只能够生成，获取信息，但是不能够修改
 */
class BoardCase{
    private BoardComponentColor curPlayerColor;
    private String boardModel;
    public BoardCase(BoardComponentColor curPlayerColor,String boardModel){
        this.curPlayerColor=curPlayerColor;
        this.boardModel=new String(boardModel);
    }

    public BoardComponentColor getCurPlayerColor() {
        return curPlayerColor;
    }
    public String getBoardModel() {
        return boardModel;
    }

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        BoardCase boardCase=(BoardCase)obj;
        if(boardCase.boardModel.equals(this.boardModel)&&boardCase.curPlayerColor==this.curPlayerColor)
            return true;
        return false;
    }
}


/*
 * 棋形数据，
 * 一个棋形数据由三大部分组成
 * 1.当前棋形(棋盘中棋子的排布以及当前行棋方颜色)
 * 2.当前棋形的价值
 * 3.当前棋形下一步能够变成的所有棋形列表
 * 
 * 注意:
 * 一个棋形对应的下一步棋形的集合是确定的，不能够重复的
 * 
 */
public class CaseData {

    private static final int maxValue=20000;
    private static final int minValue=0;

    private BoardCase curBoardCase;
    private int value;  //价值只允许做加一和减1操作
    private final Set<BoardCase> nextBoardCases=new HashSet();

    public CaseData(BoardCase curBoardCase,int value){
        this.curBoardCase=curBoardCase;
        this.value=value;
    }

    public void setCurBoardCase(BoardCase curBoardCase) {
        this.curBoardCase = curBoardCase;
    }
    public BoardCase getCurBoardCase() {
        return curBoardCase;
    }
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        if(value<minValue) this.value=minValue;
        else if(value<maxValue) this.value=value;
        else    this.value = maxValue;
    }
    

    //也可以直接接受字符串作为下一步
    public void addNextBoardCase(String boardModel){
        //先判断有没有出现重复的棋子布局,如果有，则使用return中止退出
        var curPlayerColor=(this.curBoardCase.getCurPlayerColor()==BoardComponentColor.WHITE)?BoardComponentColor.BLACK:BoardComponentColor.WHITE;
        //调用Set类型的add方法，能够加入不重复的成员，如果重复则不加入。
        //而且因为nextBoardCases实际上是HashSet，内部是用哈希实现的，所以查找判断是否重复的速度很快,
        //而且Set不接受空元素，自带检查，所以使用起来比较安全
        nextBoardCases.add(new BoardCase(curPlayerColor, boardModel));
    }


    /**
     * 棋形数据对象的字符串序列化
     * 用到的字母表{1-9,'\n'}
     * 第一行依次存储棋形，颜色(如果是黑色存1，白色存0)，以及价值,相邻两项之间用空格隔开
     * 第二行开始存储下一步可能导致的棋形，每两个棋形之间换一行，
     * 最后一个棋形不换行
     * @author CNCSMONSTER
     * 
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder=new StringBuilder();
        int intOfColor=(curBoardCase.getCurPlayerColor()==BoardComponentColor.BLACK)?1:0;
        stringBuilder.append(curBoardCase.getBoardModel()+" "+intOfColor+" "+value+"\n");
        int size=nextBoardCases.size();
        int i=0;
        for(BoardCase boardCase:nextBoardCases){
            stringBuilder.append(boardCase.getBoardModel());
            if(i<size-1)    stringBuilder.append("\n");
            else break;
            i++;
        }
        return stringBuilder.toString();
    }

    //把字符串转化为一个CaseData对象
    public static CaseData valueOf(String s){
        String[] sa=s.split("\n");
        if(sa.length<1) return null;
        String[] sa2=sa[0].split(" ");
        try {
            if(sa2.length!=3) throw new Exception();
            String boardModel=sa2[0];
            int intOfColor=Integer.parseInt(sa2[1]);
            if(intOfColor!=0&&intOfColor!=1) throw new Exception();
            int value=Integer.parseInt(sa2[2]);
            var curPlayerColor=(intOfColor==1)?BoardComponentColor.BLACK:BoardComponentColor.WHITE;
            BoardCase boardCase=new BoardCase(curPlayerColor, boardModel);
            CaseData caseData=new CaseData(boardCase, value);
            for(int i=1;i<sa.length;i++){
                caseData.addNextBoardCase(sa[i]);
            }
            return caseData;
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println("wrong argument for CaseData.valueOf");
        }
        return null;
    }


    //测试完成

    public static void main(String[] args) {
        ChessBoard chessBoard=new ChessBoard(1);
        String boardModel=chessBoard.toString();
        BoardCase boardCase=new BoardCase(BoardComponentColor.BLACK, boardModel);
        CaseData caseData=new CaseData(boardCase, 1);
        caseData.addNextBoardCase(boardModel);
        caseData.addNextBoardCase(boardModel);
        caseData.addNextBoardCase(boardModel);
        caseData.addNextBoardCase(boardModel);

        CaseData caseData2=new CaseData(boardCase, 1);
        caseData2.addNextBoardCase(boardModel);
        caseData2.addNextBoardCase(boardModel);

        System.out.println(caseData);
        System.out.println(caseData2);


        //测试反序列化
        CaseData caseData3=CaseData.valueOf(caseData.toString());
        System.out.println("3");
        System.out.println(caseData3);
        System.out.println("end");
        


    }


}
