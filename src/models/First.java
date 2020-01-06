package models;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名称：First 类描述：First集类 创建时间：2019年12月30日 下午11:20:04
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
				// 如果first集中不包含对象非终结符中的第i个元素，那么就添加。
				first.add(non_terminal.getFirst().get(i));
				judge = true;
			}
		}
		return judge;
	}
}
