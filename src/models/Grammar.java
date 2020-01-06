package models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * 类名称：Grammar 类描述：文法 创建时间：2019年12月30日 下午10:21:03
 */
final public class Grammar {
	private List<Production> productions = new ArrayList<Production>(); // 产生式列表，保存了多条产生式后就叫做文法。
	private List<First> firsts = new ArrayList<First>(); // 文法的First集
	private List<Follow> follows = new ArrayList<Follow>(); // 文法的Follow集
	private List<String> terminals = new ArrayList<String>();
	private List<String> non_terminals = new ArrayList<String>();

	public List<String> getTerminals() {
		return terminals;
	}

	public void setTerminals(List<String> terminals) {
		this.terminals = terminals;
	}

	public List<String> getNon_terminals() {
		return non_terminals;
	}

	public void setNon_terminals(List<String> non_terminals) {
		this.non_terminals = non_terminals;
	}

	public List<Production> getproductions() {
		return productions;
	}

	public void setproductions(List<Production> productions) {
		this.productions = productions;
	}

	public List<First> getFirsts() {
		return firsts;
	}

	public void setFirsts(List<First> firsts) {
		this.firsts = firsts;
	}

	public List<Follow> getFollows() {
		return follows;
	}

	public void setFollows(List<Follow> follows) {
		this.follows = follows;
	}

	/** 返回第i条产生式，从1开始。 */
	public Production getProduction(int index) {
		return productions.get(index + 1);
	}

	// 类主要方法部分↓
	public Grammar() {

	}

	@SuppressWarnings("resource")
	public Grammar(String path) {
		int number = 0;
		try {
			FileReader fr = new FileReader(path);
			BufferedReader bf = new BufferedReader(fr);
			String s = bf.readLine();
			while (s != null) {
				number++;
				Production production = new Production(s);
				production.number = number;
				productions.add(production);
				s = bf.readLine();
			}
		} catch (IOException e) {
			System.out.println("打开文件失败，错误原因：" + e.toString());
		}
		createNon_terminals(); // 根据文法，计算非终结符
		createterminals(); // 根据文法，计算终结符
		createFirst(); // 根据文法，计算First集
		createFollow();// 根据文法，计算Follow集
	}

	/** 根据文法，生成非终结符集合 */
	private void createNon_terminals() {
		int index = 0;
		while (index < productions.size()) {
			// indexString：定义一个中间变量字符串，用来保存第index条产生式的左部
			String indexString = productions.get(index).leftPart;
			if (!non_terminals.contains(indexString)) {
				non_terminals.add(indexString);
			}
			index++;
		}
	}

	/** 根据文法，生成终结符集合 */
	private void createterminals() {
		int index = 0;
		while (index < productions.size()) {
			int temp = 0;
			while (temp < productions.get(index).rightParts.size()) {
				// indexString：定义一个中间变量字符串，用来保存第index条产生式的右部的第temp个符号
				String indexString = productions.get(index).rightParts.get(temp);
				if (!non_terminals.contains(indexString)&&!terminals.contains(indexString)) {
					// 如果不在非终结符集合中，没有被添加到终结符集合，就将该终结符保存
					terminals.add(indexString);
				}
				temp++;
			}
			index++;
		}
	}

	/** 就是根据终结符和非终结符还有文法生成first集 */
	private void createFirst() {
		boolean _ALTERED_ = true; // 判断是否对First集进行过修改
		for (int i = 0; i < non_terminals.size(); i++) {
			firsts.add(new First()); // 为firsts集合分配空间
		}
		while (_ALTERED_) {
			_ALTERED_ = false; // 算法一开始先置将_ALTERED_置为false，表示未修改过
			for (int i = 0; i < non_terminals.size(); i++) { // 循环对每一个非终结符进行判断
				for (int j = 0; j < productions.size(); j++) { // 循环对文法中的每一条产生式进行判断
					String i_non_terminals = non_terminals.get(i); // 第i个非终结符
					Production j_production = productions.get(j); // 第j条产生式
					if (j_production.leftPart.equals(i_non_terminals)) {
						// 第j条产生式的左部就是这个非终结符
						if (!firsts.get(i).getFirst().contains(j_production.rightParts.get(0))
								&& terminals.contains(j_production.rightParts.get(0))) {
							// 第j条产生式的右部的第一个字符是终结符，就将这个字符添加到第i个非终结符对应first集中
							firsts.get(i).getFirst().add(j_production.rightParts.get(0));
							_ALTERED_ = true;
						} else if (non_terminals.contains(j_production.rightParts.get(0))) {
							// 第j条产生式的右部的第一个字符不是终结符，而是非终结符，那么要把第j条产生式右部第一个字符的first集中的所有元素添加到第i个非终结符的first集中。
							if (firsts.get(i)
									.setFirst(firsts.get(non_terminals.indexOf(j_production.rightParts.get(0))))) {
								_ALTERED_ = true;
							}
							//如果第j条产生式右部的第一个非终结符的first集中包含$，将第j条产生式右部的第二个符号也添加到first集中
							if(firsts.get(non_terminals.indexOf(j_production.rightParts.get(0))).getFirst().contains("~")) {
								if(j_production.rightParts.size() > 1) {
									if (!firsts.get(i).getFirst().contains(j_production.rightParts.get(1))
											&& terminals.contains(j_production.rightParts.get(1))) {
										// 第j条产生式的右部的第一个字符是终结符，就将这个字符添加到第i个非终结符对应first集中
										firsts.get(i).getFirst().add(j_production.rightParts.get(1));
										_ALTERED_ = true;
									} else if (non_terminals.contains(j_production.rightParts.get(1))) {
										// 第j条产生式的右部的第一个字符不是终结符，而是非终结符，那么要把第j条产生式右部第一个字符的first集中的所有元素添加到第i个非终结符的first集中。
										if (firsts.get(i)
												.setFirst(firsts.get(non_terminals.indexOf(j_production.rightParts.get(1))))) {
											_ALTERED_ = true;
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	/** 就是根据终结符和非终结符还有文法生成first集 */
	@SuppressWarnings("unused")
	private void createFollow() {
		boolean _ALTERED_ = true; // 判断是否对Follow集进行过修改
		for (int i = 0; i < non_terminals.size(); i++) {
			follows.add(new Follow()); // 为follows集合分配空间
		}
		/**
		 * 计算Follow集。（该方法并不严谨，还需设置_ALTERED_变量来决定是否再调整一次Follow集，但是目前文法是够用的）
		 * 1、遍历文法中的所有产生式，所有出现在非终结符右边的终结符加入到相应follow集中，如果非终结符右边没有东西，则把$加入。
		 * 2、遍历文法中的所有产生式，若产生式最右边是非终结符，把左部非终结符的follow集的所有元素都添加到对应非终结符中。
		 * 3、将$加入到第一个非终结符的follow集中。
		 */
		/* 第一步 */
		for (int i = 0; i < productions.size(); i++) {
			String leftpart = productions.get(i).leftPart;
			List<String> rightpart = productions.get(i).rightParts;
			for (int j = 0; j < rightpart.size(); j++) {
				String thisone = rightpart.get(j);// 举个例子：thisone表示E->T+F中的T
				int thisone_location = non_terminals.indexOf(thisone);// 用thisnoe_location保存E在非终结符集合中的位置
				if (non_terminals.contains(thisone)) {// 如果T是非终结符
					if (j + 1 < rightpart.size()) {// 如果T后面有东西
						String nextone = rightpart.get(j + 1);// 用nextone保存T后面的东西，例子这边就是+
						if (non_terminals.contains(nextone)) {// 如果+是非终结符
							int nextone_location = non_terminals.indexOf(nextone);// 用nextone_location保存E后面的非终结符在集合中的位置
							follows.get(thisone_location).setfollow(firsts.get(nextone_location));
							if(firsts.get(non_terminals.indexOf(nextone)).getFirst().contains("~")&&j + 2 < rightpart.size()) {
								String next2 = rightpart.get(j + 2);
								if (non_terminals.contains(next2)) {// 如果+是非终结符
									int next2_location = non_terminals.indexOf(next2);// 用nextone_location保存E后面的非终结符在集合中的位置
									follows.get(thisone_location).setfollow(firsts.get(next2_location));
								} else if (terminals.contains(next2)) {// 如果+是终结符
									follows.get(thisone_location).setfollow(next2);
								} else { // 如果+什么都不是，那肯定是程序出错了，报错。
									System.out.println("这个情况不可能出现，如果出现，请检查操作系统是否有问题。");
								}
							}
						} else if (terminals.contains(nextone)) {// 如果+是终结符
							follows.get(thisone_location).setfollow(nextone);
						} else { // 如果+什么都不是，那肯定是程序出错了，报错。
							System.out.println("这个情况不可能出现，如果出现，请检查操作系统是否有问题。");
						}
					} else {// 如果T后面没有东西
						follows.get(thisone_location).setfollow("$");
					}
				}
			}
		}
		/* 第二步 */
		for (int i = 0; i < productions.size(); i++) {
			String leftpart = productions.get(i).leftPart;
			int leftpart_location = non_terminals.indexOf(leftpart);// 左部在非终结符列表中的位置
			List<String> rightpart = productions.get(i).rightParts;
			String last = rightpart.get(rightpart.size() - 1);
			if(non_terminals.contains(last)&&follows.get(non_terminals.indexOf(last)).getfollow().contains("~")&&rightpart.size() > 1) {
				String last2 = rightpart.get(rightpart.size()-2);
				if (non_terminals.contains(last2)) {
					// 如果产生式右边的最后一个字符是非终结符
					int last2_location = non_terminals.indexOf(last2);// 最后一个字符在非终结符集合中的位置
					follows.get(last2_location).setfollow(follows.get(leftpart_location));// 为最后一个非终结符添加该产生式左部非终结符的follow集
				}
			}
			if (non_terminals.contains(last)) {
				// 如果产生式右边的最后一个字符是非终结符
				int last_location = non_terminals.indexOf(last);// 最后一个字符在非终结符集合中的位置
				follows.get(last_location).setfollow(follows.get(leftpart_location));// 为最后一个非终结符添加该产生式左部非终结符的follow集
			}
			
		}
		/* 第三步 */
		follows.get(0).getfollow().add("$");
	}

	public void printAll() {
		printTerminalAndNonTerminal();
		printGrammar();
		System.out.println();
		printFirst();
		System.out.println();
		printFollow();
	}

//System.out.println();
	public void printFirst() {
		System.out.println("文法的First集为：");
		for (int i = 0; i < firsts.size(); i++) {
			System.out.print("First(" + non_terminals.get(i) + ") = { ");
			for (int j = 0; j < firsts.get(i).getFirst().size(); j++) {
				System.out.print(firsts.get(i).getFirst().get(j) + " ");
			}
			System.out.println("}");
		}
	}

	public void printFollow() {
		System.out.println("文法的Follow集为：");
		for (int i = 0; i < follows.size(); i++) {
			System.out.print("Follow(" + non_terminals.get(i) + ") = { ");
			for (int j = 0; j < follows.get(i).getfollow().size(); j++) {
				System.out.print(follows.get(i).getfollow().get(j) + " ");
			}
			System.out.println("}");
		}
	}

	public void printGrammar() {
		System.out.println("文法如下所示：");
		for (int i = 0; i < productions.size(); i++) {
			System.out.print("(" + productions.get(i).number + ")");
			System.out.print(productions.get(i).leftPart + "->");
			for (int j = 0; j < productions.get(i).rightParts.size(); j++) {
				System.out.print(productions.get(i).rightParts.get(j));
			}
			System.out.println();
		}
	}

	public void printTerminalAndNonTerminal() {
		System.out.println("终结符：" + terminals);
		System.out.println("非终结符：" + non_terminals);
	}
}