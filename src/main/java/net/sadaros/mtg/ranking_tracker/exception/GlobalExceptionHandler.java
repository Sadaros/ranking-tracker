package net.sadaros.mtg.ranking_tracker.exception;

import jakarta.servlet.http.HttpServletRequest;
import net.sadaros.mtg.ranking_tracker.dto.ErrorResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tools.jackson.core.exc.StreamReadException;
import tools.jackson.databind.exc.InvalidFormatException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpServletRequest request) {

        Throwable cause = ex.getCause();
        String error;
        String invalidField = "";
        Map<String, String> invalidFields = new HashMap<>();

        if (cause instanceof InvalidFormatException) {
            error = "Bad Request";
            invalidField += ("Invalid value '");
            invalidField += (((InvalidFormatException) cause).getValue());
            invalidField += ("' for field of type ");
            invalidField += (((InvalidFormatException) cause).getTargetType().getSimpleName());
            Class<?> targetType = ((InvalidFormatException) cause).getTargetType();
            if (targetType.isEnum()) {
                invalidField += (". Accepted values are: ");
                invalidField += (Arrays.toString(targetType.getEnumConstants()));
            }
            invalidFields.put(((InvalidFormatException) cause).getPath().getLast().getPropertyName(), invalidField);

        } else if (cause instanceof StreamReadException) {
            error = (ex.getMessage());
        } else {
            error = ("Unknown Error");
            logger.error("Unknown error: {}", ex.getCause().toString());
        }

        ErrorResponse errorResponse = createErrorResponse(request, error, invalidFields);

        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(400));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        var errors = ex.getBindingResult().getFieldErrors();
        Map<String, String> invalidFields = new HashMap<>();
        for (var error : errors) {
            invalidFields.put(error.getField(), error.getDefaultMessage());
        }

        ErrorResponse errorResponse = createErrorResponse(request, "Bad Request", invalidFields);

        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(400));
    }
    private static ErrorResponse createErrorResponse(HttpServletRequest request, String error, Map<String, String> invalidFields) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimeStamp(LocalDateTime.now());
        errorResponse.setStatusCode(400);
        errorResponse.setPath(request.getRequestURI());
        errorResponse.setError(error);
        if(invalidFields.isEmpty()) {
            errorResponse.setInvalidFields(null);
        } else {
            errorResponse.setInvalidFields(invalidFields);
        }
        return errorResponse;
    }
}
