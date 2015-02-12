// Name: Yanni Wang	
// USC loginid: yanniwan 
// CS 455 PA3
// Spring 2013
import java.io.IOException;
/**
   This class reports bad polynomial data
 */

public class BadDataException extends IOException {
	public BadDataException() {}
	public BadDataException(String message) {
		super(message);
	}

}
