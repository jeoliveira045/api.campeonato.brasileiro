package cbf.projeto.api.demo.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import cbf.projeto.api.demo.exception.TimeNullException;

@ControllerAdvice
public class TimeControllerAdvide extends ResponseEntityExceptionHandler{
    @ExceptionHandler(TimeNullException.class)
    public ResponseEntity<Object> capturaErroNull(){
        Map<String, Object> body = new HashMap<String, Object>();

        body.put("message", "Cadastre um time existente");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}
