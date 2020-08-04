package com.huanfion.wms.common.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huanfion.wms.common.contanst.PageConstant;
import com.huanfion.wms.common.model.AjaxResult;
import com.huanfion.wms.common.utils.ServletUtils;
import com.huanfion.wms.common.utils.TypeUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * web 通用层数据处理
 */
@Slf4j
public class BaseController {

    /**
     * 获取分页对象
     *
     * @return
     */
    protected <T> Page<T> getPage() {
        return getPage(false);
    }

    /**
     * 获取分页对象
     *
     * @param openSort
     * @return
     */
    protected <T> Page<T> getPage(boolean openSort) {
        int index = 1;
        // 页数
        Integer cursor = TypeUtils.castToInt(ServletUtils.getParameterToInt(PageConstant.PAGE_SIZE), index);
        // 分页大小
        Integer limit = TypeUtils.castToInt(ServletUtils.getParameterToInt(PageConstant.PAGE_NUM), PageConstant.DEFAULT_LIMIT);
        // 是否查询分页
        Boolean searchCount = TypeUtils.castToBoolean(ServletUtils.getParameter(PageConstant.SEARCH_COUNT), true);
        limit = limit > PageConstant.MAX_LIMIT ? PageConstant.MAX_LIMIT : limit;
        Page<T> page = new Page<>(cursor, limit, searchCount);
//        if (openSort) {
//            page.setAsc(getParameterSafeValues(PageConstant.PAGE_ASCS));
//            page.setDesc(getParameterSafeValues(PageConstant.PAGE_DESCS));
//        }
        return page;
    }
    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected AjaxResult toAjax(int rows)
    {
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 响应返回结果
     *
     * @param success 影响行数
     * @return 操作结果
     */
    protected AjaxResult toAjax(boolean success)
    {
        return success ? AjaxResult.success() : AjaxResult.error();
    }
}

