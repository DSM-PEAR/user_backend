package com.dsmpear.main.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageResponse {

    private int totalElements;

    private int totalPages;

}
