package com.example.SD3a_Calculator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private String currentInput = "";
    private String operator = "";
    private double firstValue = 0;
    private boolean isOperatorPressed = false;
    private boolean hasDot = false;
    private boolean isNegative = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);

        View.OnClickListener numberListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                if (isOperatorPressed) {
                    currentInput = "";
                    isOperatorPressed = false;
                }
                currentInput += button.getText().toString();
                display.setText(currentInput);
            }
        };

        int[] numberButtonIds = {
                R.id.button0, R.id.button1, R.id.button2, R.id.button3,
                R.id.button4, R.id.button5, R.id.button6, R.id.button7,
                R.id.button8, R.id.button9
        };

        for (int id : numberButtonIds) {
            findViewById(id).setOnClickListener(numberListener);
        }

        findViewById(R.id.buttonDot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasDot) {
                    if (currentInput.isEmpty()) {
                        currentInput = "0.";
                    } else {
                        currentInput += ".";
                    }
                    hasDot = true;
                    display.setText(currentInput);
                }
            }
        });

        findViewById(R.id.buttonAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOperator("+");
            }
        });

        findViewById(R.id.buttonSubtract).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOperator("-");
            }
        });

        findViewById(R.id.buttonMultiply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOperator("*");
            }
        });

        findViewById(R.id.buttonDivide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOperator("/");
            }
        });

        findViewById(R.id.buttonClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });

        findViewById(R.id.buttonEquals).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate();
            }
        });

        findViewById(R.id.buttonSign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSign();
            }
        });
    }

    private void setOperator(String op) {
        if (!currentInput.isEmpty()) {
            if (operator.isEmpty()) {
                firstValue = Double.parseDouble(currentInput);
            } else {
                calculate();
            }
            operator = op;
            isOperatorPressed = true;
            hasDot = false;
            display.setText(currentInput + " " + operator);
        }
    }

    private void calculate() {
        try {
            double secondValue = Double.parseDouble(currentInput);
            double result = 0;

            switch (operator) {
                case "+":
                    result = firstValue + secondValue;
                    break;
                case "-":
                    result = firstValue - secondValue;
                    break;
                case "*":
                    result = firstValue * secondValue;
                    break;
                case "/":
                    if (secondValue == 0) {
                        throw new ArithmeticException("Division by zero");
                    }
                    result = firstValue / secondValue;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + operator);
            }

            DecimalFormat df = new DecimalFormat("#.####");
            display.setText(df.format(result));
            currentInput = df.format(result);
            operator = "";
            isOperatorPressed = false;
            hasDot = currentInput.contains(".");
        } catch (NumberFormatException e) {
            display.setText("Error: Invalid Number");
            clear();
        } catch (ArithmeticException e) {
            display.setText("Error: " + e.getMessage());
            clear();
        } catch (Exception e) {
            display.setText("Error: " + e.getMessage());
            clear();
        }
    }

    private void clear() {
        currentInput = "";
        operator = "";
        firstValue = 0;
        isOperatorPressed = false;
        hasDot = false;
        isNegative = false;
        display.setText("0");
    }

    private void toggleSign() {
        if (!currentInput.isEmpty()) {
            if (isNegative) {
                currentInput = currentInput.replace("-", "");
                isNegative = false;
            } else {
                currentInput = "-" + currentInput;
                isNegative = true;
            }
            display.setText(currentInput);
        }
    }
}
