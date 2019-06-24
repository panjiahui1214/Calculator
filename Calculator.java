import java.util.regex.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame {

    public static void main(String[] args) {
        new Calculator();
    }

    private JPanel showPanel = new JPanel();
    private JLabel showText = new JLabel();
    private JLabel showMoreText = new JLabel();
    private JPanel btnPanel = new JPanel();
    private String[] btnArray = {"7","8","9","/","4","5","6","*","1","2","3","-","0",".","=","+"};
    private ActionListener btnListener;
    private Font bigFont = new Font("微软雅黑", Font.BOLD, 24);

    private Double result;
    private Boolean isOperator = false; // 标记刚输入的是否为符号
    private Boolean ifNotCount = false; // 标记是否未计算

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
        showText.setVerticalAlignment(JLabel.BOTTOM);
        showText.setFont(bigFont);
        showText.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        showMoreText.setBounds(0, 0, 200, 30);
        showMoreText.setHorizontalAlignment(JLabel.RIGHT);
        btnPanel.setBounds(10, 80, 200, 220);
        btnPanel.setLayout(new GridLayout(4, 4, 10, 10));
    }

    protected void addListener() {
        btnListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton btn = (JButton)e.getSource();
                String input = btn.getText();
                String oldShow = showText.getText();
                if (Pattern.matches("\\d", input)) {
                    if (oldShow.isEmpty() || isOperator) {
                        showText.setText(input);
                    }
                    else {
//                        showText.setText(formatNumber(oldShow, input));
                        showText.setText(oldShow + input);
                    }
                    isOperator = false;
                }
                else if (input.equals(".")) {
                    showText.setText(oldShow + input);
                }
                else if (Pattern.matches("[\\+\\-\\*/]", input)) {

                    showMoreText.setText(oldShow + " " + input + " ");
                    isOperator = true;
                }
                else {

                }
            }
        };
    }
    protected String formatNumber(String oldShow, String input) {
        return ((oldShow + input).indexOf(".") == -1) ?
                (oldShow + input) : (Double.parseDouble(oldShow + input) + "");
    }

    protected void addElement() {
        showPanel.add(showText);
        showPanel.add(showMoreText);
        forBtn();
        this.add(showPanel);
        this.add(btnPanel);
    }

    protected void setSelf() {
        this.setTitle("计算器");
        this.setBounds(300, 200, 226, 340);
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
