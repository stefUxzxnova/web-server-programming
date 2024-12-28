package uni.fmi.plovdiv.exception;

public class InvalidCarIdException extends RuntimeException{
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private String message; 
  
  private String code;
  
  public InvalidCarIdException() {
      super();
  }
  
  public InvalidCarIdException(String message) {
    super();
    this.message = message;
  }

  public InvalidCarIdException(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }
  
}
