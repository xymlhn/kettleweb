package com.zysd.kettleweb.beans;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class BaseVo {
    private List<Map<String,Object>> list;
    //当前页
    private int pageNum;
    //每页的数量
    private int pageSize;
    //总记录数
    private long total;
}
