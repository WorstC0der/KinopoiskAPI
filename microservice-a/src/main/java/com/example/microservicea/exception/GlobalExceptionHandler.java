package com.example.microservicea.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@RestControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler {

    ObjectMapper objectMapper;

    private Date parseDateStrToDate(String timestampStr) {
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            return isoFormat.parse(timestampStr);
        } catch (ParseException parseException) {
            return new Date();
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Неправильный запрос",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionDetails.class))})
    })
    @ExceptionHandler(FeignException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDetails handleBadRequestFeignException(FeignException e) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(e.contentUTF8());
        String message = jsonNode.path("message").asText();
        if (message.contains("java.util.NoSuchElementException")) {
            return new ExceptionDetails(new Date(), "Запрашиваемый элемент не существует");
        }
        // Извлечение внутреннего сообщения из message
        if (message.contains("message")) {
            List<String> extendedErrorMessage = Arrays.asList(message.split("\\[|\\]"));
            message = extendedErrorMessage.get(10).trim();
            message = message.substring(1, message.length() - 1);
            message = message.replace("\\\\", "");
        }
        String timestampStr = jsonNode.path("timestamp").asText();
        Date timestamp = parseDateStrToDate(timestampStr);
        return new ExceptionDetails(timestamp, message);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Ошибка в приложении",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionDetails.class))})
    })
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionDetails globalExceptionHandler(Exception e) {
        return new ExceptionDetails(new Date(), e.toString());
    }
}

