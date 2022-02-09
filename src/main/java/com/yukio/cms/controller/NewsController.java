package com.yukio.cms.controller;


import com.yukio.cms.entity.News;
import com.yukio.cms.service.INewsService;
import com.yukio.common.model.R;
import com.yukio.common.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 自媒体图文内容信息表 前端控制器
 * </p>
 *
 * @author yukio
 * @since 2020-06-18
 */
@Controller
@RequestMapping("/cms/news")
@Slf4j
public class NewsController {

    @Autowired
    private INewsService iNewsService;

    @Autowired
	private RedisUtil redisUtil;

    //必须先登录，否则会报获取不到get方法
    @GetMapping("/{id}")
    @ResponseBody
    public R get(@PathVariable Integer id) {
        return R.ok("请求成功", iNewsService.getById(id));
    }

    @PostMapping("/save")
    @ResponseBody
    public R save(@RequestBody News news) {
        return R.ok("请求成功", iNewsService.saveOrUpdate(news));
    }


    @GetMapping("/redis/{redisKey}")
    @ResponseBody
    public R getKey(@PathVariable String redisKey) {
        return R.ok("请求redis成功",redisUtil.get(redisKey));
    }

    @GetMapping("/setredistime/{redisKey}")
    @ResponseBody
    public R setKey(@PathVariable String redisKey) {
        return R.ok("设置redis成功",redisUtil.expire(redisKey, 180));
    }


//
//	@GetMapping("redis")
//	public Object getRedis(){
//		redisUtil.set("r","aaaaaa");
//		return redisUtil.get("r");
//	}
}
