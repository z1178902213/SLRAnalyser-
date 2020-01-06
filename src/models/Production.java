package models;

import java.util.*;

/**
 * 类名称：Production
 * 类描述：产生式类，这里规定了文法中的每一条产生式都是由字符组成的。如果出现了诸如“_F”形式的终结符或非终结符，都将不被识别。
 * 并且程序会自动判断，产生式左边的一定是非终结符。
 */
public class Production {
	public int number;
	String leftPart = ""; // 产生式左部
	List<String> rightParts = new ArrayList<String>(); // 产生式右部

	public Production() {

	}

	public Production(String s) {
		Separate(s);
	}

	/** 分离字符串方法，输入文法中存放产生式的字符串，将字符串分离后保存在Tokens中 */
	private void Separate(String s) {
		boolean left = true;
		int i = 0; // 定义一个变量
		while (i < s.length()) { // 如果没有读到字符串的末尾，就继续循环判断
			boolean special = true; // 产生式分析的时候遇到非字母的元素，就比如是遇到了!@#$%^&*()之类的元素
			if (s.charAt(i) == '-' && s.charAt(i + 1) == '>') {
				// 如果遇到了->标志，就说明是到了产生式右部了，置标志位为false，表示开始记录右部
				i += 2;
				left = false;
				continue;
			}
			String indexString = ""; // 初始化一个用于暂时存放文法中单词的字符串变量
			while (s.charAt(i) >= 'a' && s.charAt(i) <= 'z' || s.charAt(i) >= 'A' && s.charAt(i) <= 'Z') {
				// 如果遇到的字符是字母，就将这个字符保存在indexString中，并继续判断下一个字符是不是字符
				special = false;
				indexString += s.charAt(i);
				i++;
				if (i == s.length()) {
					break;
				}
			}
			if (special) {
				if (s.charAt(i) == ' ') {
					i++;
					continue;
				}else {
					indexString += s.charAt(i);
					i++;
				}
			}
			if (left) {
				// 如果程序此时正在判断产生式的左部，那么就将中间变量indexString保存在产生式左部字符串变量中。
				leftPart = indexString;
			} else {
				rightParts.add(indexString);
			}
		}
	}

	public String getLeftPart() {
		return leftPart;
	}

	public void setLeftPart(String leftPart) {
		this.leftPart = leftPart;
	}

	public List<String> getRightParts() {
		return rightParts;
	}

	public void setRightParts(List<String> rightParts) {
		this.rightParts = rightParts;
	}

	@Override
	public String toString() {
		return "{左部 = " + leftPart + ", 右部 = " + rightParts + "}\n";
	}
}
