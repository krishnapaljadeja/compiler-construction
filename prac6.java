import java.util.Scanner;

public class prac6 {

    static String input;
    static int index = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the string:");
        input = sc.nextLine().replaceAll("\\s+", ""); // remove spaces
        index = 0;

        if (S() && index == input.length()) {
            System.out.println("Valid string");
        } else {
            System.out.println("Invalid string");
        }

        sc.close();
    }

    static boolean S() {
        if (index < input.length() && input.charAt(index) == 'a') {
            index++;
            return true;
        } 
        else if (index < input.length() && input.charAt(index) == '(') {
            index++;
            if (L()) {
                if (index < input.length() && input.charAt(index) == ')') {
                    index++;
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    static boolean L() {
        if (S()) {
            return Ldash();
        }
        return false;
    }

    static boolean Ldash() {
        if (index < input.length() && input.charAt(index) == ',') {
            index++;
            if (S()) {
                return Ldash();
            }
            return false;
        }
        return true;
    }
}