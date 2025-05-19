package com.portalasig.ms.commons.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Iterator;
import java.util.List;

/**
 * Paginated response containing a subset of the result set.
 *
 * @param <T> the type of elements in the paginated response
 */
@ApiModel(description = "A paginated aware response including paging information with an Array of ")
@Data
public class Paginated<T> implements Iterable<T> {

    @ApiModelProperty("The array of data/content")
    @JsonProperty
    private List<T> content;

    @ApiModelProperty("Total number of elements in the returned subset")
    @JsonProperty
    private long totalElements;

    @ApiModelProperty("Total number of pages in the result set")
    @JsonProperty
    private int totalPages;

    @ApiModelProperty("Total elements in the result set")
    @JsonProperty
    private long totalCount;

    @ApiModelProperty("The maximum number of elements in each page")
    @JsonProperty
    private int size;

    @ApiModelProperty("Current page number")
    @JsonProperty
    private int currentPage;

    @ApiModelProperty("Links (if applicable) to previous and next pages")
    @JsonProperty
    private PageLinks links;

    private static PageLinks createPageLinks(Page<?> page, UriComponentsBuilder basePath) {
        String next = null;
        if (page.getNumber() + 1 < page.getTotalPages()) {
            next = basePath.cloneBuilder()
                    .replaceQueryParam("page", page.getNumber() + 1)
                    .replaceQueryParam("size", page.getSize())
                    .build()
                    .toUriString();
        }

        String prev = null;
        if (page.getNumber() > 0) {
            prev = basePath.cloneBuilder()
                    .replaceQueryParam("page", page.getNumber() - 1)
                    .replaceQueryParam("size", page.getSize())
                    .build()
                    .toUriString();
        }

        return (prev == null && next == null) ? null : new PageLinks(prev, next);
    }

    private static <T> Paginated<T> wrap(Page<T> page, List<T> content) {
        Paginated<T> p = new Paginated<>();
        p.content = content;
        p.totalPages = page.getTotalPages();
        p.totalElements = page.getNumberOfElements();
        p.totalCount = page.getTotalElements();
        p.size = page.getSize();
        p.currentPage = page.getNumber();
        return p;
    }

    /**
     * Creates a paginated response from a page.
     *
     * @param <T>      element type
     * @param page     source page
     * @param content  page elements
     * @param basePath base URI path for links
     * @return paginated
     */
    public static <T> Paginated<T> wrap(Page<T> page, List<T> content, UriComponentsBuilder basePath) {
        Paginated<T> p = wrap(page, content);
        p.links = createPageLinks(page, basePath);
        return p;
    }

    /**
     * Creates a paginated response from a page.
     *
     * @param <T>      element type
     * @param page     source page
     * @param content  page elements
     * @param basePath base URI path for links
     * @return paginated
     */
    public static <T> Paginated<T> wrap(Page<T> page, List<T> content, String basePath) {
        return wrap(page, content, UriComponentsBuilder.fromUriString(basePath));
    }

    /**
     * Creates a paginated response from a page.
     *
     * @param <T>      element type
     * @param page     source page
     * @param basePath base URI path for links
     * @return paginated
     */
    public static <T> Paginated<T> wrap(Page<T> page, String basePath) {
        return wrap(page, page.getContent(), basePath);
    }

    /**
     * Creates a paginated response using the current request URI as the base path for links.
     *
     * @param <T>  element type
     * @param page page of data
     * @return paginated response
     */
    public static <T> Paginated<T> wrap(Page<T> page) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest currentRequest = ((ServletRequestAttributes) requestAttributes).getRequest();

        UriComponentsBuilder basePath = UriComponentsBuilder.fromPath(currentRequest.getRequestURI())
                .query(currentRequest.getQueryString());

        return wrap(page, page.getContent(), basePath);
    }

    /**
     * Creates a paginated response from a page excluding page links.
     *
     * @param <T>  element type
     * @param page page of data
     * @return paginated response
     */
    public static <T> Paginated<T> wrapExcludingPageLinks(Page<T> page) {
        return wrap(page, page.getContent());
    }

    @JsonIgnore
    @Override
    public Iterator<T> iterator() {
        return content.iterator();
    }
}