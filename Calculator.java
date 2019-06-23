import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame {

    public static void main(String[] args) {
        new Calculator();
    }

    private JPanel showPanel = new JPanel();
    private JLabel showText = new JLabel("0");
    private JPanel btnPanel = new JPanel();
    private String[] btnArray = {"7","8","9","/","4","5","6","*","1","2","3","-","0",".","=","+"};
//    private JButton btn1 = new JButton("1");
//    private JButton btn2 = new JButton("2");
//    private JButton btn3 = new JButton("3");
//    private JButton btn4 = new JButton("4");
//    private JButton btn5 = new JButton("5");
//    private JButton btn6 = new JButton("6");
//    private JButton btn7 = new JButton("7");
//    private JButton btn8 = new JButton("8");
//    private JButton btn9 = new JButton("9");
//    private JButton btn0 = new JButton("0");
//    private JButton btnAdd = new JButton("+");
//    private JButton btnSub = new JButton("-");
//    private JButton btnMul = new JButton("*");
//    private JButton btnDiv = new JButton("/");
    private ActionListener btnListener;
    private Font bigFont = new Font("微软雅黑", Font.BOLD, 24);

    public Calculator() {
        this.setElement();
        this.addListener();
        this.addElement();
        this.setSelf();
    }

    protected void setElement() {
        this.setLayout(null);
        showPanel.setBounds(10, 10, 200, 60);
        showPanel.setLayout(null);
        showText.setBounds(0, 0, 200, 60);
        showText.setHorizontalAlignment(JLabel.RIGHT);
        showText.setFont(bigFont);
        showText.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        btnPanel.setBounds(10, 80, 200, 220);
        btnPanel.setLayout(new GridLayout(4, 4, 10, 10));
    }

    protected void addListener() {
        btnListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton btn = (JButton)e.getSource();
                showText.setText(btn.getText());
            }
        };
    }

    protected void addElement() {
        showPanel.add(showText);
        forBtn();
        this.add(showPanel);
        this.add(btnPanel);
    }

    protected void setSelf() {
        this.setTitle("计算器");
        this.setBounds(300, 200, 226, 350);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
    }

    protected void forBtn() {
        for (int i = 0; i < btnArray.length; i ++) {
            JButton btn = new JButton(btnArray[i]);
            btn.addActionListener(btnListener);
            btnPanel.add(btn);
        }
    }

}
