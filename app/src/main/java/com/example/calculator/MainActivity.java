package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import java.util.Stack;

//
//import java.util.regex.Pattern;
//
//public class MainActivity extends AppCompatActivity {
//
//    private TextView screen;
//    Button clear, multiple, division, equal, minus, numOne, numTwo, numThree, numFour, numFive, numSix, numSeven, numEight, numNine, numZero, dotPoint;
//    String input, answer;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_main);
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        // Define all the views
//        screen = findViewById(R.id.tv_result);
//        clear = findViewById(R.id.clear);
//        numOne = findViewById(R.id.one);
//        numTwo = findViewById(R.id.two);
//        numThree = findViewById(R.id.three);
//        numFour = findViewById(R.id.four);
//        numFive = findViewById(R.id.five);
//        numSix = findViewById(R.id.six);
//        numSeven = findViewById(R.id.seven);
//        numEight = findViewById(R.id.eight);
//        numNine = findViewById(R.id.nine);
//        numZero = findViewById(R.id.zero);
//        multiple = findViewById(R.id.multiple);
//        division = findViewById(R.id.devision);
//        equal = findViewById(R.id.equal);
//        minus = findViewById(R.id.minus);
//        dotPoint = findViewById(R.id.dot);
//    }
//
//    public void ButtonClick(View view) {
//        Button btn = (Button) view;
//        String data = btn.getText().toString();
//        switch (data) {
//            case "C":
//                input = "";
//                answer = "";
//                break;
//            case "*":
//                input += "*";
//                break;
//            case "+":
//                input += "+";
//                break;
//            case "-":
//                input += "-";
//                break;
//            case "/":
//                input += "/";
//                break;
//            case "=":
//                Solve();
//                answer = input;
//                break;
//            default:
//                if (input == null) {
//                    input = "";
//                }
//                if (data.equals("+") || data.equals("-") || data.equals("/") || data.equals("*")) {
//                    Solve();
//                }
//                input += data;
//        }
//        screen.setText(input);
//    }
//
//    private void Solve() {
//        if (input.split("\\*").length == 2) {
//            String number[] = input.split("\\*");
//            try {
//
//                double mul = Double.parseDouble(number[0]) * Double.parseDouble(number[1]);
//                input = mul + "";
//            } catch (Exception e) {
//                //Toggle error
//            }
//        } else if (input.split("/").length == 2) {
//            String number[] = input.split("/");
//            try {
//                double div = Double.parseDouble(number[0]) / Double.parseDouble(number[1]);
//                input = div + "";
//            } catch (Exception e) {
//                //Toggle error
//            }
//        } else if (input.split("\\+").length == 2) {
//            String number[] = input.split("\\+");
//            try {
//
//                double sum = Double.parseDouble(number[0]) + Double.parseDouble(number[1]);
//                input = sum + "";
//            } catch (Exception e) {
//                //Toggle error
//            }
//        } else if (input.split("-").length == 2) {
//            String number[] = input.split("-");
//            if (number[0] == "" && number.length == 2) {
//                number[0] = 0 + "";
//            }
//            try {
//                double sub = Double.parseDouble(number[0]) - Double.parseDouble(number[1]);
//                input = sub + "";
//            } catch (Exception e) {
//                //Toggle error
//            }
//        }
//        String n[] = input.split("\\.");
//        if (n.length > 1) {
//            if (n[1].equals("0")) {
//                input = n[0];
//            }
//        }
//        screen.setText(input);
//    }
//}



public class MainActivity extends AppCompatActivity {

    private TextView screen;
    private StringBuilder input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        screen = findViewById(R.id.tv_result);
        input = new StringBuilder();
    }

    public void ButtonClick(View view) {
        Button btn = (Button) view;
        String data = btn.getText().toString();

        switch (data) {
            case "C":
                input = new StringBuilder();
                break;
            case "=":
                Solve();
                break;
            default:
                input.append(data);
        }

        screen.setText(input.toString());
    }

    private void Solve() {
        String answer = input.toString();

        try {
            Stack<Double> stack = new Stack<>();
            StringBuilder currentNumber = new StringBuilder();
            char lastOperator = '+';

            for (int i = 0; i < answer.length(); i++) {
                char c = answer.charAt(i);

                if (Character.isDigit(c) || c == '.') {
                    currentNumber.append(c);
                } else {
                    if (currentNumber.length() > 0) {
                        double number = Double.parseDouble(currentNumber.toString());

                        switch (lastOperator) {
                            case '+':
                                stack.push(number);
                                break;
                            case '-':
                                stack.push(-number);
                                break;
                            case '*':
                                stack.push(stack.pop() * number);
                                break;
                            case '/':
                                stack.push(stack.pop() / number);
                                break;
                        }

                        currentNumber.setLength(0);
                    }

                    if (c == '+' || c == '-' || c == '*' || c == '/') {
                        lastOperator = c;
                    }
                }
            }

            // Process the last number
            if (currentNumber.length() > 0) {
                double number = Double.parseDouble(currentNumber.toString());

                switch (lastOperator) {
                    case '+':
                        stack.push(number);
                        break;
                    case '-':
                        stack.push(-number);
                        break;
                    case '*':
                        stack.push(stack.pop() * number);
                        break;
                    case '/':
                        stack.push(stack.pop() / number);
                        break;
                }
            }

            // Calculate the final result
            double result = 0;
            while (!stack.isEmpty()) {
                result += stack.pop();
            }

            // Display the result
            input.setLength(0);
            input.append(result);
        } catch (Exception e) {
            input.setLength(0);
            input.append("Error");
        }
    }
}
