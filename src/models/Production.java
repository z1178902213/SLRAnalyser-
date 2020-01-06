package models;

import java.util.*;

/**
 * �����ƣ�Production
 * ������������ʽ�࣬����涨���ķ��е�ÿһ������ʽ�������ַ���ɵġ�������������硰_F����ʽ���ս������ս������������ʶ��
 * ���ҳ�����Զ��жϣ�����ʽ��ߵ�һ���Ƿ��ս����
 */
public class Production {
	public int number;
	String leftPart = ""; // ����ʽ��
	List<String> rightParts = new ArrayList<String>(); // ����ʽ�Ҳ�

	public Production() {

	}

	public Production(String s) {
		Separate(s);
	}

	/** �����ַ��������������ķ��д�Ų���ʽ���ַ��������ַ�������󱣴���Tokens�� */
	private void Separate(String s) {
		boolean left = true;
		int i = 0; // ����һ������
		while (i < s.length()) { // ���û�ж����ַ�����ĩβ���ͼ���ѭ���ж�
			boolean special = true; // ����ʽ������ʱ����������ĸ��Ԫ�أ��ͱ�����������!@#$%^&*()֮���Ԫ��
			if (s.charAt(i) == '-' && s.charAt(i + 1) == '>') {
				// ���������->��־����˵���ǵ��˲���ʽ�Ҳ��ˣ��ñ�־λΪfalse����ʾ��ʼ��¼�Ҳ�
				i += 2;
				left = false;
				continue;
			}
			String indexString = ""; // ��ʼ��һ��������ʱ����ķ��е��ʵ��ַ�������
			while (s.charAt(i) >= 'a' && s.charAt(i) <= 'z' || s.charAt(i) >= 'A' && s.charAt(i) <= 'Z') {
				// ����������ַ�����ĸ���ͽ�����ַ�������indexString�У��������ж���һ���ַ��ǲ����ַ�
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
				// ��������ʱ�����жϲ���ʽ���󲿣���ô�ͽ��м����indexString�����ڲ���ʽ���ַ��������С�
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
		return "{�� = " + leftPart + ", �Ҳ� = " + rightParts + "}\n";
	}
}
