package tools;

public class SRException extends Exception {

	/**
	 * 冲突异常类
	 */
	private static final long serialVersionUID = 1L;
	public String error;

	public SRException() {

	}

	public SRException(int i) {
		switch (i) {
		case 1:
			error = "文法错误：发生了移进-归约冲突，该文法可能不是SLR文法，程序关闭！";
			break;
		case 2:
			error = "文法错误：发生了归约-归约冲突，该文法不可能是SLR文法，程序关闭！";
			break;
		default:
			error = "文法错误：发生了未知错误，程序关闭！";
			break;
		}
	}
}
