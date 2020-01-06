package models;

import java.util.*;

/**
 * 项目集
 * 
 * 这边定义“项目”就是有·符号的产生式，产生式：START->E，则项目就是START->·E
 */
public class ItemSet {
	int number;
	List<Item> items = new ArrayList<Item>();

	public ItemSet() {

	}

	public void initItemSet(Item item, Grammar G) {
		boolean _ALTERED_ = false;
		int i = items.size();
		items.add(item);
		List<String> non_terminal = G.getNon_terminals();
		do {
			if (item.point >= item.rightParts.size()) {
				break;
			}
			_ALTERED_ = false;
			int items_size = items.size(); // items的大小会变化，需要提前保存好
			for (; i < items_size; i++) {
				Item iitem = items.get(i);
				if(iitem.getRightParts().size()==0)continue;
				String pointToken = iitem.getRightParts().get(iitem.getPoint());// 表示点后面的那个符号
				if (non_terminal.contains(pointToken)) {
					// 如果第i条项目中的点的后面的符号是非终结符
					// ↓将点后面的非终结符对应的所有产生式添加到项目集中
					for (int j = 0; j < G.getproductions().size(); j++) {
						// 遍历G中的所有产生式
						Production jproduction = G.getproductions().get(j);// 存放第j条产生式
						if (pointToken.equals(jproduction.leftPart)) {
							Item item2 = new Item(jproduction);// 将jproduction用Item表示
							if (!contains(item2)) {
								// 如果项目集中不包含这个项目则添加
								items.add(item2);
								_ALTERED_ = true;
							}
						}
					}
				}
			}
		} while (_ALTERED_);
	}

	public boolean equals(Item item1, Item item2) {
		// 判断item1和item2是否相同
		boolean judge = false;
		if (item1.rightParts.size() == item2.getRightParts().size()) {
			int j = 0;
			for (; j < item1.rightParts.size(); j++) {
				if (!item1.getRightParts().get(j).equals(item2.getRightParts().get(j))) {
					break;
				}
			}
			if (j >= item1.rightParts.size()) {
				// 右部相等
				judge = true;
			}
		}
		if (judge && item1.leftPart.equals(item2.leftPart) && item1.point == item2.point) {
			// 如果右部相同，且左部和点的位置都相同，那么说明就是一样的
			return true;
		} else {
			return false;
		}
	}

	private boolean contains(Item item) {
		// List自带的contains好像不可以用，判断items中是否有item
		int i = 0;
		for (; i < items.size(); i++) {
			// 判断第i个元素和传入的元素是否相同
			if (equals(items.get(i), item)) {
				break;
			}
		}
		if (i >= items.size()) {
			return false;
		} else {
			return true;
		}
	}
}
