package tools;

import java.util.*;
import java.util.Scanner;

import models.Grammar;
import models.Token;

public class LexicalAnalyser {
	String programLine;

	public LexicalAnalyser() {
		System.out.println("\n��������жϵĳ���Σ�\na+xyz*10+(c/d)\na+xyz*10+(c/d)+(xyz%a)+(d^5)");
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
				// �������������ĸ
				s2 += index;
				if (programLine.length() > i + 1) {
					index = programLine.charAt(i + 1);
					while (index >= 'a' && index <= 'z' || index >= 'A' && index <= 'Z') {
						// �����ĸ���ַ����е��ַ����뵽s2�У�ֱ������һ����ĸ����ķ���
						i++;
						s2 += index;
						if (i + 1 < programLine.length())
							index = programLine.charAt(i + 1);
						else
							break;
					}
				}

				/* ��߿���дһ���ж��ǲ��ǹؼ��ֵ��жϣ�SLR��ʱ��д����Ĭ���Ǳ�ʶ�� */
				// ��ID���뵽TokenList�У�����ID��ֵ����
				Token tk = new Token();
				tk.setToken(terminal.indexOf("id"), s2);
				tokenlist.add(tk);
			} else if (index >= '0' && index <= '9') {
				// ���������������
				s2 += index;
				if (programLine.length() > i + 1) {
					index = programLine.charAt(i + 1);
					while (index >= '0' && index <= '9') {
						// ��������ֽ��ַ����е��ַ����뵽s2�У�ֱ������һ����ĸ����ķ���
						i++;
						s2 += index;
						if (i + 1 < programLine.length())
							index = programLine.charAt(i + 1);
						else {
							break;
						}
					}
				}
				/* ��߿���дһ���ж��ǲ��ǹؼ��ֵ��жϣ�SLR��ʱ��д����Ĭ���Ǳ�ʶ�� */
				// ��NUM���뵽TokenList�У�����NUM��ֵ����
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
				System.out.println("�����������˲���ʶ����ַ�" + index + "������ֹͣ���У�");
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
		// ��ջ�����$��Ϊ�����ս���ж�
		e.setToken(terminal.indexOf("$"), "$");
		stack.add(0, e);
		return stack;
	}
}
