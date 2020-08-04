package com.huanfion.wms.common.contanst;

/**
 * @author huanfion
 * @version 1.0
 * @date 2019/8/30 10:09
 */
public interface PageConstant {
    /**
     * 页数
     */
    String PAGE_SIZE = "pageSize";
    /**
     * 分页大小
     */
    String PAGE_NUM = "pageNum";
    /**
     * 排序字段 ASC
     */
    String PAGE_ASCS = "ascs";
    /**
     * 排序字段 DESC
     */
    String PAGE_DESCS = "descs";
    /**
     * 查询总数
     */
    String SEARCH_COUNT = "searchCount";
    /**
     * 默认每页条目20,最大条目数100
     */
    int DEFAULT_LIMIT = 20;
    int MAX_LIMIT = 100;
}
