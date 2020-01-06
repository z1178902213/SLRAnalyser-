package tools;

import java.util.Formatter;
import java.util.List;
import java.util.Stack;

/*
�������ķ�������D�̸�Ŀ¼�µ�Grammar.txt�ļ���
E->E+T
E->E-T
E->T
T->T*F
T->T/F
T->F
F->(E) 
F->id
F->num
*/
import models.Grammar;
import models.ItemFamily;
import models.SLRAnalyserTable;
import models.Token;

public class SLRAnalyser {
	/**
	 * SLR������
	 * 
	 * ���룺�ķ������λ�ÿ��Դ�����first����Ԥ����������������
	 * 
	 * ���룺�������жϳ���ʷ�����������������������ʱ��ջ�����ݵı仯�ͳ�������
	 * 
	 * ���������ѡ���Ե�������н������First��follow����Ŀ���淶�塢SLRԤ�������ȵȡ�
	 */
	public SLRAnalyser() {
		Grammar G = new Grammar("D://Grammar.txt");
		G.printAll();// ����ķ���first����follow��
		ItemFamily ItFml = new ItemFamily(G);
		ItFml.print();// �����Ŀ���淶��
		SLRAnalyserTable analyserTable = new SLRAnalyserTable(G, ItFml);
		LexicalAnalyser lexicalAnalyser = new LexicalAnalyser();
		List<Token> tokenlist = lexicalAnalyser.analise(G);
		System.out.println("�ʷ�������Ľ����" + tokenlist.toString() + "\n");
		Stack<Token> stack = lexicalAnalyser.convertToStack(G, tokenlist);
		analyse(stack, G, analyserTable);
	}

	@SuppressWarnings("resource")
	public boolean analyse(Stack<Token> stack, Grammar G, SLRAnalyserTable analyserTable) {
		// ����Ĳ���stack�����뻺������ջ
		Formatter f1 = new Formatter(System.out);
		int s, t;
		Stack<Integer> p = new Stack<Integer>();
		Stack<String> p2 = new Stack<String>();
		List<String> terminal = G.getTerminals();
		List<String> non_terminal = G.getNon_terminals();
		p.add(0);
		// a�����뻺�����ĵ�һ������
		// t�Ǳ�����ָʾ��״̬
		// s�ǳ����ջ��ջ��״̬
		Token a = stack.peek(); // ��a��w$�ĵ�һ������
		f1.format("ջ\t\t\t\t\t\t\t\t����\t\t����\n");
		while (true) {
			String string = "";
			for (int i = 0; i < p.size(); i++) {
				if (i != 0) {
					string = string + p2.get(i - 1);
				}
				string = string + p.get(i);
			}
			f1.format("%-42s", string);
			string = "";
			for (int i = stack.size() - 1; i >= 0; i--) {
				string = string + stack.get(i).getValue();
			}
			f1.format("%25s", string);
			string = "\t\t";
			// s����ջ��״̬
			s = p.peek();
			try {
				t = analyserTable.getACTION().get(s).get(a.getTokenType()).getNumber();
				if (analyserTable.getACTION().get(s).get(a.getTokenType()).getAction() == 's') {
					// ��a��t������index�в�ѹ��ջ
					p.push(t);
					p2.push(terminal.get(a.getTokenType()));
					// ��a����һ���������
					stack.pop();
					a = stack.peek();
					string = string + "�ƽ�";
				} else if (analyserTable.getACTION().get(s).get(a.getTokenType()).getAction() == 'r') {
					// ջ���˵�2*|��|������
					int y = analyserTable.getACTION().get(s).get(a.getTokenType()).getNumber() - 1;
					for (int i = G.getproductions().get(y).getRightParts().size(); i > 0; i--) {
						p.pop();
						p2.pop();
					}
					// ��t�����ڵ�ջ��״̬
					t = p.peek();
					// ��Goto(t,A)ѹ��ջ
					String A = G.getproductions().get(y).getLeftPart();
					p.push(analyserTable.getGOTO().get(t).get(non_terminal.indexOf(A)).getNumber());
					p2.push(A);
					// �������ʽA->��
					string = string + "��" + G.getproductions().get(y).getLeftPart() + "->"
							+ G.getproductions().get(y).getRightParts() + "��Լ";
				} else if (analyserTable.getACTION().get(s).get(a.getTokenType()).getAction() == 'a') {
					// �������
					string = string + "����";
					System.out.println(string);
					System.out.println("������ȷ");
					break;
				} else {
					// ���ô���ָ�����
					System.out.println("\t\t�����ˣ�����ĳ��������������");
					System.exit(0);
					break;
				}
				System.out.println(string);
			} catch (Exception e) {
				System.out.println("\n�������ִ�������ĳ����г������ķ�����ʶ����ַ�������Ȼ����ͨ���ʷ����������ķ��в����ڶ�Ӧ�ַ����ʳ����﷨����");
				System.exit(0);
			}
		}
		return false;
	}
}
