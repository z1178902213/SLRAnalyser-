package models;

import java.util.*;
import tools.SRException;

public class SLRAnalyserTable {
	private List<List<Lattice>> ACTION = new ArrayList<List<Lattice>>();
	private List<List<Lattice>> GOTO = new ArrayList<List<Lattice>>();

	public SLRAnalyserTable() {

	}

	public List<List<Lattice>> getACTION() {
		return ACTION;
	}

	public void setACTION(List<List<Lattice>> aCTION) {
		ACTION = aCTION;
	}

	public List<List<Lattice>> getGOTO() {
		return GOTO;
	}

	public void setGOTO(List<List<Lattice>> gOTO) {
		GOTO = gOTO;
	}

	public SLRAnalyserTable(Grammar G, ItemFamily itemFamily) {
		try {
			createSLRAnalyserTable(G, itemFamily);
		} catch (SRException e) {
			System.out.println(e.error);
			System.exit(0);
		}
	}

	public void createSLRAnalyserTable(Grammar G, ItemFamily itemFamily) throws SRException {
		// 先把终结符和非终结符保存一下
		List<String> non_terminal = G.getNon_terminals();
		List<String> terminal = G.getTerminals();
		List<Follow> follows = G.getFollows();
		terminal.add("$");
		// 为action表和goto表分配空间
		for (int i = 0; i < itemFamily.itemsets.size(); i++) {
			// 为action表分配空间
			List<Lattice> list1 = new ArrayList<Lattice>();
			for (int j = 0; j < terminal.size(); j++) {
				Lattice lattice = new Lattice();
				list1.add(lattice);
			}
			// 为goto表分配空间
			List<Lattice> list2 = new ArrayList<Lattice>();
			for (int j = 0; j < non_terminal.size(); j++) {
				Lattice lattice = new Lattice();
				list2.add(lattice);
			}
			ACTION.add(list1);
			GOTO.add(list2);
		}
		// 生成SLR分析表
		for (int i = 0; i < itemFamily.itemsets.size(); i++) {
			for (int j = 0; j < itemFamily.itemsets.get(i).items.size(); j++) {
				Item item = itemFamily.itemsets.get(i).items.get(j);
				if (item.point < item.rightParts.size()) {// 点不在产生式右部的最后一个位置，类似E->E·+T
					if (terminal.contains(item.rightParts.get(item.point))) {// 如果点后面的是终结符，类似类似E->E·+T
						int location = terminal.indexOf(item.rightParts.get(item.point));// 用location表示点后面的终结符在终结符集合中的位置
						if (ACTION.get(i).get(location).action != 'r' && ACTION.get(i).get(location).action != 's') {
							ACTION.get(i).get(location).action = 's';
						} else if (ACTION.get(i).get(location).action == 'r') {
							throw new SRException(1);
						}
						for (int k = 1; k < itemFamily.itemsets.size(); k++) {
							Item k1item = itemFamily.itemsets.get(k).items.get(0);
							Item ijitem = itemFamily.itemsets.get(i).items.get(j);
							if (k1item.productionEquals(ijitem) && k1item.point == ijitem.point + 1) {
								ACTION.get(i).get(location).number = k;
								break;
							}
						}
					} else {// 如果点后面的字符是非终结符，类似E->E+·T
						int location = non_terminal.indexOf(item.rightParts.get(item.point));// 用location表示点后面的非终结符在非终结符集合中的位置
						for (int k = 1; k < itemFamily.itemsets.size(); k++) {
							Item k1item = itemFamily.itemsets.get(k).items.get(0);
							Item ijitem = itemFamily.itemsets.get(i).items.get(j);
							if (k1item.productionEquals(ijitem) && k1item.point == ijitem.point + 1) {
								GOTO.get(i).get(location).number = k;
								break;
							}
						}
					}
				} else if (item.point == item.rightParts.size()) {// 点在产生式右部的最后一个位置，类似E->E+T·
					if (item.leftPart.equals("START")) { // 如果左部是START拓广文法，就在action表中对应位置加入acc。因为使用char型字符数组保存的，因此这里保存a
						int location = terminal.indexOf("$");
						ACTION.get(i).get(location).action = 'a';
					} else {
						Follow follow = follows.get(non_terminal.indexOf(item.leftPart));// E->T· 将
						for (int k = 0; k < follow.getfollow().size(); k++) {
							int location = terminal.indexOf(follow.getfollow().get(k));
							if (ACTION.get(i).get(location).action != 'r'
									&& ACTION.get(i).get(location).action != 's') {
								ACTION.get(i).get(location).action = 'r';
								ACTION.get(i).get(location).number = item.number;
							} else if (ACTION.get(i).get(location).action == 's') {
								throw new SRException(1);
							} else {
								throw new SRException(2);
							}
						}
					}
				}
			}
		}
		// 输出SLR预测分析表
		System.out.println("\n预测分析表：");
		for (int i = 0; i < terminal.size(); i++) {
			System.out.print("\t" + terminal.get(i));
		}
		for (int i = 0; i < non_terminal.size(); i++) {
			System.out.print("\t" + non_terminal.get(i));
		}
		System.out.println();
		for (int i = 0; i < ACTION.size(); i++) {
			System.out.print(i);
			for (int j = 0; j < ACTION.get(i).size(); j++) {
				System.out.print("\t" + ACTION.get(i).get(j).action + ACTION.get(i).get(j).number);
			}
			for (int j = 0; j < GOTO.get(i).size(); j++) {
				System.out.print("\t" + GOTO.get(i).get(j).number);
			}
			System.out.println();
		}
	}

	public void print() {

	}
}
