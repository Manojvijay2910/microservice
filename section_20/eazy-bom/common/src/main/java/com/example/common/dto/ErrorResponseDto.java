package com.example.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Schema(
    name = "ErrorResponse",
    description = "Schema to hold error response information"
)
@Data @AllArgsConstructor
public class ErrorResponseDto {

    @Schema(
        description = "API path"
    )
    private String apiPath;

    @Schema(
        description = "Error code"
    )
    private HttpStatus errorCode;

    @Schema(
        description = "Error message"
    )
    private String errorMsg;

    @Schema(
        description = "Time of error"
    )
    private LocalDateTime errorTime;

}
