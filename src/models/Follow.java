package models;

import java.util.ArrayList;
import java.util.List;

/**
 * �����ƣ�Follow ��������Follow������ ����ʱ�䣺2019��12��30�� ����11:20:16
 */
public class Follow {
	private List<String> follow = new ArrayList<String>();

	public void setfollow(List<String> follow) {
		this.follow = follow;
	}

	public List<String> getfollow() {
		return follow;
	}

	public boolean setfollow(Follow non_terminal) {
		boolean judge = false;
		for (int i = 0; i < non_terminal.getfollow().size(); i++) {
			if (!follow.contains(non_terminal.getfollow().get(i))&&!non_terminal.getfollow().get(i).equals("~")) {
				// ���follow���в�����������ս���еĵ�i��Ԫ�أ���ô����ӡ�
				follow.add(non_terminal.getfollow().get(i));
				judge = true;
			}
		}
		return judge;
	}

	public boolean setfollow(First non_terminal) {
		boolean judge = false;
		for (int i = 0; i < non_terminal.getFirst().size(); i++) {
			if (!follow.contains(non_terminal.getFirst().get(i))&&!non_terminal.getFirst().get(i).equals("~")) {
				// ���Follow���в�����������ս���еĵ�i��Ԫ�أ���ô����ӡ�
				follow.add(non_terminal.getFirst().get(i));
				judge = true;
			}
		}
		return judge;
	}

	public boolean setfollow(String terminal) {
		boolean judge = false;
		if (!follow.contains(terminal)&&!terminal.equals("~")) {
			// ���Follow���в��������ս����������ս����ӵ�Follow����
			follow.add(terminal);
			judge = true;
		}
		return judge;
	}
}
