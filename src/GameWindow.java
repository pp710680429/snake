import javax.swing.*;
import java.awt.*;

public class GameWindow {
    public static void main(String[] args) {
        JFrame frame = new JFrame();

        SnackPanel snackPanel = new SnackPanel();
        snackPanel.setmMultiple(2); //放大两倍
        //设置窗口大小 ，因为SnackPanel大小为500＊300，窗口边为2，deepin中的标题栏为（600－561）
        frame.setSize(snackPanel.getmWidth() + 2 ,snackPanel.getmHeight() + 2 + (600-561));
        //设置标题
        frame.setTitle("贪吃蛇");
        //设置图标
        frame.setIconImage(new ImageIcon("image/logo.png").getImage());

        //获得屏幕大小
        Dimension screensize   =   Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)screensize.getWidth();
        int height = (int)screensize.getHeight();

        //设置窗口居中
        frame.setLocation((width - frame.getWidth()) / 2,(height - frame.getHeight()) / 2);

        //加入游戏界面
        frame.add(snackPanel);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}