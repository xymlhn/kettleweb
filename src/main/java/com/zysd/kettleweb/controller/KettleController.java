/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zysd.kettleweb.controller;

import com.zysd.kettleweb.kettle.KettleService;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.pentaho.di.core.RowMetaAndData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author cartmanxie
 */
@RestController
@RequestMapping(path = "/kettle")
public class KettleController {

    @Autowired
    KettleService etlExecutor;

    @GetMapping(value = "/job")
    public ResponseEntity executeEtl(@RequestParam(value = "job")String name) {
        try {
            etlExecutor.runKjb(name, null,null);
        } catch (Exception ex) {
            Logger.getLogger(KettleController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/tran")
    public List<RowMetaAndData> trans(@RequestParam(value = "tran") String name) throws Exception {

        List<RowMetaAndData> metaAndDataList = etlExecutor.runKtr(name,null);
        return metaAndDataList;
    }

}
