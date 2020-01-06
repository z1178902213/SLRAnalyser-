package tools;

import java.util.Formatter;
import java.util.List;
import java.util.Stack;

/*
将下列文法保存在D盘根目录下的Grammar.txt文件中
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
	 * SLR分析器
	 * 
	 * 输入：文法保存的位置可以创建从first集到预测分析表的所有资料
	 * 
	 * 输入：给出待判断程序词法分析结果可以输出程序运行时的栈内内容的变化和程序正误。
	 * 
	 * 输出：可以选择性的输出所有结果，如First、follow、项目集规范族、SLR预测分析表等等。
	 */
	public SLRAnalyser() {
		Grammar G = new Grammar("D://Grammar.txt");
		G.printAll();// 输出文法，first集，follow集
		ItemFamily ItFml = new ItemFamily(G);
		ItFml.print();// 输出项目集规范族
		SLRAnalyserTable analyserTable = new SLRAnalyserTable(G, ItFml);
		LexicalAnalyser lexicalAnalyser = new LexicalAnalyser();
		List<Token> tokenlist = lexicalAnalyser.analise(G);
		System.out.println("词法分析后的结果：" + tokenlist.toString() + "\n");
		Stack<Token> stack = lexicalAnalyser.convertToStack(G, tokenlist);
		analyse(stack, G, analyserTable);
	}

	@SuppressWarnings("resource")
	public boolean analyse(Stack<Token> stack, Grammar G, SLRAnalyserTable analyserTable) {
		// 传入的参数stack是输入缓冲区的栈
		Formatter f1 = new Formatter(System.out);
		int s, t;
		Stack<Integer> p = new Stack<Integer>();
		Stack<String> p2 = new Stack<String>();
		List<String> terminal = G.getTerminals();
		List<String> non_terminal = G.getNon_terminals();
		p.add(0);
		// a是输入缓冲区的第一个符号
		// t是表中所指示的状态
		// s是程序的栈的栈顶状态
		Token a = stack.peek(); // 令a是w$的第一个符号
		f1.format("栈\t\t\t\t\t\t\t\t输入\t\t动作\n");
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
			// s等于栈顶状态
			s = p.peek();
			try {
				t = analyserTable.getACTION().get(s).get(a.getTokenType()).getNumber();
				if (analyserTable.getACTION().get(s).get(a.getTokenType()).getAction() == 's') {
					// 把a和t保存在index中并压入栈
					p.push(t);
					p2.push(terminal.get(a.getTokenType()));
					// 令a是下一个输入符号
					stack.pop();
					a = stack.peek();
					string = string + "移进";
				} else if (analyserTable.getACTION().get(s).get(a.getTokenType()).getAction() == 'r') {
					// 栈顶退掉2*|β|个符号
					int y = analyserTable.getACTION().get(s).get(a.getTokenType()).getNumber() - 1;
					for (int i = G.getproductions().get(y).getRightParts().size(); i > 0; i--) {
						p.pop();
						p2.pop();
					}
					// 令t是现在的栈顶状态
					t = p.peek();
					// 把Goto(t,A)压入栈
					String A = G.getproductions().get(y).getLeftPart();
					p.push(analyserTable.getGOTO().get(t).get(non_terminal.indexOf(A)).getNumber());
					p2.push(A);
					// 输出产生式A->β
					string = string + "按" + G.getproductions().get(y).getLeftPart() + "->"
							+ G.getproductions().get(y).getRightParts() + "归约";
				} else if (analyserTable.getACTION().get(s).get(a.getTokenType()).getAction() == 'a') {
					// 分析完成
					string = string + "接受";
					System.out.println(string);
					System.out.println("程序正确");
					break;
				} else {
					// 调用错误恢复历程
					System.out.println("\t\t出错了，输入的程序有误，请检查程序");
					System.exit(0);
					break;
				}
				System.out.println(string);
			} catch (Exception e) {
				System.out.println("\n分析出现错误：输入的程序中出现了文法不可识别的字符，其虽然可以通过词法分析，但文法中不存在对应字符，故出现语法错误。");
				System.exit(0);
			}
		}
		return false;
	}
}
