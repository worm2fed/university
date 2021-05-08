/**
 * Created by worm2fed on 03.02.17.
 */

class ShuntingYard {
    // Structure for opearations
    private class OperationTable {
        int priority;
        String operation;

        OperationTable(int pr, String op) {
            priority = pr;
            operation = op;
        }
    }

    private OperationTable[] op_table = {
            new OperationTable(3, "*"),
            new OperationTable(3, "/"),
            new OperationTable(4, "+"),
            new OperationTable(4, "-")
    };

    // Queue for RPN result
    private LinkedQueue<String> queue = new LinkedQueue<>();

    // Is string a number
    private boolean is_number(String s) {
        try {
            Double.parseDouble(s);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Calculate RPN
    String calculate() {
        // Stack for RPN calculation
        LinkedStack<String> stack = new LinkedStack<>();

        while (!queue.is_empty()) {
            // If a number
            if (is_number(queue.peek()))
                stack.push(queue.poll());
            else {
                try {
                    double a = Double.parseDouble(stack.pop());
                    double b = Double.parseDouble(stack.pop());

                    switch (queue.poll()) {
                        case "+": stack.push(Double.toString(b + a)); break;
                        case "-": stack.push(Double.toString(b - a)); break;
                        case "*": stack.push(Double.toString(b * a)); break;
                        case "/": stack.push(Double.toString(b / a)); break;
                    }
                } catch (Exception e) {
                    System.out.println("oops");
                }
            }
        }

        return String.format("%.2f", Double.parseDouble(stack.pop()));
    }

    // Create RPN
    void create(String s) {
        // Stack for RPN generation
        LinkedStack<String> stack = new LinkedStack<>();

        s = parser(s);
        String[] ch = s.split(" ");

        for (String aCh : ch) {
            // If it isn't an operation
            if (!isOperaton(aCh)) {
                switch (aCh) {
                    case "(":
                        stack.push(aCh);
                        break;
                    case ")":
                        // Get all until there is the open bracket
                        while (!stack.peek().equals("("))
                            queue.add(stack.pop());

                        // Delete the open bracket
                        stack.pop();
                        break;
                    default:
                        queue.add(aCh);
                        break;
                }
                // If it's an operation
            } else {
                int next_pos = -1;

                if (!stack.is_empty())
                    next_pos = getPriorityPos(stack.peek());

                while (!isProrityLow(getPriorityPos(aCh), next_pos)) {
                    if (stack.is_empty())
                        break;
                    else if (!isOperaton(stack.peek()))
                        break;

                    queue.add(stack.pop());
                }

                stack.push(aCh);
            }
        }

        while (!stack.is_empty())
            queue.add(stack.pop());
    }

    // Parser
    private String parser(String s) {
        String result = "";
        s = s.replaceAll(" ", "");

        char[] ch = s.toCharArray();

        for (int i = 0; i < ch.length; i++) {
            if (Character.isDigit(ch[i]) || ch[i] == '.')
                result += ch[i];
            else {
                if (i != 0 && Character.isDigit(ch[i - 1]))
                    result += " ";

                result += ch[i] + " ";
            }
        }

        return result;
    }

    // Check is priority low
    private boolean isProrityLow(int cur_pos, int next_pos) {
        return next_pos == -1 || op_table[cur_pos].priority < op_table[next_pos].priority;
    }

    // Get priority position
    private int getPriorityPos(String s) {
        int pos = -1;

        for (int i = 0; i < op_table.length; i++) {
            if (op_table[i].operation.equals(s)) {
                pos = i;
                break;
            }
        }

        return pos;
    }

    // Check is char an operation
    private boolean isOperaton(String s) {
        boolean statement = false;

        for (OperationTable anOp_table : op_table) {
            if (anOp_table.operation.equals(s)) {
                statement = true;
                break;
            }
        }

        return statement;
    }
}