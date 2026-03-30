import java.util.*;

public class prac12 {

    // Check if string is number
    static boolean isNumber(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Perform operation
    static String evaluate(String a, String b, String op) {
        double x = Double.parseDouble(a);
        double y = Double.parseDouble(b);

        switch (op) {
            case "+": return String.valueOf(x + y);
            case "-": return String.valueOf(x - y);
            case "*": return String.valueOf(x * y);
            case "/": return String.valueOf(x / y);
        }
        return "";
    }

    // Precedence
    static int precedence(String op) {
        if (op.equals("+") || op.equals("-")) return 1;
        if (op.equals("*") || op.equals("/")) return 2;
        return 0;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter expression: ");
        String expr = sc.nextLine();

        // Tokenize (split by space)
        String[] tokens = expr.split(" ");

        Stack<String> values = new Stack<>();
        Stack<String> ops = new Stack<>();

        for (String token : tokens) {

            if (token.equals("(")) {
                ops.push(token);
            }
            else if (token.equals(")")) {
                while (!ops.peek().equals("(")) {
                    process(values, ops);
                }
                ops.pop();
            }
            else if (token.matches("[+\\-*/]")) {
                while (!ops.isEmpty() && precedence(ops.peek()) >= precedence(token)) {
                    process(values, ops);
                }
                ops.push(token);
            }
            else {
                values.push(token);
            }
        }

        while (!ops.isEmpty()) {
            process(values, ops);
        }

        System.out.println("Optimized Expression: " + values.peek());
    }

    static void process(Stack<String> values, Stack<String> ops) {
        String b = values.pop();
        String a = values.pop();
        String op = ops.pop();

        if (isNumber(a) && isNumber(b)) {
            // Fold constants
            values.push(evaluate(a, b, op));
        } else {
            // Keep as expression
            values.push(a + " " + op + " " + b);
        }
    }
}