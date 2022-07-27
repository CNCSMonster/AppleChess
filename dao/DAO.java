package dao;
/**
 * 使用了数据访问对象模式，定义了用来访问数据的对象的接口
 * 
 * 需要能够查询到某个棋形下一步能够走成的棋形，以及下该棋形对应的价值，
 * 价值设定最小为0，限制最大为200000，如果超过200000就保持不变。
 * 
 * 用表的形式描述:
 * 该棋形                    棋形的价值              能够走到的棋形列表  
 * 当前棋局和当前颜色         一个整数                   v1,v2,....,vn
 * 
 * 需要实现的功能:
 * 1. 查询某个对象，
 * 根据当前棋形查询，如果存在，返回整个棋形数据对象(实际对象的克隆)
 * 如果不存在，则返回null
 * 2. 修改某个对象的价值
 * 输入要修改的对象的棋形(BoardCase)和要修改的价值
 * 如果要修改的对象不存在则抛出异常
 * 
 */


import model.BoardComponentColor;

public interface DAO {
    
    
    public CaseData find(String boardModel,BoardComponentColor curPlayerColor);
    
    public void resetValue(String boardModel,int value);

}
