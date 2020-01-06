package models;

import java.util.*;

/**
 * ��Ŀ��
 * 
 * ��߶��塰��Ŀ�������С����ŵĲ���ʽ������ʽ��START->E������Ŀ����START->��E
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
			int items_size = items.size(); // items�Ĵ�С��仯����Ҫ��ǰ�����
			for (; i < items_size; i++) {
				Item iitem = items.get(i);
				if(iitem.getRightParts().size()==0)continue;
				String pointToken = iitem.getRightParts().get(iitem.getPoint());// ��ʾ�������Ǹ�����
				if (non_terminal.contains(pointToken)) {
					// �����i����Ŀ�еĵ�ĺ���ķ����Ƿ��ս��
					// ���������ķ��ս����Ӧ�����в���ʽ��ӵ���Ŀ����
					for (int j = 0; j < G.getproductions().size(); j++) {
						// ����G�е����в���ʽ
						Production jproduction = G.getproductions().get(j);// ��ŵ�j������ʽ
						if (pointToken.equals(jproduction.leftPart)) {
							Item item2 = new Item(jproduction);// ��jproduction��Item��ʾ
							if (!contains(item2)) {
								// �����Ŀ���в����������Ŀ�����
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
		// �ж�item1��item2�Ƿ���ͬ
		boolean judge = false;
		if (item1.rightParts.size() == item2.getRightParts().size()) {
			int j = 0;
			for (; j < item1.rightParts.size(); j++) {
				if (!item1.getRightParts().get(j).equals(item2.getRightParts().get(j))) {
					break;
				}
			}
			if (j >= item1.rightParts.size()) {
				// �Ҳ����
				judge = true;
			}
		}
		if (judge && item1.leftPart.equals(item2.leftPart) && item1.point == item2.point) {
			// ����Ҳ���ͬ�����󲿺͵��λ�ö���ͬ����ô˵������һ����
			return true;
		} else {
			return false;
		}
	}

	private boolean contains(Item item) {
		// List�Դ���contains���񲻿����ã��ж�items���Ƿ���item
		int i = 0;
		for (; i < items.size(); i++) {
			// �жϵ�i��Ԫ�غʹ����Ԫ���Ƿ���ͬ
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
