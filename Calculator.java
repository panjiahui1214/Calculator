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
    private JLabel showText = new JLabel("0");
    private JLabel showMoreText = new JLabel();
    private JPanel btnPanel = new JPanel();
    private String[] btnArray = {"7","8","9","/","4","5","6","*","1","2","3","-","0",".","=","+"};
    private ActionListener btnListener;
    private Font bigFont = new Font("微软雅黑", Font.BOLD, 24);

    private Float num1; // 操作数1
    private String operator; // 操作符
    private Boolean isCalculate = false; // 标记是否刚计算完毕
    private Boolean isOperate = false; // 标记是否正在操作操作符

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
                String oldText = showText.getText();
                String oldMoreText = showMoreText.getText();
                if (Pattern.matches("\\d", input)) { // 输入数字
                    if (oldText.equals("0") || isCalculate == true || isOperate == true) {
                        // 重置数字显示区域
                        showText.setText(input);
                    }
                    else {
                        // 连接旧数字显示
                        showText.setText(oldText + input);
                    }
                    isCalculate = false;
                    isOperate = false;
                }
                else if (Pattern.matches("[\\+\\-\\*/]", input)) { // 输入操作符
                    if (null == operator) {
                        // 还没有输入过操作符
                        num1 = Float.parseFloat(oldText);
                        showMoreText.setText(oldText + " " + input);
                    }
                    else if (null != operator && isOperate == true) {
                        // 已经输入过操作符，且刚刚输入的是操作符，则变更操作符
                        showMoreText.setText(oldMoreText.substring(0, oldMoreText.length()-1) + input);
                    }
                    else if (null != operator && isOperate == false) {
                        // 已经输入过操作符，且刚刚输入的是数字，则先对前面的进行计算
                        isCalculate = true;
                        Float result = calculate(num1, operator, Float.parseFloat(oldText));
                        showText.setText(formatNum(result));
                        num1 = result;
                        showMoreText.setText(oldMoreText + " " + oldText + " " + input);
                    }
                    isOperate = true;
                    operator = input;
                }
                else if (input.equals(".")) { // 输入小数点
                    showText.setText(showText.getText() + ".");
                }
                else if (input.equals("=") && null != num1 && null != operator) { // 输入等号
                    isCalculate = true;
                    Float result = calculate(num1, operator, Float.parseFloat(oldText));
                    showText.setText(formatNum(result));
                    showMoreText.setText(null);
                    num1 = null;
                    operator = null;
                }
            }
        };
    }
    // 格式化输出数字
    protected String formatNum(Float num) {
        String sNum = num.toString();
        return Pattern.matches("\\d+.0", sNum) ? num.intValue()+"" : sNum;
    }
    // 根据加减乘除计算结果
    protected Float calculate(Float n1, String oper, Float n2) {
        Float result = 0.0F;
        switch (oper) {
            case "+":
                result = n1 + n2;
                break;
            case "-":
                result = n1 - n2;
                break;
            case "*":
                result = n1 * n2;
                break;
            case "/":
                result = n1 / n2;
                break;
        }
        return result;
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
