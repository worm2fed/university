package com.spo.lab4;

import java.util.Stack;

public class Analyzer {
	String[] ch;
	Stack<String> stack = new Stack<String>();
	String out = "";
	
	OperationTable[] op_table = { 
			new OperationTable(1, ".+"), 
			new OperationTable(1, ".-"), 
			new OperationTable(1, "!"), 
			new OperationTable(1, "~"),
			new OperationTable(2, "*"), 
			new OperationTable(2, "/"), 
			new OperationTable(2, "%"), 
			new OperationTable(3, "+"), 
			new OperationTable(3, "-"),
			new OperationTable(3, "<<"),
			new OperationTable(3, ">>"), 
			new OperationTable(4, "<"), 
			new OperationTable(4, "<="), 
			new OperationTable(4, ">"),
			new OperationTable(4, ">="), 
			new OperationTable(5, "=="), 
			new OperationTable(5, "!="), 
			new OperationTable(6, "&"), 
			new OperationTable(6, "^"), 
			new OperationTable(6, "|"), 
			new OperationTable(7, "&&"), 
			new OperationTable(7, "||")
		};

	public String run(String s) {
		ch = s.split(" ");
		
		for (int i = 0; i < ch.length; i++) {
			// если не операция
			if (!isOperaton(ch[i])) {
				// если закрывающая скобка
				if (ch[i].equals("(")) {
					stack.push(ch[i]);
					
					continue;
				} else if (ch[i].equals(")")) {
					// извлекаем всё до открывающей скобки
					while (!stack.peek().equals("("))
						out += stack.pop();
					
					// удаляем открывающую скбоку
					stack.pop();
				} else
					out += ch[i];
				
				continue;
			// если операция
			} else {
				int next_pos = -1;

				if (!stack.empty())
					next_pos = getPriorityPos(stack.peek());
				
				while (!isProrityLow(getPriorityPos(ch[i]), next_pos)) {
					if (stack.empty())
						break;
					else if (!isOperaton(stack.peek()))
						break;
					
					out += stack.pop();
				}

				stack.push(ch[i]);
				
				continue;
			}
		}
		
		while (!stack.empty())
			out += stack.pop();
		
		return out;
	}
	
	public boolean isProrityLow(int cur_pos, int next_pos) {
		if (next_pos == -1)
			return true;
		else {
			if (op_table[cur_pos].priority < op_table[next_pos].priority)
				return true;
			else
				return false;
		}
	}
	
	public int getPriorityPos(String s) {
		int pos = -1;
		for (int i = 0; i < op_table.length; i++) {
			if (op_table[i].operation.equals(s)) {
				pos = i;
				break;
			}
		}
		
		return pos;
	}
	
	public boolean isOperaton(String s) {
		boolean statement = false;
		
		for (int i = 0; i < op_table.length; i++) {
			if (op_table[i].operation.equals(s)) {
				statement = true;
				break;
			}
		}
		
		return statement;
	}
}

class OperationTable {
	int priority;
	String operation;

	OperationTable(int pr, String op) {
		this.priority = pr;
		this.operation = op;
	}
}
