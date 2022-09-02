package com.nhnacademy.marketgg.client.paging;

import lombok.Getter;
import lombok.Setter;

/**
 * 페이지네이션을 구현하기 위한 클래스입니다.
 *
 * @author 조현진
 */
@Getter
@Setter
public class Pagination {

    private int pageSize = 9; // 한 페이지당 데이터 개수
    private int blockSize = 10; // 뷰에 보여줄 페이지 이동가능 개수(블럭)
    private int page; // 현재 페이지
    private int block;
    private int totalListCnt; // 전체 데이터 개수인 듯 하나 미사용중
    private int totalPageCnt;
    private int totalBlockCnt;
    private int startPage;
    private int endPage;
    private int startIndex;
    private int prevBlock;
    private int nextBlock;

    public Pagination(int totalPage, int page) {
        this.totalPageCnt = totalPage;
        this.page = page;
        this.totalBlockCnt = (int) Math.ceil(this.totalPageCnt * 1.0 / this.blockSize);
        this.block = (int) Math.ceil((page * 1.0) / this.blockSize);
        this.startPage = (this.block - 1) * this.blockSize + 1;
        this.endPage = this.startPage + this.blockSize - 1;

        if (this.endPage > this.totalPageCnt) {
            this.endPage = this.totalPageCnt;
        }

        this.prevBlock = (this.block * this.blockSize) - this.blockSize;

        if (this.prevBlock < 1) {
            this.prevBlock = 1;
        }

        this.nextBlock = (this.block * this.blockSize) + 1;

        if (this.nextBlock > this.totalPageCnt) {
            this.nextBlock = this.totalPageCnt;
        }

        this.startIndex = (page - 1) * this.pageSize;
    }

}
