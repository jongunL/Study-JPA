package jpabook.jpashop.exception;

public class NotEnoughStockExcpetion extends RuntimeException {
	private static final long serialVersionUID = 1L;

	//기본 생성자
	public NotEnoughStockExcpetion() {
		super();
	}
	
	//예외 메시지를 받는 생성자
	public NotEnoughStockExcpetion(String message) {
		super(message);
	}
	
	//예외 메시지와 원인(다른 예외)를 받는 생성자
	public NotEnoughStockExcpetion(String message, Throwable cause) {
		super(message, cause);
	}
	
	//원인(다른 예외)을 받는 생성자
	public NotEnoughStockExcpetion(Throwable cause) {
		super(cause);
	}
	
}
