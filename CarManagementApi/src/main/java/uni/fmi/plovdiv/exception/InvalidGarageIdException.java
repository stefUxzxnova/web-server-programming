package uni.fmi.plovdiv.exception;

public class InvalidGarageIdException extends RuntimeException{
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private String message; 
  
  private String code;
  
  public InvalidGarageIdException() {
      super();
  }
  
  public InvalidGarageIdException(String message) {
    super();
    this.message = message;
  }

  public InvalidGarageIdException(String code, String message) {
    this.code = code;
    this.message = message;
  }

}
