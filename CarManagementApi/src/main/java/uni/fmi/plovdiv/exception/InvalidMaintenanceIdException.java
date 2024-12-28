package uni.fmi.plovdiv.exception;

public class InvalidMaintenanceIdException extends RuntimeException{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private String message; 
  
  private String code;

  public InvalidMaintenanceIdException() {}

  public InvalidMaintenanceIdException(String message) {
    super(message);
  }

  public InvalidMaintenanceIdException(String message, Throwable cause) {
    super(message, cause);
  }

}
