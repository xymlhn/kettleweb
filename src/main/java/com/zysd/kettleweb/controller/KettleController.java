/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zysd.kettleweb.controller;

import com.zysd.kettleweb.beans.Page;
import com.zysd.kettleweb.beans.RestResponse;
import com.zysd.kettleweb.kettle.KettleService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author cartmanxie
 */
@RestController
@RequestMapping(path = "/kettle")
public class KettleController {

    @Autowired
    KettleService kettleService;

    @PostMapping(value = "/page")
    public RestResponse page(@RequestBody Map<String,String> map) throws Exception {
        if(map.containsKey("page") && map.containsKey("rows")){
            int pageNum = (Integer.parseInt(map.get("page")) - 1) * Integer.parseInt(map.get("rows"));
            map.put("page", Integer.toString(pageNum));
        }
        List<Map<String,Object>> records =  kettleService.runKtr(map.get("file"),map);
        List<Map<String,Object>> counts =  kettleService.runKtr("count_" + map.get("file"),map);

        Page page = new Page();
        page.setRecords(records);
        page.setTotal(Long.parseLong(String.valueOf(counts.get(0).get("num"))));

        return RestResponse.success(page);
    }

    @PostMapping(value = "/tran")
    public RestResponse trans(@RequestBody Map<String,String> map) throws Exception {

        return RestResponse.success(kettleService.runKtr(map.get("file"),map));
    }

}
