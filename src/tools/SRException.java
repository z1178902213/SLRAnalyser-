package tools;

public class SRException extends Exception {

	/**
	 * ��ͻ�쳣��
	 */
	private static final long serialVersionUID = 1L;
	public String error;

	public SRException() {

	}

	public SRException(int i) {
		switch (i) {
		case 1:
			error = "�ķ����󣺷������ƽ�-��Լ��ͻ�����ķ����ܲ���SLR�ķ�������رգ�";
			break;
		case 2:
			error = "�ķ����󣺷����˹�Լ-��Լ��ͻ�����ķ���������SLR�ķ�������رգ�";
			break;
		default:
			error = "�ķ����󣺷�����δ֪���󣬳���رգ�";
			break;
		}
	}
}
