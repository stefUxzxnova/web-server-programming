package uni.fmi.plovdiv.exception;

import org.springframework.http.HttpStatus;
import lombok.Data;

@Data
public class ErrorBean {
  
  private String message;

  private HttpStatus code;

  public ErrorBean(HttpStatus code, String message) {
    this.code = code;
    this.message = message;
  }

  public ErrorBean(String message) {
    this.message = message;
  }
  

}
