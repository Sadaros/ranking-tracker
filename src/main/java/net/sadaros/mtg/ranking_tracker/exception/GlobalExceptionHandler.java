package net.sadaros.mtg.ranking_tracker.exception;

import jakarta.annotation.Nullable;
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
        String message;
        String invalidField = "";
        Map<String, String> invalidFields = null;

        if (cause instanceof InvalidFormatException) {
            message = "One or more fields are invalid";
            invalidField += ("Invalid value '");
            invalidField += (((InvalidFormatException) cause).getValue());
            invalidField += ("' for field of type ");
            invalidField += (((InvalidFormatException) cause).getTargetType().getSimpleName());
            Class<?> targetType = ((InvalidFormatException) cause).getTargetType();
            if (targetType.isEnum()) {
                invalidField += (". Accepted values are: ");
                invalidField += (Arrays.toString(targetType.getEnumConstants()));
            }
            invalidFields = new HashMap<>();
            invalidFields.put(((InvalidFormatException) cause).getPath().getLast().getPropertyName(), invalidField);

        } else if (cause instanceof StreamReadException) {
            message = (ex.getMessage());
        } else {
            message = ("Unknown Error");
            logger.warn("Unknown error: {}", ex.getCause().toString());
        }

        ErrorResponse errorResponse = createErrorResponse(request, "Bad request", invalidFields, 400, message);

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

        ErrorResponse errorResponse = createErrorResponse(request, "Bad request", invalidFields, 400, "One or more fields invalid");

        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundExceptions(
            ResourceNotFoundException ex,
            HttpServletRequest request) {
        Map<String, String> invalidFields = null;
        if (ex.getField() != null) {
            invalidFields = new HashMap<>();
            invalidFields.put(ex.getField(), ex.getMessage());
        }
        ErrorResponse errorResponse = createErrorResponse(request, "Not found", invalidFields, 404, "One or more resources not found");
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(404));
    }

    @ExceptionHandler(DuplicateNameException.class)
    public ResponseEntity<?> handleDuplicateNames(
            DuplicateNameException ex,
            HttpServletRequest request) {
        ErrorResponse errorResponse = createErrorResponse(request, "Conflict", null, 409, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(409));
    }


    private static ErrorResponse createErrorResponse(HttpServletRequest request, String error, @Nullable Map<String, String> invalidFields, int statusCode, String message) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimeStamp(LocalDateTime.now());
        errorResponse.setStatusCode(statusCode);
        errorResponse.setPath(request.getRequestURI());
        errorResponse.setError(error);
        errorResponse.setMessage(message);
        errorResponse.setInvalidFields(invalidFields);
        return errorResponse;
    }
}
