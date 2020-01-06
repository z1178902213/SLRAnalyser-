package models;

import java.util.*;

/**
 * ��Ŀ���淶��
 */

public class ItemFamily {
	List<ItemSet> itemsets = new ArrayList<ItemSet>();

	public ItemFamily() {

	}

	public void print() {
		System.out.println("\n���ķ�����Ŀ���淶��������ʾ��");
		for (int i = 0; i < itemsets.size(); i++) {
			System.out.println("I" + i + "��");
			for (int j = 0; j < itemsets.get(i).items.size(); j++) {
				String s = itemsets.get(i).items.get(j).leftPart;
				s = s + "->";
				for (int k = 0; k < itemsets.get(i).items.get(j).rightParts.size(); k++) {
					if (itemsets.get(i).items.get(j).point == k) {
						s = s + "��";
					}
					s = s + itemsets.get(i).items.get(j).rightParts.get(k);
				}
				if (itemsets.get(i).items.get(j).point == itemsets.get(i).items.get(j).rightParts.size()) {
					s = s + "��";
				}
				System.out.println(s);
			}
			System.out.println();
		}
	}

	public ItemFamily(Grammar G) {
		InitFamily(G);
		createFamily(G);
	}

	private void InitFamily(Grammar G) {
		String s = "START->" + G.getProduction(0).leftPart;// �����ع��ķ�
		Production _START_ = new Production(s);// ���ع��ķ��Բ���ʽ��ʽ����
		Item item = new Item(_START_);// ������ʽ��ʽ���ع��ķ�����Ŀ��ʽ���棬����START->��E
//		ItemSet is = new ItemSet(item, G);// ���ݵ�һ������ʽSTART->��E�Ƶ���������Ŀ���齨����Ŀ��
		ItemSet is = new ItemSet();
		is.initItemSet(item, G);
		is.number = 0;
		itemsets.add(is);// �����ع��ķ���������Ŀ��I0����I0��ӵ���Ŀ���淶����
	}

	private void createFamily(Grammar G) {
		int number = 1;
		for (int i = 0; i < itemsets.size(); i++) {// itemsets.size�Ƕ�̬�ģ������ų����ִ�ж��仯
			List<String> caching = new ArrayList<String>();// ����
			ItemSet iitemset = itemsets.get(i);// ��iitemset��ʾ��i����Ŀ��
			/* ����һ���i����Ŀ���������г����ڵ������ַ���ӵ������� */
			for (int j = 0; j < iitemset.items.size(); j++) {
				Item jitem = iitemset.items.get(j);// ��jitem��ʾ��j����Ŀ
				if (jitem.getPoint() >= jitem.getRightParts().size()) {
					// ���������û�ж����������������Ŀ���жϡ�
					continue;
				}
				if (!caching.contains(jitem.rightParts.get(jitem.getPoint()))) {
					// ����������ж����������������ӵ�caching�����С�
					caching.add(jitem.rightParts.get(jitem.getPoint()));
				}
			}

			/* �������棬��ʾ�������״̬ */
			for (int j = 0; j < caching.size(); j++) {
				String jcache = caching.get(j);// jcache��ʾ��j�����Զ����״̬
				ItemSet newITS = new ItemSet();
				for (int k = 0; k < iitemset.items.size(); k++) {
					List<String> rightpart = iitemset.items.get(k).rightParts;
					int point = iitemset.items.get(k).point;
					if (point < rightpart.size()) {
						if (rightpart.get(point).equals(jcache)) {
							// �����������ж����ģ�������Ŀ����ĳ����Ŀ��point����ַ��� �����еĵ�k���ַ���ͬ��Ҳ����E->��E-T�����ַ�E
							Item item = new Item(iitemset.items.get(k));// ����һ���µ���Ŀ
							newITS.initItemSet(item, G);// ���´�������Ŀ���뵽�µ���Ŀ����
						}
					}
				}
				/* ����µ�״̬������������Ŀ���淶���У��ͽ�����ӵ���Ŀ���淶���� */
				int l = 0;
				for (l = 0; l < itemsets.size(); l++) {
					if (newITS.equals(itemsets.get(l).items.get(0), newITS.items.get(0))) {
						break;
					}
				}
				if (l >= itemsets.size()) {
					newITS.number = number;
					itemsets.add(newITS);
					number++;
				}
			}
		}
	}
}
