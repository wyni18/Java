// Name: Yanni Wang	
// USC loginid: yanniwan 
// CS 455 PA4
// Spring 2013
import java.io.IOException;

/**
   This class reports bad input data.
*/
public class BadArgumentDataException extends IOException
{
   public BadArgumentDataException() {}
   public BadArgumentDataException(String message)
   {
      super(message);
   }
}