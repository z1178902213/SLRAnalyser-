package models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * �����ƣ�Grammar ���������ķ� ����ʱ�䣺2019��12��30�� ����10:21:03
 */
final public class Grammar {
	private List<Production> productions = new ArrayList<Production>(); // ����ʽ�б������˶�������ʽ��ͽ����ķ���
	private List<First> firsts = new ArrayList<First>(); // �ķ���First��
	private List<Follow> follows = new ArrayList<Follow>(); // �ķ���Follow��
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

	/** ���ص�i������ʽ����1��ʼ�� */
	public Production getProduction(int index) {
		return productions.get(index + 1);
	}

	// ����Ҫ�������֡�
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
			System.out.println("���ļ�ʧ�ܣ�����ԭ��" + e.toString());
		}
		createNon_terminals(); // �����ķ���������ս��
		createterminals(); // �����ķ��������ս��
		createFirst(); // �����ķ�������First��
		createFollow();// �����ķ�������Follow��
	}

	/** �����ķ������ɷ��ս������ */
	private void createNon_terminals() {
		int index = 0;
		while (index < productions.size()) {
			// indexString������һ���м�����ַ��������������index������ʽ����
			String indexString = productions.get(index).leftPart;
			if (!non_terminals.contains(indexString)) {
				non_terminals.add(indexString);
			}
			index++;
		}
	}

	/** �����ķ��������ս������ */
	private void createterminals() {
		int index = 0;
		while (index < productions.size()) {
			int temp = 0;
			while (temp < productions.get(index).rightParts.size()) {
				// indexString������һ���м�����ַ��������������index������ʽ���Ҳ��ĵ�temp������
				String indexString = productions.get(index).rightParts.get(temp);
				if (!non_terminals.contains(indexString)&&!terminals.contains(indexString)) {
					// ������ڷ��ս�������У�û�б���ӵ��ս�����ϣ��ͽ����ս������
					terminals.add(indexString);
				}
				temp++;
			}
			index++;
		}
	}

	/** ���Ǹ����ս���ͷ��ս�������ķ�����first�� */
	private void createFirst() {
		boolean _ALTERED_ = true; // �ж��Ƿ��First�����й��޸�
		for (int i = 0; i < non_terminals.size(); i++) {
			firsts.add(new First()); // Ϊfirsts���Ϸ���ռ�
		}
		while (_ALTERED_) {
			_ALTERED_ = false; // �㷨һ��ʼ���ý�_ALTERED_��Ϊfalse����ʾδ�޸Ĺ�
			for (int i = 0; i < non_terminals.size(); i++) { // ѭ����ÿһ�����ս�������ж�
				for (int j = 0; j < productions.size(); j++) { // ѭ�����ķ��е�ÿһ������ʽ�����ж�
					String i_non_terminals = non_terminals.get(i); // ��i�����ս��
					Production j_production = productions.get(j); // ��j������ʽ
					if (j_production.leftPart.equals(i_non_terminals)) {
						// ��j������ʽ���󲿾���������ս��
						if (!firsts.get(i).getFirst().contains(j_production.rightParts.get(0))
								&& terminals.contains(j_production.rightParts.get(0))) {
							// ��j������ʽ���Ҳ��ĵ�һ���ַ����ս�����ͽ�����ַ���ӵ���i�����ս����Ӧfirst����
							firsts.get(i).getFirst().add(j_production.rightParts.get(0));
							_ALTERED_ = true;
						} else if (non_terminals.contains(j_production.rightParts.get(0))) {
							// ��j������ʽ���Ҳ��ĵ�һ���ַ������ս�������Ƿ��ս������ôҪ�ѵ�j������ʽ�Ҳ���һ���ַ���first���е�����Ԫ����ӵ���i�����ս����first���С�
							if (firsts.get(i)
									.setFirst(firsts.get(non_terminals.indexOf(j_production.rightParts.get(0))))) {
								_ALTERED_ = true;
							}
							//�����j������ʽ�Ҳ��ĵ�һ�����ս����first���а���$������j������ʽ�Ҳ��ĵڶ�������Ҳ��ӵ�first����
							if(firsts.get(non_terminals.indexOf(j_production.rightParts.get(0))).getFirst().contains("~")) {
								if(j_production.rightParts.size() > 1) {
									if (!firsts.get(i).getFirst().contains(j_production.rightParts.get(1))
											&& terminals.contains(j_production.rightParts.get(1))) {
										// ��j������ʽ���Ҳ��ĵ�һ���ַ����ս�����ͽ�����ַ���ӵ���i�����ս����Ӧfirst����
										firsts.get(i).getFirst().add(j_production.rightParts.get(1));
										_ALTERED_ = true;
									} else if (non_terminals.contains(j_production.rightParts.get(1))) {
										// ��j������ʽ���Ҳ��ĵ�һ���ַ������ս�������Ƿ��ս������ôҪ�ѵ�j������ʽ�Ҳ���һ���ַ���first���е�����Ԫ����ӵ���i�����ս����first���С�
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

	/** ���Ǹ����ս���ͷ��ս�������ķ�����first�� */
	@SuppressWarnings("unused")
	private void createFollow() {
		boolean _ALTERED_ = true; // �ж��Ƿ��Follow�����й��޸�
		for (int i = 0; i < non_terminals.size(); i++) {
			follows.add(new Follow()); // Ϊfollows���Ϸ���ռ�
		}
		/**
		 * ����Follow�������÷��������Ͻ�����������_ALTERED_�����������Ƿ��ٵ���һ��Follow��������Ŀǰ�ķ��ǹ��õģ�
		 * 1�������ķ��е����в���ʽ�����г����ڷ��ս���ұߵ��ս�����뵽��Ӧfollow���У�������ս���ұ�û�ж��������$���롣
		 * 2�������ķ��е����в���ʽ��������ʽ���ұ��Ƿ��ս�������󲿷��ս����follow��������Ԫ�ض���ӵ���Ӧ���ս���С�
		 * 3����$���뵽��һ�����ս����follow���С�
		 */
		/* ��һ�� */
		for (int i = 0; i < productions.size(); i++) {
			String leftpart = productions.get(i).leftPart;
			List<String> rightpart = productions.get(i).rightParts;
			for (int j = 0; j < rightpart.size(); j++) {
				String thisone = rightpart.get(j);// �ٸ����ӣ�thisone��ʾE->T+F�е�T
				int thisone_location = non_terminals.indexOf(thisone);// ��thisnoe_location����E�ڷ��ս�������е�λ��
				if (non_terminals.contains(thisone)) {// ���T�Ƿ��ս��
					if (j + 1 < rightpart.size()) {// ���T�����ж���
						String nextone = rightpart.get(j + 1);// ��nextone����T����Ķ�����������߾���+
						if (non_terminals.contains(nextone)) {// ���+�Ƿ��ս��
							int nextone_location = non_terminals.indexOf(nextone);// ��nextone_location����E����ķ��ս���ڼ����е�λ��
							follows.get(thisone_location).setfollow(firsts.get(nextone_location));
							if(firsts.get(non_terminals.indexOf(nextone)).getFirst().contains("~")&&j + 2 < rightpart.size()) {
								String next2 = rightpart.get(j + 2);
								if (non_terminals.contains(next2)) {// ���+�Ƿ��ս��
									int next2_location = non_terminals.indexOf(next2);// ��nextone_location����E����ķ��ս���ڼ����е�λ��
									follows.get(thisone_location).setfollow(firsts.get(next2_location));
								} else if (terminals.contains(next2)) {// ���+���ս��
									follows.get(thisone_location).setfollow(next2);
								} else { // ���+ʲô�����ǣ��ǿ϶��ǳ�������ˣ�����
									System.out.println("�����������ܳ��֣�������֣��������ϵͳ�Ƿ������⡣");
								}
							}
						} else if (terminals.contains(nextone)) {// ���+���ս��
							follows.get(thisone_location).setfollow(nextone);
						} else { // ���+ʲô�����ǣ��ǿ϶��ǳ�������ˣ�����
							System.out.println("�����������ܳ��֣�������֣��������ϵͳ�Ƿ������⡣");
						}
					} else {// ���T����û�ж���
						follows.get(thisone_location).setfollow("$");
					}
				}
			}
		}
		/* �ڶ��� */
		for (int i = 0; i < productions.size(); i++) {
			String leftpart = productions.get(i).leftPart;
			int leftpart_location = non_terminals.indexOf(leftpart);// ���ڷ��ս���б��е�λ��
			List<String> rightpart = productions.get(i).rightParts;
			String last = rightpart.get(rightpart.size() - 1);
			if(non_terminals.contains(last)&&follows.get(non_terminals.indexOf(last)).getfollow().contains("~")&&rightpart.size() > 1) {
				String last2 = rightpart.get(rightpart.size()-2);
				if (non_terminals.contains(last2)) {
					// �������ʽ�ұߵ����һ���ַ��Ƿ��ս��
					int last2_location = non_terminals.indexOf(last2);// ���һ���ַ��ڷ��ս�������е�λ��
					follows.get(last2_location).setfollow(follows.get(leftpart_location));// Ϊ���һ�����ս����Ӹò���ʽ�󲿷��ս����follow��
				}
			}
			if (non_terminals.contains(last)) {
				// �������ʽ�ұߵ����һ���ַ��Ƿ��ս��
				int last_location = non_terminals.indexOf(last);// ���һ���ַ��ڷ��ս�������е�λ��
				follows.get(last_location).setfollow(follows.get(leftpart_location));// Ϊ���һ�����ս����Ӹò���ʽ�󲿷��ս����follow��
			}
			
		}
		/* ������ */
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
		System.out.println("�ķ���First��Ϊ��");
		for (int i = 0; i < firsts.size(); i++) {
			System.out.print("First(" + non_terminals.get(i) + ") = { ");
			for (int j = 0; j < firsts.get(i).getFirst().size(); j++) {
				System.out.print(firsts.get(i).getFirst().get(j) + " ");
			}
			System.out.println("}");
		}
	}

	public void printFollow() {
		System.out.println("�ķ���Follow��Ϊ��");
		for (int i = 0; i < follows.size(); i++) {
			System.out.print("Follow(" + non_terminals.get(i) + ") = { ");
			for (int j = 0; j < follows.get(i).getfollow().size(); j++) {
				System.out.print(follows.get(i).getfollow().get(j) + " ");
			}
			System.out.println("}");
		}
	}

	public void printGrammar() {
		System.out.println("�ķ�������ʾ��");
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
		System.out.println("�ս����" + terminals);
		System.out.println("���ս����" + non_terminals);
	}
}