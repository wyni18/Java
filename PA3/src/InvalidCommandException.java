// Name: Yanni Wang	
// USC loginid: yanniwan 
// CS 455 PA3
// Spring 2013
import java.io.IOException;

/**
   This class reports invalid Input
 */
public class InvalidCommandException extends IOException {
	public InvalidCommandException() {}
	public InvalidCommandException(String message) {
		super(message);
	}
}