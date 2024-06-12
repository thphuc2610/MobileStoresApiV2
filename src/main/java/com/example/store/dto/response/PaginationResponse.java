package com.example.store.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginationResponse<T> {
    private List<T> content;
    private boolean first;
    private boolean last;
    private int totalPages;
    private long totalItems;
    private int size;
    private int page;
}
