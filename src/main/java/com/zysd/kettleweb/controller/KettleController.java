/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zysd.kettleweb.controller;

import com.zysd.kettleweb.beans.BaseEnum;
import com.zysd.kettleweb.beans.RestResponse;
import com.zysd.kettleweb.kettle.KettleService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.tree.BaseElement;
import org.pentaho.di.core.RowMetaAndData;
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

    @PostMapping(value = "/tran")
    public RestResponse<List<Map<String,Object>>> trans(@RequestBody Map<String,String> map,
                                                        @RequestParam(value = "file") String file) throws Exception {
        if(map.containsKey("pageNum") && map.containsKey("pageSize")){
            Integer pageNum = Integer.valueOf(map.get("pageNum")) * Integer.valueOf(map.get("pageSize"));
            map.put("pageNum",pageNum.toString());
        }
        return RestResponse.success(kettleService.runKtr(file,map));
    }

}
