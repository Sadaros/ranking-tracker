package net.sadaros.mtg.ranking_tracker.exception;

import jakarta.servlet.http.HttpServletRequest;
import net.sadaros.mtg.ranking_tracker.dto.ErrorResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex,
                                                        HttpServletRequest request) {


        var errors = ex.getBindingResult().getFieldErrors();
        Map<String, String> invalidFields = new HashMap<>();
        for (var error : errors) {
            invalidFields.put(error.getField(), error.getDefaultMessage());
        }

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimeStamp(LocalDateTime.now());
        errorResponse.setStatusCode(400);
        errorResponse.setError("Bad request");
        errorResponse.setPath(request.getRequestURI());
        errorResponse.setInvalidFields(invalidFields);


        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(400));
    }
}
