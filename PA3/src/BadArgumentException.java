// Name: Yanni Wang	
// USC loginid: yanniwan 
// CS 455 PA3
// Spring 2013
import java.io.IOException;

/**
   This class reports bad augument
 */
public class BadArgumentException extends IOException {
	public BadArgumentException() {}
	public BadArgumentException(String message) {
		super(message);
	}

}
