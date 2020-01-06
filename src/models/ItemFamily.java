package models;

import java.util.*;

/**
 * 项目集规范族
 */

public class ItemFamily {
	List<ItemSet> itemsets = new ArrayList<ItemSet>();

	public ItemFamily() {

	}

	public void print() {
		System.out.println("\n该文法的项目集规范族如下所示：");
		for (int i = 0; i < itemsets.size(); i++) {
			System.out.println("I" + i + "：");
			for (int j = 0; j < itemsets.get(i).items.size(); j++) {
				String s = itemsets.get(i).items.get(j).leftPart;
				s = s + "->";
				for (int k = 0; k < itemsets.get(i).items.get(j).rightParts.size(); k++) {
					if (itemsets.get(i).items.get(j).point == k) {
						s = s + "·";
					}
					s = s + itemsets.get(i).items.get(j).rightParts.get(k);
				}
				if (itemsets.get(i).items.get(j).point == itemsets.get(i).items.get(j).rightParts.size()) {
					s = s + "·";
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
		String s = "START->" + G.getProduction(0).leftPart;// 构造拓广文法
		Production _START_ = new Production(s);// 将拓广文法以产生式形式保存
		Item item = new Item(_START_);// 将产生式形式的拓广文法用项目形式保存，类似START->·E
//		ItemSet is = new ItemSet(item, G);// 根据第一个产生式START->·E推导出其余项目并组建成项目集
		ItemSet is = new ItemSet();
		is.initItemSet(item, G);
		is.number = 0;
		itemsets.add(is);// 根据拓广文法产生的项目集I0，将I0添加到项目集规范族中
	}

	private void createFamily(Grammar G) {
		int number = 1;
		for (int i = 0; i < itemsets.size(); i++) {// itemsets.size是动态的，会随着程序的执行而变化
			List<String> caching = new ArrayList<String>();// 缓存
			ItemSet iitemset = itemsets.get(i);// 用iitemset表示第i个项目集
			/* 遍历一遍第i个项目集，将所有出现在点后面的字符添加到缓存中 */
			for (int j = 0; j < iitemset.items.size(); j++) {
				Item jitem = iitemset.items.get(j);// 用jitem表示第j个项目
				if (jitem.getPoint() >= jitem.getRightParts().size()) {
					// 如果·后面没有东西，就跳过这个项目的判断。
					continue;
				}
				if (!caching.contains(jitem.rightParts.get(jitem.getPoint()))) {
					// 如果·后面有东西，将这个东西添加到caching缓存中。
					caching.add(jitem.rightParts.get(jitem.getPoint()));
				}
			}

			/* 遍历缓存，表示逐个读入状态 */
			for (int j = 0; j < caching.size(); j++) {
				String jcache = caching.get(j);// jcache表示第j个可以读入的状态
				ItemSet newITS = new ItemSet();
				for (int k = 0; k < iitemset.items.size(); k++) {
					List<String> rightpart = iitemset.items.get(k).rightParts;
					int point = iitemset.items.get(k).point;
					if (point < rightpart.size()) {
						if (rightpart.get(point).equals(jcache)) {
							// 如果点后面是有东西的，并且项目集中某个项目的point后的字符和 缓存中的第k个字符相同。也就是E->·E-T读入字符E
							Item item = new Item(iitemset.items.get(k));// 创建一个新的项目
							newITS.initItemSet(item, G);// 把新创建的项目加入到新的项目集中
						}
					}
				}
				/* 如果新的状态集不存在于项目集规范族中，就将其添加到项目集规范族中 */
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
