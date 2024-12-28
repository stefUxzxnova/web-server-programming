package uni.fmi.plovdiv.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uni.fmi.plovdiv.exception.ErrorBean;
import uni.fmi.plovdiv.exception.InvalidCarIdException;
import uni.fmi.plovdiv.exception.InvalidGarageIdException;

@ControllerAdvice
public class ErrorController {
  
  @ExceptionHandler(InvalidGarageIdException.class)
  public ResponseEntity<ErrorBean> handleInvalidGarageException(Exception e) {
    ErrorBean errorBean = new ErrorBean(HttpStatus.NOT_FOUND,"There is no existing garage with the given id");
    return new ResponseEntity<>(errorBean, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(InvalidCarIdException.class)
  public ResponseEntity<ErrorBean> handleInvalidCarException(Exception e) {
    ErrorBean errorBean = new ErrorBean(HttpStatus.NOT_FOUND, "There is no existing car with the given id");
    return new ResponseEntity<>(errorBean, HttpStatus.NOT_FOUND);
  }


}
