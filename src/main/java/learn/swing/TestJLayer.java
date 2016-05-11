package learn.swing;

import java.awt.AWTEvent;  
import java.awt.AlphaComposite;  
import java.awt.BasicStroke;  
import java.awt.Color;  
import java.awt.Font;  
import java.awt.Graphics;  
import java.awt.Graphics2D;  
import java.awt.Image;  
import java.awt.Point;  
import java.awt.RadialGradientPaint;  
import java.awt.RenderingHints;  
import java.awt.event.ActionEvent;  
import java.awt.event.ActionListener;  
import java.awt.event.FocusAdapter;  
import java.awt.event.FocusEvent;  
import java.awt.event.ItemEvent;  
import java.awt.event.ItemListener;  
import java.awt.event.MouseEvent;  


import java.beans.PropertyChangeEvent;



import javax.swing.ImageIcon;  
import javax.swing.JButton;  
import javax.swing.JCheckBox;  
import javax.swing.JComponent;  
import javax.swing.JFrame;  
import javax.swing.JLabel;  
import javax.swing.JLayer;  
import javax.swing.JPanel;  
import javax.swing.JPasswordField;  
import javax.swing.JTextField;  
import javax.swing.SwingUtilities;  
import javax.swing.Timer;  
import javax.swing.UIManager;  
import javax.swing.plaf.LayerUI;  
/**  
 * @author 陈苗发
 *  
 */ 
public class TestJLayer {  
    private static JFrame loginFrame;  
    private LoginLayerUI layerUI;//LayerUI的一个子类  
    public static void main(String[] args) {  
        try {  
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");     
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
          
        javax.swing.SwingUtilities.invokeLater(new Runnable() {  
            public void run() {  
                new TestJLayer().createUI();  
            }  
        });  
    }  
    public void createUI() {  
          
        loginFrame = new JFrame("JLayer 测试");  
        loginFrame.setSize(340, 260);  
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        loginFrame.setLocationRelativeTo(null);  
          
        JPanel panel = createPanel();  
        layerUI = new LoginLayerUI();  
        /*  
         * JLayer的构造函数：JLayer(V view, LayerUI<V> ui)，  
         * 第一个参数view为Component的一个子类，就是要要装饰的Swing组件  
         * 第二个参数为LayerUI<V>的一个实例，指定  
         */ 
        JLayer<JPanel> jlayer = new JLayer<JPanel>(panel, layerUI);  
        //注意这个add的是JLayer<JPanel>的一个实例  
        loginFrame.add(jlayer);  
        loginFrame.setVisible(true);  
    }  
 
    private JPanel createPanel() {  
        final Image background = new ImageIcon("image/background.jpg").getImage();//背景图片  
        JPanel panel = new JPanel() {  
            private static final long serialVersionUID = 6246862165441423926L;  
            public void paintComponent(Graphics g) {  
                super.paintComponent(g);  
                Graphics2D g2d = (Graphics2D) g;  
                g2d.drawImage(background, 0, 0, loginFrame.getWidth(),  
                        loginFrame.getHeight(), this);  
            }  
        };  
        panel.setLayout(null);  
          
          
        Font font = new Font("微软雅黑",Font.PLAIN,14);  
        JLabel accountLabel = new JLabel("账号:");  
        accountLabel.setFont(font);  
        accountLabel.setBounds(40, 60, 40, 15);  
        JLabel pswLabel = new JLabel("密码:");  
        pswLabel.setFont(font);  
        pswLabel.setBounds(40, 98, 40, 15);  
          
        final JTextField userName = new JTextField();  
        userName.setFont(font);  
        userName.setBounds(88, 53, 155, 33);  
        userName.addFocusListener(new FocusAdapter(){  
            public void focusGained(FocusEvent e) {  
                userName.selectAll();  
            }  
        });  
        JPasswordField password = new JPasswordField();  
        password.setBounds(88, 90, 155, 33);  
        JLabel registerLabel = new JLabel("注册");  
        registerLabel.setFont(font);  
        registerLabel.setBounds(260, 60, 28, 19);  
        JLabel findBackLabel = new JLabel("找回");  
        findBackLabel.setFont(font);  
        findBackLabel.setBounds(260, 98, 28, 19);  
          
        JCheckBox rmbCBox = new JCheckBox("记住密码");  
        rmbCBox.setSelected(true);  
        rmbCBox.setBounds(90, 140, 77, 14);  
        rmbCBox.addItemListener(new ItemListener(){  
            public void itemStateChanged(ItemEvent e) {  
                if(e.getStateChange() == ItemEvent.DESELECTED){  
                    System.out.println("rmbCBox（JCheckBox）被选中！");  
                }  
            }  
        });  
        JCheckBox autoCBox = new JCheckBox("自动登录");  
        autoCBox.setBounds(175, 140, 77, 14);  
          
        JButton setButton = new JButton("设置");  
        setButton.setBounds(50, 170, 75, 27);  
          
        JButton loginButton = new JButton("登录");  
        loginButton.setFont(font);  
        loginButton.setBounds(90, 170, 75, 27);  
        //5秒钟后停止  
        final Timer stopper = new Timer(5000, new ActionListener() {  
            public void actionPerformed(ActionEvent ae) {  
                layerUI.stop();  
            }  
        });  
        stopper.setRepeats(false);//设置计时器只在第一次时触发然后停止  
 
        loginButton.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent ae) {  
                layerUI.start();  
                //若stopper不是在运行，则启动stopper。  
                if (!stopper.isRunning()) {  
                    stopper.start();  
                }  
            }  
        });  
          
        JButton exitButton = new JButton("退出");  
        exitButton.setFont(font);  
        exitButton.setBounds(170, 170, 75, 27);  
        exitButton.addActionListener(new ActionListener()  
        {  
            public void actionPerformed(ActionEvent e) {  
                System.exit(0);  
            }  
              
        });  
          
        panel.add(userName);  
        panel.add(password);  
        panel.add(accountLabel);  
        panel.add(pswLabel);  
        panel.add(registerLabel);  
        panel.add(findBackLabel);  
        panel.add(rmbCBox);  
        panel.add(autoCBox);  
        panel.add(loginButton);  
        panel.add(exitButton);  
          
        return panel;  
 
 
    }  
}  
class LoginLayerUI extends LayerUI<JPanel> implements ActionListener {  
 
    private static final long serialVersionUID = 1L;  
    private boolean mIsRunning = false;// 点击按钮之后所触发的线程是否在运行  
    private boolean mIsFadingOut = false;//控制线程的停止，和处理  
    private Timer mTimer;// 在指定时间间隔触发一个或多个 ActionEvent。  
 
    private int mAngle = 0;//用于旋转时计算角度  
    private int mFadeCount = 0;//结合mFadeLimit用于设置针形旋转图案整体的渐变的效果  
    private int mFadeLimit = 30;  
 
    private boolean mActive;// 是否进入窗口内  
    private int mX, mY;// 记录鼠标的坐标  
    /*  
     * 渲染效果的过程中会不断调用paint  
     */ 
    public void paint(Graphics g, JComponent c) {  
        int w = c.getWidth();//获取组件c的宽度  
        int h = c.getHeight();  
        super.paint(g, c);//调用父类的paint方法，绘画组件c  
 
        Graphics2D g2 = (Graphics2D) g.create();  
        // 设置聚光灯（Spotlight）效果  
        if (mActive) {  
            // 创建径向渐变，中间透明。  
            java.awt.geom.Point2D center = new java.awt.geom.Point2D.Float(mX,  
                    mY);  
            float radius = 72;  
            float[] dist = { 0.0f, 1.0f };  
            Color[] colors = { new Color(0.0f, 0.0f, 0.0f, 0.0f), Color.black };  
            //RadialGradientPaint 类提供使用圆形辐射颜色渐变模式填充某一形状的方式  
            RadialGradientPaint p = new RadialGradientPaint(center, radius,  
                    dist, colors);  
            //setPaint:为 Graphics2D 上下文设置 Paint 属性  
            g2.setPaint(p);  
            /*为 Graphics2D 上下文设置 Composite。   
             *Composite 用于所有绘制方法中，如 drawImage、drawString、draw 和 fill  
             *这里使用AlphaComposite 类实现一些基本的 alpha 合成规则  
             *将源色与目标色组合，在图形和图像中实现混合和透明效果  
             */ 
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,  
                    .6f));  
            //填充是绘画  
            g2.fillRect(0, 0, c.getWidth(), c.getHeight());  
        }  
          
          
        if (!mIsRunning) {  
            return;  
        }  
        float fade = (float) mFadeCount / (float) mFadeLimit;  
        //fade的不同形成：12根针整体刚才出现的渐变效果，和登录成功后的针消失过程的渐变效果  
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,  
                .5f * fade));  
        g2.fillRect(0, 0, w, h);  
 
        int s = Math.min(w, h) / 5;  
        int cx = w / 2;  
        int cy = h / 2;  
        /* 为呈现算法设置单个首选项的值。  
         * RenderingHints.KEY_ANTIALIASING:抗锯齿提示键。  
         * RenderingHints.VALUE_ANTIALIAS_ON：抗锯齿提示值——使用抗锯齿模式完成呈现  
         */ 
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  
                RenderingHints.VALUE_ANTIALIAS_ON);  
 
        /*  
         *  setStroke:设置呈现过程中要画的图形  
         * BasicStroke 类定义针对图形图元轮廓呈现属性的一个基本集合  
         */ 
        g2.setStroke(new BasicStroke(s / 4, BasicStroke.CAP_ROUND,  
                BasicStroke.JOIN_ROUND));  
        //设置绘画颜色  
        g2.setPaint(Color.white);  
        /* rotate：旋转转换连接  
         * 第一个参数 Math.PI * mAngle / 180：旋转的角度，以弧度为单位  
         * 第二个参数 cx：x - 旋转原点的 x 坐标  
         * 第二个参数 cy：y - 旋转原点的 y 坐标  
         */ 
        g2.rotate(Math.PI * mAngle / 180, cx, cy);  
          
        //12跟针，各个针的颜色是不同的，针与针之间形成渐变的效果  
        for (int i = 0; i < 12; i++) {  
            float scale = (11.0f - (float) i) / 11.0f;  
            g2.drawLine(cx + s, cy, cx + s * 2, cy);  
            g2.rotate(-Math.PI / 6, cx, cy);  
            //通过设置AlphaComposite.getInstance()第二个参数的不同来实现12“针”颜色的不同  
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,  
                    scale * fade));  
        }  
        //释放此图形的上下文以及它使用的所有系统资源。   
        g2.dispose();  
    }  
 
    public void installUI(JComponent c) {  
        super.installUI(c);  
        JLayer<?> jlayer = (JLayer<?>) c;  
        // 启动鼠标事件和鼠标移动事件  
        jlayer.setLayerEventMask(AWTEvent.MOUSE_EVENT_MASK  
                | AWTEvent.MOUSE_MOTION_EVENT_MASK);  
    }  
 
    public void uninstallUI(JComponent c) {  
        JLayer<?> jlayer = (JLayer<?>) c;  
        jlayer.setLayerEventMask(0);  
        super.uninstallUI(c);  
    }  
 
    // JLayer对象专用的鼠标事件,在installUI中启动事件，在uninstallUI中禁用事件 
    @SuppressWarnings ( {"rawtypes" } )
    protected void processMouseEvent(MouseEvent e, JLayer l) {  
        if (e.getID() == MouseEvent.MOUSE_ENTERED)  
            mActive = true;  
        if (e.getID() == MouseEvent.MOUSE_EXITED)  
            mActive = false;  
        l.repaint();  
    }  
 
    // JLayer对象专用的鼠标移动事件，在installUI中启动事件，在uninstallUI中禁用事件  
    // 这里把鼠标坐标记录在mX和mY中
    @SuppressWarnings ( {"rawtypes" } )
    protected void processMouseMotionEvent(MouseEvent e, JLayer l) {  
        // 确定鼠标移动事件相对于层的坐标位置。  
        Point p = SwingUtilities  
                .convertPoint(e.getComponent(), e.getPoint(), l);  
        mX = p.x;  
        mY = p.y;  
        l.repaint();  
    }  
 
    public void actionPerformed(ActionEvent e) {  
        if (mIsRunning) {  
            //firePropertyChange():通知的内部状态的更新  
            firePropertyChange("tick", 0, 1);  
            mAngle += 3;  
            if (mAngle >= 360) {  
                mAngle = 0;  
            }  
            if (mIsFadingOut) {  
                if (--mFadeCount == 0) {  
                    mIsRunning = false;  
                    mTimer.stop();  
                }  
            //防止AlphaComposite.getInstance()的第二个参数的值超出范围  
            } else if (mFadeCount < mFadeLimit) {  
                mFadeCount++;  
            }  
        }  
    }  
 
    public void start() {  
        if (mIsRunning) {  
            return;  
        }  
        mIsRunning = true;  
        mIsFadingOut = false;  
        mFadeCount = 0;  
        int fps = 24;  
        int tick = 1000 / fps;  
        //每tick毫秒调用一次本类中的actionPerformed方法  
        mTimer = new Timer(tick, this);  
        mTimer.start();  
    }  
 
    public void stop() {  
        mIsFadingOut = true;  
    }  
    //不断repaint()窗口  
    @SuppressWarnings ( {"rawtypes" } )
    public void applyPropertyChange(PropertyChangeEvent pce, JLayer l) {  
        if ("tick".equals(pce.getPropertyName())) {  
            l.repaint();  
        }  
    }  
} 