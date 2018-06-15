package com.xdong.controller.blog;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.plugins.Page;
import com.xdong.admin.service.blog.IBlogContentService;
import com.xdong.common.controller.BaseController;
import com.xdong.common.utils.DateUtils;
import com.xdong.common.utils.PageUtils;
import com.xdong.common.utils.Query;
import com.xdong.dal.blog.domain.BlogContent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/blog")
@Controller
public class BlogController extends BaseController {

    @Autowired
    IBlogContentService bContentService;

    @GetMapping()
    String blog(@RequestParam Map<String, Object> params, Model model) {
        PageUtils pageUtils = opentList(params);
        model.addAttribute("result", pageUtils);
        return "blog/index/main";
    }

    @ResponseBody
    @GetMapping("/open/list")
    public PageUtils opentList(@RequestParam Map<String, Object> params) {

        // 查询列表数据
        Query query = new Query(params);
        Page<BlogContent> page = new Page<BlogContent>(query.getPage(), query.getLimit());
        Page<BlogContent> result = bContentService.selectPage(page.setCondition(convertConditionParam(params)));
        PageUtils pageUtils = new PageUtils(result.getRecords(), result.getTotal());

        return pageUtils;
    }

    @GetMapping("/open/post/{cid}")
    String post(@PathVariable("cid") Long cid, Model model) {
        BlogContent bContentDO = bContentService.selectById(cid);
        model.addAttribute("bContent", bContentDO);
        model.addAttribute("gtmModified", DateUtils.format(bContentDO.getGtmModified()));
        return "blog/index/post";
    }

    @GetMapping("/open/page/{categories}")
    String about(@PathVariable("categories") String categories, Model model) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("categories", categories);
        BlogContent bContentDO = null;
        List<BlogContent> resultList = bContentService.selectByMap(map);
        if (CollectionUtils.isNotEmpty(resultList)) {
            bContentDO = resultList.get(0);
        }
        model.addAttribute("bContent", bContentDO);
        return "blog/index/post";
    }
}
