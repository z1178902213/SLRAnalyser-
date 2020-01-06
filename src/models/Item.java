package models;

/**
 * 类名称：Item 类描述：项目集规范族中的其中一个项目的其中一个产生式 创建时间：2019年12月30日 下午10:19:16
 */
public class Item extends Production {
	int point = 0;// 假设point=0，则表示E'->E

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public Item() {

	}

	public Item(Item item) {
		this.point = item.point + 1;
		this.leftPart = item.leftPart;
		this.rightParts = item.rightParts;
		this.number = item.number;
	}

	public Item(Production production) {
		leftPart = production.leftPart;
		if(production.rightParts.contains("~")) {
			production.rightParts.remove(production.rightParts.indexOf("~"));
		}
		rightParts = production.rightParts;
		this.number = production.number;
	}

	public boolean productionEquals(Item item) {
		boolean judge = false;
		if (rightParts.size() == item.rightParts.size()) {
			int i = 0;
			for (i = 0; i < rightParts.size(); i++) {
				if (!rightParts.get(i).equals(item.rightParts.get(i))) {
					break;
				}
			}
			if (i == rightParts.size()) {
				judge = true;
			}
			if (judge && leftPart.equals(item.leftPart)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		String s = leftPart;
		s = s + "->";
		for (int k = 0; k < rightParts.size(); k++) {
			if (point == k) {
				s = s + "·";
			}
			s = s + rightParts.get(k);
		}
		if (point == rightParts.size()) {
			s = s + "·";
		}
		return s;
	}
}
