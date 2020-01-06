package models;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名称：Follow 类描述：Follow集的类 创建时间：2019年12月30日 下午11:20:16
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
				// 如果follow集中不包含对象非终结符中的第i个元素，那么就添加。
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
				// 如果Follow集中不包含对象非终结符中的第i个元素，那么就添加。
				follow.add(non_terminal.getFirst().get(i));
				judge = true;
			}
		}
		return judge;
	}

	public boolean setfollow(String terminal) {
		boolean judge = false;
		if (!follow.contains(terminal)&&!terminal.equals("~")) {
			// 如果Follow集中不包含该终结符，则将这个终结符添加到Follow集中
			follow.add(terminal);
			judge = true;
		}
		return judge;
	}
}
