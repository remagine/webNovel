package com.arthur.webnovel.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.collect.ImmutableList;

public class Paging {

    private final int totalCount; // 조건에 맞는 전체 건수
    private final int entriesPerPage; // 한페이지에 보여질 엔트리 숫자
    private final int pagesPerPageGroup; // 페이징 그룹에 보일 페이징 갯수
    private final int currentPage; // current page number
    private final int firstPageInCurrentPageGroup;
    private final UriComponentsBuilder ucb;
    private final String param;

    public Paging(int totalCount, int entriesPerPage, int currentPage, int pagesPerPageGroup, UriComponentsBuilder ucb, String param) {
        this.totalCount = totalCount;
        this.entriesPerPage = entriesPerPage;
        this.currentPage = currentPage;
        this.pagesPerPageGroup = pagesPerPageGroup;

        this.firstPageInCurrentPageGroup = (currentPage / pagesPerPageGroup) * pagesPerPageGroup;

        this.ucb = ucb;
        this.param = param;
    }

    public static Builder builder() {
        return new Builder();
    }

    public int getLastPage() {
        if (0 == totalCount) {
            return 0;
        }
        return (totalCount - 1) / entriesPerPage;
    }

    public List<Integer> getPages() {
        int first = firstPageInCurrentPageGroup;
        int last = first + pagesPerPageGroup - 1;
        if (last > getLastPage()) {
            last = getLastPage();
        }

        ImmutableList.Builder<Integer> builder = ImmutableList.<Integer>builder();
        for (int i = first; i < last + 1; i++) {
            builder.add(i);
        }
        return builder.build();
    }

    public boolean hasPrevious() {
        return (currentPage >= pagesPerPageGroup);
    }

    public int getPrevious() {
        return firstPageInCurrentPageGroup - 1;
    }

    public int getPrevPage(){
        return currentPage - 1;
    }

    public boolean hasNext() {
        return firstPageInCurrentPageGroup + pagesPerPageGroup <= getLastPage();
    }

    public int getNext() {
        return firstPageInCurrentPageGroup + pagesPerPageGroup;
    }

    public int getNextPage(){
        return currentPage + 1;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public String link(int page) {
        if (0 == page) {
            return this.ucb.build().encode().toUriString();
        }

        UriComponentsBuilder ucb2 = (UriComponentsBuilder) this.ucb.clone();
        if (StringUtils.isNotBlank(this.param)) {
            ucb2.queryParam(this.param, page);
        } else {
            ucb2.pathSegment(Integer.toString(page));
        }
        return ucb2.build().encode().toUriString();
    }

    public static class Builder {
        private int totalCount;
        private int entriesPerPage = Const.ADMIN_LIST_SIZE;
        private int pagesPerPageGroup = Const.PAGES_PER_PAGE_GROUP;
        private int currentPage = 0;

        private UriComponentsBuilder ucb = UriComponentsBuilder.newInstance();
        private String param = null;

        private Builder() {
        }

        public Builder totalCount(int totalCount) {
            this.totalCount = totalCount;
            return this;
        }

        public Builder entriesPerPage(int entriesPerPage) {
            this.entriesPerPage = entriesPerPage;
            return this;
        }

        public Builder pagesPerPageGroup(int pagesPerPageGroup) {
            this.pagesPerPageGroup = pagesPerPageGroup;
            return this;
        }

        public Builder currentPage(int currentPage) {
            this.currentPage = currentPage;
            return this;
        }

        public Builder link(String path) {
            this.ucb.path(path);
            return this;
        }

        public Builder query(String name, Object... values) {
            this.ucb.queryParam(name, values);
            return this;
        }

        public Builder param(String name) {
            this.param = name;
            return this;
        }

        public Paging build() {
            return new Paging(this.totalCount, this.entriesPerPage, this.currentPage, this.pagesPerPageGroup, this.ucb, this.param);
        }
    }
}