import java.util.*;

public class prac8 {

    static Map<String, Map<String, String>> table = new HashMap<>();

    // Initialize parsing table
    static void createTable() {
        // S → aS
        table.putIfAbsent("S", new HashMap<>());
        table.get("S").put("a", "aS");

        // S → b
        table.get("S").put("b", "b");
    }

    // Check LL(1)
    static boolean isLL1() {
        for (String nonTerminal : table.keySet()) {
            Map<String, String> row = table.get(nonTerminal);
            Set<String> seen = new HashSet<>();

            for (String terminal : row.keySet()) {
                if (seen.contains(terminal)) {
                    return false;
                }
                seen.add(terminal);
            }
        }
        return true;
    }

    // Validate string
    static void parse(String input) {
        Stack<String> stack = new Stack<>();
        stack.push("$");
        stack.push("S");

        input = input + "$";
        int i = 0;

        while (!stack.isEmpty()) {
            String top = stack.peek();
            String current = String.valueOf(input.charAt(i));

            if (top.equals(current)) {
                stack.pop();
                i++;
            }
            else if (!table.containsKey(top)) {
                System.out.println("Invalid string");
                return;
            }
            else {
                String rule = table.get(top).get(current);

                if (rule == null) {
                    System.out.println("Invalid string");
                    return;
                }

                stack.pop();

                // push in reverse
                for (int j = rule.length() - 1; j >= 0; j--) {
                    stack.push(String.valueOf(rule.charAt(j)));
                }
            }
        }

        if (i == input.length()) {
            System.out.println("Valid string");
        } else {
            System.out.println("Invalid string");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        createTable();

        // Print table
        System.out.println("Predictive Parsing Table:");
        for (String nt : table.keySet()) {
            for (String t : table.get(nt).keySet()) {
                System.out.println("M[" + nt + "," + t + "] = " + table.get(nt).get(t));
            }
        }

        // Check LL(1)
        if (isLL1()) {
            System.out.println("Grammar is LL(1)");
        } else {
            System.out.println("Grammar is NOT LL(1)");
        }

        // Input string
        System.out.print("Enter string: ");
        String input = sc.next();

        parse(input);
    }
}