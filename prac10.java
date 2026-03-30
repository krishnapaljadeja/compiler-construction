import java.util.*;

public class prac10 {

    // Check precedence
    static int precedence(char op) {
        if (op == '+' || op == '-') return 1;
        if (op == '*' || op == '/') return 2;
        if (op == '^') return 3;
        return 0;
    }

    // Apply operation
    static double applyOp(double a, double b, char op) {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/': return a / b;
            case '^': return Math.pow(a, b);
        }
        return 0;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter expression: ");
        String expr = sc.nextLine().replaceAll(" ", "");

        try {
            double result = evaluate(expr);
            System.out.println("Result: " + result);
        } catch (Exception e) {
            System.out.println("Invalid expression");
        }
    }

    static double evaluate(String expr) {
        Stack<Double> values = new Stack<>();
        Stack<Character> ops = new Stack<>();

        for (int i = 0; i < expr.length(); i++) {
            char ch = expr.charAt(i);

            // Number (multi-digit + decimal)
            if (Character.isDigit(ch) || ch == '.') {
                StringBuilder sb = new StringBuilder();
                while (i < expr.length() &&
                      (Character.isDigit(expr.charAt(i)) || expr.charAt(i) == '.')) {
                    sb.append(expr.charAt(i));
                    i++;
                }
                i--;
                values.push(Double.parseDouble(sb.toString()));
            }

            else if (ch == '(') {
                ops.push(ch);
            }

            else if (ch == ')') {
                while (ops.peek() != '(') {
                    double b = values.pop();
                    double a = values.pop();
                    values.push(applyOp(a, b, ops.pop()));
                }
                ops.pop();
            }

            else if ("+-*/^".indexOf(ch) != -1) {
                while (!ops.isEmpty() &&
                      ((precedence(ops.peek()) > precedence(ch)) ||
                      (precedence(ops.peek()) == precedence(ch) && ch != '^'))) {

                    double b = values.pop();
                    double a = values.pop();
                    values.push(applyOp(a, b, ops.pop()));
                }
                ops.push(ch);
            }
        }

        while (!ops.isEmpty()) {
            double b = values.pop();
            double a = values.pop();
            values.push(applyOp(a, b, ops.pop()));
        }

        return values.pop();
    }
}