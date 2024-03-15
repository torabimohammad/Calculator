package com.example.calculator;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private TextView screen;
    private StringBuilder input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UpdateProgramBasedOnOrientation(getResources().getConfiguration());
        screen = findViewById(R.id.tv_result);
        input = new StringBuilder();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        UpdateProgramBasedOnOrientation(newConfig);
    }


    private void UpdateProgramBasedOnOrientation(Configuration configuration) {
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.acativity_main_landscape);
        } else {
            setContentView(R.layout.activity_main);
        }
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
