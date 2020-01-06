package tools;

import java.util.*;
import java.util.Scanner;

import models.Grammar;
import models.Token;

public class LexicalAnalyser {
	String programLine;

	public LexicalAnalyser() {
		System.out.println("\n请输入待判断的程序段：\na+xyz*10+(c/d)\na+xyz*10+(c/d)+(xyz%a)+(d^5)");
		Scanner scan = new Scanner(System.in);
		programLine = scan.next();
		scan.close();
	}

	public List<Token> analise(Grammar G) {
		List<String> terminal = G.getTerminals();
		List<Token> tokenlist = new ArrayList<Token>();
		String s2 = new String();
		char index;
		for (int i = 0; i < programLine.length(); i++) {
			index = programLine.charAt(i);
			s2 = new String();
			if (index >= 'a' && index <= 'z' || index >= 'A' && index <= 'Z') {
				// 如果遇到的是字母
				s2 += index;
				if (programLine.length() > i + 1) {
					index = programLine.charAt(i + 1);
					while (index >= 'a' && index <= 'z' || index >= 'A' && index <= 'Z') {
						// 逐个字母将字符串中的字符加入到s2中，直到遇到一个字母以外的符号
						i++;
						s2 += index;
						if (i + 1 < programLine.length())
							index = programLine.charAt(i + 1);
						else
							break;
					}
				}

				/* 这边可以写一个判断是不是关键字的判断，SLR暂时不写，都默认是标识符 */
				// 将ID加入到TokenList中，并把ID的值保存
				Token tk = new Token();
				tk.setToken(terminal.indexOf("id"), s2);
				tokenlist.add(tk);
			} else if (index >= '0' && index <= '9') {
				// 如果遇到的是数字
				s2 += index;
				if (programLine.length() > i + 1) {
					index = programLine.charAt(i + 1);
					while (index >= '0' && index <= '9') {
						// 逐个将数字将字符串中的字符加入到s2中，直到遇到一个字母以外的符号
						i++;
						s2 += index;
						if (i + 1 < programLine.length())
							index = programLine.charAt(i + 1);
						else {
							break;
						}
					}
				}
				/* 这边可以写一个判断是不是关键字的判断，SLR暂时不写，都默认是标识符 */
				// 将NUM加入到TokenList中，并把NUM的值保存
				Token tk = new Token();
				tk.setToken(terminal.indexOf("num"), s2);
				tokenlist.add(tk);
			} else if (index == '+') {
				Token tk = new Token();
				s2 += "+";
				tk.setToken(terminal.indexOf("+"), s2);
				tokenlist.add(tk);
			} else if (index == '-') {
				Token tk = new Token();
				s2 += "-";
				tk.setToken(terminal.indexOf("-"), s2);
				tokenlist.add(tk);
			} else if (index == '*') {
				Token tk = new Token();
				s2 += "*";
				tk.setToken(terminal.indexOf("*"), s2);
				tokenlist.add(tk);
			} else if (index == '/') {
				Token tk = new Token();
				s2 += "/";
				tk.setToken(terminal.indexOf("/"), s2);
				tokenlist.add(tk);
			} else if (index == '(') {
				Token tk = new Token();
				s2 += "(";
				tk.setToken(terminal.indexOf("("), s2);
				tokenlist.add(tk);
			} else if (index == ')') {
				Token tk = new Token();
				s2 += ")";
				tk.setToken(terminal.indexOf(")"), s2);
				tokenlist.add(tk);
			} else if (index == '=') {
				Token tk = new Token();
				s2 += "=";
				tk.setToken(terminal.indexOf("="), s2);
				tokenlist.add(tk);
			} else if (index == ';') {
				Token tk = new Token();
				s2 += ";";
				tk.setToken(terminal.indexOf(";"), s2);
				tokenlist.add(tk);
			} else if (index == ',') {
				Token tk = new Token();
				s2 += ",";
				tk.setToken(terminal.indexOf(","), s2);
				tokenlist.add(tk);
			} else if (index == '^') {
				Token tk = new Token();
				s2 += "^";
				tk.setToken(terminal.indexOf("^"), s2);
				tokenlist.add(tk);
			} else if (index == '%') {
				Token tk = new Token();
				s2 += "%";
				tk.setToken(terminal.indexOf("%"), s2);
				tokenlist.add(tk);
			} else if (index == ' ') {

			} else {
				System.out.println("程序中遇到了不可识别的字符" + index + "，程序将停止运行！");
				System.exit(0);
				break;
			}
		}
		return tokenlist;
	}

	public Stack<Token> convertToStack(Grammar G, List<Token> tokenlist) {
		List<String> terminal = G.getTerminals();
		Stack<Token> stack = new Stack<Token>();
		for (int i = tokenlist.size() - 1; i >= 0; i--) {
			stack.add(tokenlist.get(i));
		}
		Token e = new Token();
		// 在栈底添加$作为程序终结的判断
		e.setToken(terminal.indexOf("$"), "$");
		stack.add(0, e);
		return stack;
	}
}
