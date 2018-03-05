package domain;


public class Manager extends Actor{
	private boolean suspicious;

	
	public boolean isSuspicious() {
		return suspicious;
	}

	
	public void setSuspicious(boolean suspicious) {
		this.suspicious = suspicious;
	}
}
