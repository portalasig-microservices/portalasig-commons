package com.portalasig.ms.commons.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Provides relative paging links for a collection of entities.
 */
@AllArgsConstructor
@ApiModel(description = "Page relative links for entity collections")
@Data
@NoArgsConstructor
public class PageLinks {

    @ApiModelProperty("Provides the path to the previous page, if applicable")
    @JsonProperty
    private String prev;

    @ApiModelProperty("Provides the path to the next page, if applicable")
    @JsonProperty
    private String next;
}
