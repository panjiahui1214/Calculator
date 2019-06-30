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

    private Float num1;
    private String operator;
    private Float num2;

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
                if (Pattern.matches("\\d", input)) { // 输入数字
                    if (null == operator) {
                        inputNumJudgeNum(input, num1, true);
                    }
                    else {
                        inputNumJudgeNum(input, num2, false);
                    }
                }
                else if (Pattern.matches("[\\+\\-\\*/]", input)) { // 输入加减乘除
                    if(null == operator) {
                        num1 = (null == num1) ? Float.parseFloat(showText.getText()) : num1;
                        showMoreText.setText(formatNum(num1) + " " + input);
                        operator = input;
                    }
                    else {
                        num1 = calculator(num1, num2, operator);
                        showText.setText(formatNum(num1) + "");
                        showMoreText.setText(showMoreText.getText() +" "+ formatNum(num2) +" "+ operator);
                        operator = input;
                        num2 = null;
                        operator = null;
                    }
                }
                else if (input.equals(".")) { // 输入小数点
                    if (null == operator) {
                        showText.setText((null == num1) ? "0." : (num1 + "."));
                    }
                    else {
                        showText.setText((null == num2) ? "0." : (num2 + "."));
                    }
                }
                else { // 输入等号
                    if (null != operator) {
                        num2 = ((null == num2) ? num1 : num2);
                        showText.setText(formatNum(calculator(num1, num2, operator)));
                        showMoreText.setText(null);
                        num1 = null;
                        num2 = null;
                        operator = null;
                    }
                }
            }
        };
    }
    // 判断某个操作数是否为空，是则直接输出数字，否则进行数字的连接
    protected void inputNumJudgeNum(String input, Float num, Boolean isNum1) {
        if (null == num){
            if (isNum1) { num1 = new Float(input); }
            else        { num2 = new Float(input); }
            showText.setText(input);
        }
        else {
            Float newNum = numConcatNum(input, num);
            if (isNum1) { num1 = newNum; }
            else        { num2 = newNum; }
            showText.setText(formatNum(newNum).toString());
        }
    }
    // 连接旧数字与新数字
    protected Float numConcatNum(String input, Float oldNum) {
        return oldNum * 10 + new Float(input);
    }
    // 格式化输出数字
    protected String formatNum(Float num) {
        String sNum = num.toString();
        return Pattern.matches("\\d+.0", sNum) ? num.intValue()+"" : sNum;
    }
    // 根据加减乘除计算结果
    protected Float calculator(Float n1, Float n2, String oper) {
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
