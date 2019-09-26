/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zysd.kettleweb.controller;

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

    @PostMapping(value = "/tran")
    public RestResponse<List<Map<String,Object>>> trans(@RequestBody Map<String,String> map) throws Exception {
        if(map.containsKey("page") && map.containsKey("rows")){
            Integer pageNum = Integer.valueOf(map.get("page")) * Integer.valueOf(map.get("rows"));
            map.put("page",pageNum.toString());
        }
        return RestResponse.success(kettleService.runKtr(map.get("file"),map));
    }

}
