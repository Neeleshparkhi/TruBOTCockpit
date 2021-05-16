package exceptions;

import java.util.logging.Logger;


public class ElementNotFoundException extends Throwable{

	
	private static final long serialVersionUID = -1316208727164968710L;

	

	public  ElementNotFoundException(String s, Logger LOGGER) {
		LOGGER.severe(s);
	}

}
