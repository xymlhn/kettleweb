package com.zysd.kettleweb.beans;

import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * 项目名称：CRM
 * 功能说明：TODO
 *
 * @author cartman
 * @createtime 2019/10/10 11:34 上午
 */
@Data
public class Page<T> {
    private List<T> records;
    private Long total;
}
