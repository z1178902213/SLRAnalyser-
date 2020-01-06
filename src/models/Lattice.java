package models;

/**
 * 预测分析表中，一个格子的类
 */
public class Lattice {
	char action;
	int number;

	public char getAction() {
		return action;
	}

	public void setAction(char action) {
		this.action = action;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Lattice() {

	}
}
