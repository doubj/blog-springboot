package com.guojunjie.springbootblog.controller.admin;

import com.guojunjie.springbootblog.common.Result;
import com.guojunjie.springbootblog.common.ResultGenerator;
import com.guojunjie.springbootblog.entity.Log;
import com.guojunjie.springbootblog.service.LogService;
import com.guojunjie.springbootblog.service.dto.BlogListQueryAdmin;
import com.guojunjie.springbootblog.service.dto.LogQueryCriteria;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Date： 2020/4/26 22:39
 * @author： guojunjie
 */
@RestController
@RequestMapping("/admin")
public class LogController {
    @Resource
    LogService logService;

    @PostMapping("/log/query")
    @ResponseBody
    public Result getBlogListAndCountByQuery(@RequestBody LogQueryCriteria query) {
        Map<String, Object> res = logService.getLogListAndCountByQuery(query);
        return ResultGenerator.genSuccessResult(res);
    }
    @GetMapping("/log/{id}")
    @ResponseBody
    public Result getBlogListAndCountByQuery(@PathVariable int id) {
        String res = logService.getLogById(id);
        return ResultGenerator.genSuccessResult(res);
    }

    @DeleteMapping("/log/{logType}")
    @ResponseBody
    public Result deleteLogByType(@PathVariable String logType){
        logService.deleteLogByType(logType);
        return ResultGenerator.genSuccessResult();
    }
}
