import java.util.*;

class Quadruple {
    String op, arg1, arg2, result;

    Quadruple(String op, String arg1, String arg2, String result) {
        this.op = op;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.result = result;
    }

    public String toString() {
        return op + "\t" + arg1 + "\t" + arg2 + "\t" + result;
    }
}

public class prac11 {

    static int tempCount = 1;

    // Check precedence
    static int precedence(char ch) {
        if (ch == '+' || ch == '-') return 1;
        if (ch == '*' || ch == '/') return 2;
        return 0;
    }

    // Convert infix to postfix
    static String infixToPostfix(String expr) {
        Stack<Character> stack = new Stack<>();
        StringBuilder postfix = new StringBuilder();

        for (int i = 0; i < expr.length(); i++) {
            char ch = expr.charAt(i);

            if (Character.isDigit(ch)) {
                postfix.append(ch);
            }
            else if (ch == '(') {
                stack.push(ch);
            }
            else if (ch == ')') {
                while (!stack.isEmpty() && stack.peek() != '(')
                    postfix.append(stack.pop());
                stack.pop();
            }
            else if ("+-*/".indexOf(ch) != -1) {
                while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(ch))
                    postfix.append(stack.pop());
                stack.push(ch);
            }
        }

        while (!stack.isEmpty())
            postfix.append(stack.pop());

        return postfix.toString();
    }

    // Generate quadruples
    static void generateQuadruples(String postfix) {
        Stack<String> stack = new Stack<>();
        List<Quadruple> quads = new ArrayList<>();

        for (int i = 0; i < postfix.length(); i++) {
            char ch = postfix.charAt(i);

            if (Character.isDigit(ch)) {
                stack.push(String.valueOf(ch));
            }
            else {
                String op2 = stack.pop();
                String op1 = stack.pop();
                String result = "t" + tempCount++;

                quads.add(new Quadruple(String.valueOf(ch), op1, op2, result));
                stack.push(result);
            }
        }

        // Print table
        System.out.println("Operator\tOperand1\tOperand2\tResult");
        for (Quadruple q : quads) {
            System.out.println(q);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Expression: ");
        String expr = sc.nextLine().replaceAll(" ", "");

        String postfix = infixToPostfix(expr);
        generateQuadruples(postfix);
    }
}