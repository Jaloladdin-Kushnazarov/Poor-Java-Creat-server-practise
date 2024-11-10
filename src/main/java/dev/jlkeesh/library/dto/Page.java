package dev.jlkeesh.library.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Page<T> {
    private int elementsPerPage;
    private int currentPage;
    private long totalPages;
    private long totalElements;
    private boolean first;
    private boolean last;
    private List<T> content;
    private long previous;
    private long next;

    public long getPrevious() {
        return currentPage <= 1 ? 1 : currentPage - 1;
    }

    public long getNext() {
        return currentPage == totalPages ? totalPages : currentPage + 1;
    }
}
