package com.portalasig.ms.commons.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * API error response body.
 */
@ApiModel(description = "Holds the API Error code, message and request body")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

    @ApiModelProperty("The title for the error type")
    @JsonProperty
    private String title;

    @ApiModelProperty("The status code representing this error")
    @JsonProperty
    private int status;

    @ApiModelProperty("The error message/information")
    @JsonProperty
    private String message;

    @ApiModelProperty("The date/time the error occurred")
    @JsonProperty
    private LocalDateTime dateTime;

    @ApiModelProperty("Specific error code")
    @JsonProperty
    private String errorCode;

}
