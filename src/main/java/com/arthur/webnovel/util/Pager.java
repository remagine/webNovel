package com.arthur.webnovel.util;

import org.hibernate.Criteria;

//페이징을 계산하는 class
public class Pager {

    private final int page;
    private final int entriesPerPage;
    private final int pagesPerPageGroup;

    public Pager(int page, int entriesPerPage) {
        this.page = page;
        this.entriesPerPage = entriesPerPage;
        this.pagesPerPageGroup = Const.PAGES_PER_PAGE_GROUP;
    }

    public Pager(int page, int entriesPerPage, int pagesPerPageGroup) {
        this.page = page;
        this.entriesPerPage = entriesPerPage;
        this.pagesPerPageGroup = pagesPerPageGroup;
    }

    public int getOffset() {
        return getOffset(getPage());
    }

    public int getOffset(int p) {
        return entriesPerPage * p;
    }

    public int getPage() {
        return page;

    }

    public int getEntriesPerPage() {
        return entriesPerPage;
    }

    public int getPagesPerPageGroup() {
        return pagesPerPageGroup;
    }

    public Criteria apply(Criteria q) {
        return q.setMaxResults(getEntriesPerPage())
                .setFirstResult(getOffset());
    }

    public Paging.Builder apply(Paging.Builder builder) {
        return builder.currentPage(getPage())
                .entriesPerPage(getEntriesPerPage())
                .pagesPerPageGroup(getPagesPerPageGroup());
    }

    public Paging.Builder paging() {
        return apply(Paging.builder());
    }
}
