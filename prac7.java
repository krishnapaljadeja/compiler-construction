import java.util.*;

public class prac7 {

    static Map<String, List<String>> grammar = new HashMap<>();
    static Map<String, Set<String>> first = new HashMap<>();
    static Map<String, Set<String>> follow = new HashMap<>();

    public static void main(String[] args) {

        grammar.put("S", Arrays.asList("ABC", "D"));
        grammar.put("A", Arrays.asList("a", "ε"));
        grammar.put("B", Arrays.asList("b", "ε"));
        grammar.put("C", Arrays.asList("(S)", "c"));
        grammar.put("D", Arrays.asList("AC"));

        for (String nonTerminal : grammar.keySet()) {
            first.put(nonTerminal, new HashSet<>());
            follow.put(nonTerminal, new HashSet<>());
        }

        follow.get("S").add("$");

        for (String nt : grammar.keySet()) {
            computeFirst(nt);
        }

        computeFollow();

        for (String nt : grammar.keySet()) {
            System.out.println("First(" + nt + ") = " + first.get(nt));
        }

        System.out.println();

        for (String nt : grammar.keySet()) {
            System.out.println("Follow(" + nt + ") = " + follow.get(nt));
        }
    }

    static Set<String> computeFirst(String symbol) {
        if (!grammar.containsKey(symbol)) {
            return new HashSet<>(Collections.singletonList(symbol));
        }

        if (!first.get(symbol).isEmpty())
            return first.get(symbol);

        for (String production : grammar.get(symbol)) {
            for (int i = 0; i < production.length(); i++) {
                String s = String.valueOf(production.charAt(i));

                Set<String> tempFirst = computeFirst(s);

                first.get(symbol).addAll(tempFirst);
                first.get(symbol).remove("ε");

                if (!tempFirst.contains("ε"))
                    break;

                if (i == production.length() - 1)
                    first.get(symbol).add("ε");
            }
        }
        return first.get(symbol);
    }

    static void computeFollow() {
        boolean changed;

        do {
            changed = false;

            for (String lhs : grammar.keySet()) {
                for (String production : grammar.get(lhs)) {
                    for (int i = 0; i < production.length(); i++) {
                        String B = String.valueOf(production.charAt(i));

                        // Skip terminals
                        if (!grammar.containsKey(B))
                            continue;

                        Set<String> trailer = new HashSet<>();

                        // If there is symbol after B
                        if (i + 1 < production.length()) {
                            String next = String.valueOf(production.charAt(i + 1));

                            // If next is terminal
                            if (!grammar.containsKey(next)) {
                                trailer.add(next);
                            } else {
                                trailer.addAll(first.get(next));
                                trailer.remove("ε");

                                if (first.get(next).contains("ε")) {
                                    trailer.addAll(follow.get(lhs));
                                }
                            }
                        } else {
                            trailer.addAll(follow.get(lhs));
                        }

                        if (follow.get(B).addAll(trailer))
                            changed = true;
                    }
                }
            }

        } while (changed);
    }
}