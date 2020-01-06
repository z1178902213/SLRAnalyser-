package models;

public class Token {
	private int TokenType;
	private String Value = new String();
	public int getTokenType() {
		return TokenType;
	}
	public String getValue() {
		return Value;
	}
	public void setToken(int TokenType, String Value) {
		this.TokenType = TokenType;
		this.Value = Value;
	}
	@Override
	public String toString() {
		return "(" + TokenType + ", " + Value + ")";
	}
}
