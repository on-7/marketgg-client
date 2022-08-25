package com.nhnacademy.marketgg.client.paging;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 페이지네이션을 구현하기 위한 클래스입니다.
 *
 * @author 조현진
 */
@Getter
@Setter
public class Pagination {

    private int pageSize = 10;
    private int blockSize = 10;
    private int page = 1;
    private int block = 1;
    private int totalListCnt;
    private int totalPageCnt;
    private int totalBlockCnt;
    private int startPage = 1;
    private int endPage = 1;
    private int startIndex = 0;
    private int prevBlock;
    private int nextBlock;

    public Pagination(int totalPage, int page) {
        this.totalPageCnt = totalPage;
        this.page = page;

        setTotalBlockCnt((int) Math.ceil(totalPageCnt * 1.0 / blockSize));

        setBlock((int) Math.ceil((page * 1.0) / blockSize));

        setStartPage((block - 1) * blockSize + 1);

        setEndPage(startPage + blockSize - 1);

        if (endPage > totalPageCnt) {
            this.endPage = totalPageCnt;
        }

        setPrevBlock((block * blockSize) - blockSize);

        if (prevBlock < 1) {
            this.prevBlock = 1;
        }

        setNextBlock((block * blockSize) + 1);

        if (nextBlock > totalPageCnt) {
            nextBlock = totalPageCnt;
        }

        setStartIndex((page - 1) * pageSize);
    }

}
