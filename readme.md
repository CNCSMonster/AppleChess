# 基于java的黑白棋桌面应用开发

### 项目目标

实践MVC项目结构

实践java网络编程

复习java数据库编程

### 迭代目标

1. 实现双人对战功能

2. 实现联网双人对战

3. 使用策略模式实现简单机器棋手

4. 使用数据库保存棋局信息，实现悔棋功能

5. 实现基于哈希表和价值判断与迭代的方式的自动学习机器棋手

### 游戏规则和应用功能说明

略

### 模块划分和功能实现

1. 实现二人对战

   * 使用观察者模式完成下棋。

     棋子作为信息的发布者，遇到点击事件后把自己在棋盘上的坐标传递给棋盘控制者，

     所以棋子类型需要保留一个棋盘控制者的引用。

     棋盘控制者需要对数据模型，也就是要对棋盘进行操作，所以需要保存对棋盘的引用

2. 实现人机对战

   使用策略模式和模板模式，定义玩家抽象类Player，通过调用BaseClickController的方式控制棋盘，操作棋盘信息，

   定义电脑玩家类和人类玩家类，都继承抽象类Player.

   电脑玩家类中保存有一个策略类对象的引用，使用策略模式实现电脑玩家的走棋策略。

3. 实现联网对战

   使用装饰器模式实现联网对战。

   本地双人对战使用AIPlayer和HumanPlayer实现。

   而为了实现人机对战定义了LocalPlayer和RemotePlayer，都继承了Player抽象类

   其中LocalPlayer作为AIPlayer和HumanPlayer的装饰器，在内部保存这两个类之一的一个引用，在自身的singleStep方法中，调用被装饰类对象的singleStep方法，并添加向远程发送信息的装饰代码。

4. 设置棋盘不能够接受点击事件防止本地连续两次点击。

   棋盘是JPanel面板，棋件是JButton组件。棋件包含入棋盘中后，棋盘用setEnable设置enabled为FALSE，棋件的enabled却不会随之变化，需要重写棋盘的setEnabled方法。

   并且需要注意的是，像JButton这样的轻量级组件，就算设置了enabled为false，同样会接受用户的点击等事件输入，不过不会处理而已，但是我们重写了组件的事件处理方法的时候可能会在原本是否处理的条件判断的范围之外，所以我们可能要增加根据enabled的条件判断。

   下面是setEnabled的源代码注释文件

   ```java
   Sets whether or not this component is enabled. 
   A component that is enabled may respond to user input, 
   while a component that is not enabled cannot respond to 
   user input.Some components may alter their visual 
   representation when they are disabled in order to provide 
   feedback to the user that they cannot take input.
   
   Note: Disabling a component does not disable its children.
   
   Note: Disabling a lightweight component does not prevent 
   it from receiving MouseEvents.
   ```

5. 处理玩家线程之间对棋盘资源的竞争与共享。

   需要实现:一方棋手在下棋时，另一方棋手不能下棋，二，本地棋手下棋时，远程棋手不能下棋。

#### 进度

1. 2022/7/2，完成双人对战走棋逻辑

2. 2022/7/3，重构单机双人走棋逻辑判定，提高了扩展性，增加了界面自适应方法

3. 2022/7/15.

   完成三种简单策略人机。随机走法，翻转优先走法，地理位置有限走法

4. 2022/7/26