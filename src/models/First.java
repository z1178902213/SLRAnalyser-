package models;

import java.util.ArrayList;
import java.util.List;

/**
 * �����ƣ�First ��������First���� ����ʱ�䣺2019��12��30�� ����11:20:04
 */
public class First {
	private List<String> first = new ArrayList<String>();

	public void setFirst(List<String> first) {
		this.first = first;
	}

	public List<String> getFirst() {
		return first;
	}

	public boolean setFirst(First non_terminal) {
		boolean judge = false;
		for (int i = 0; i < non_terminal.getFirst().size(); i++) {
			if (!first.contains(non_terminal.getFirst().get(i))) {
				// ���first���в�����������ս���еĵ�i��Ԫ�أ���ô����ӡ�
				first.add(non_terminal.getFirst().get(i));
				judge = true;
			}
		}
		return judge;
	}
}
