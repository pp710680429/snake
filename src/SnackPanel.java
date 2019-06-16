import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class SnackPanel extends Panel implements ActionListener , KeyListener {
    int mHeight = 300;
    int mWidth = 500;
    int mMultiple = 1;//长和宽的倍数
    int timerMM = 220; //设置定时器的时间

    //这是蛇的位置和和
    int[] x= new int[750];
    int[] y = new int[750];
    int len = 3;
    String direction = "右";
    ImageIcon head = new ImageIcon("images/head.jpg");
    ImageIcon body = new ImageIcon("images/body.jpg");
    ImageIcon shiwu = new ImageIcon("images/head.jpg");
    ImageIcon backGround = new ImageIcon("images/background.jpeg");

    //定时器
    Timer timer = new Timer(timerMM,this);
    //随机数生成器
    Random rand = new Random();

    //食物坐标
    int xsw ,ysw;

    public SnackPanel(){
        this.InitSnackLocation();
        this.addKeyListener(this);
        this.setFocusable(true);
        this.creatRandSW();
    }

    //对蛇的位置进行初始化
    public void InitSnackLocation(){
        int tmpx = rand.nextInt(this.getResultLong(12)) ;
        int tmpy = rand.nextInt(this.getResultLong(8)) ;
        if (tmpx < 3 || tmpy < 3){
            this.InitSnackLocation();
        }
        x[0] = getResultLong(tmpx) *25;
        y[0] = getResultLong(tmpy) *25;
        x[1] = getResultLong(tmpx -1) *25;
        y[1] = getResultLong(tmpy) *25;
        x[2] = getResultLong(tmpx -2)*25;
        y[2] = getResultLong(tmpy) *25;
    }

    @Override
    public void paint(Graphics graphics) {
        //设置背景颜色
        this.setBackground(Color.BLACK);
        //设置背景图片
        //graphics.drawImage(backGround.getImage(), 0, 0,this.getWidth(), this.getHeight(), this);


        //设置画笔
        graphics.setColor(Color.RED);
        graphics.setFont(new Font("Noto Sans CJK SC", Font.BOLD, 26));

        //画最初蛇的位置
        head.paintIcon(this,graphics,x[0],y[0]);
        for (int i = 1;i < len ; i++) {
            body.paintIcon(this,graphics,x[i],y[i]);
        }

        //画提示文字
        if (!isStart){
            graphics.drawString("点击空格开始/暂停！",this.mWidth/2 - 26 -100,this.mHeight/2 - 26);
        }

        //如果吃到食物
        if (x[0] == xsw && y[0] == ysw){
            this.creatRandSW();
            len++;
        }

        //画出食物
        shiwu.paintIcon(this,graphics,xsw,ysw);

        //提示现在的分数 先设置画笔颜色
        graphics.setColor(Color.GREEN);
        graphics.drawString("当前分数：" + (len -3),150,26);

        //如果撞到自己则GG
        for (int i = 1 ; i < len ; i++){
            if (x[0] == x[i] && y[0] == y[i]){
                graphics.drawString("游戏结束，你当前的分数为：" + ( len - 3),this.mWidth/2 - 130,this.mHeight/2);
                this.gameOver();
            }
        }
     //   System.out.println("图片的长宽为：" + head.getIconHeight() + "  " + head.getIconWidth());;
        //设置难度
        if((len -3 ) > 15){
            timerMM = 200;
            timer.setDelay(timerMM);

            timer.stop();
            timer.start();
        } else if((len -3 ) > 45){
            timerMM = 175;
            timer.setDelay(timerMM);

            timer.stop();
            timer.start();
        }
    }

    public void creatRandSW(){
        xsw = rand.nextInt(this.getResultLong(20)) * 25;
        ysw = rand.nextInt(this.getResultLong(12)) * 25;
        System.out.println("已经打印");
    }



    public void setmMultiple(int mMultiple) {
        this.mMultiple = mMultiple;
        mHeight *= mMultiple ;
        mWidth *= mMultiple;
    }

    //返回长宽
    public int getmHeight() {
        return mHeight;
    }

    public int getmWidth() {
        return mWidth;
    }

    public int getResultLong(int Long){
        return Long*this.mMultiple; //返回放大之后的数据
    }

    //游戏结束
    public  void gameOver(){
        isStart = false;
        len=3;
        this.InitSnackLocation();
        this.creatRandSW();
        timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        /*
        * 改变蛇的位置
        * 改变方法是：
        *   从蛇尾开始，获得前一个块的坐标，蛇头通过计算下个位置得出坐标
        * */
        for (int j = len ; j > 1 ; j--){
            x[j-1] = x[j-2];
            y[j-1] = y[j-2];
        }
        if (direction.equals("右")){
            if (x[0] >= this.getResultLong(500))
                x[0] = 0;
            else
                x[0] += 25;
        }else if (direction.equals("左")){
            if (x[0] <= 0)
                x[0] = this.getResultLong(500);
            else
                x[0] -= 25;
        }else if (direction.equals("上")){
            if (y[0] <= 0)
                y[0] = this.getResultLong(300);
            else
                y[0] -= 25;
        }else if (direction.equals("下")){
            if (y[0] >= this.getResultLong(300))
                y[0] = 0;
            else
                y[0] += 25;
        }

        //重新画一次
        this.repaint();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    private boolean isStart = false;
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT && !direction.equals("左")){
            direction = "右";
        }else if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT && !direction.equals("右")){
            direction = "左";
        }else if (keyEvent.getKeyCode() == KeyEvent.VK_UP && !direction.equals("下")){
            direction = "上";
        }else if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN && !direction.equals("上")){
            direction = "下";
        }else if(keyEvent.getKeyCode() == KeyEvent.VK_SPACE){
            isStart = !isStart;
            if ( isStart){
                timer.start();
            } else {
                timer.stop();
            }
        }
        //System.out.println("按钮是：" + keyEvent.getKeyCode());
        //重新画一次，让开始/暂停提示显示
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
