package com.xdong.controller.blog;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.plugins.Page;
import com.xdong.common.config.Constant;
import com.xdong.common.controller.BaseController;
import com.xdong.common.utils.PageUtils;
import com.xdong.common.utils.Query;
import com.xdong.common.utils.R;
import com.xdong.model.entity.blog.BlogContentDo;
import com.xdong.spi.admin.blog.IBlogContentService;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * 文章内容
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-09-09 10:03:34
 */
@Controller
@RequestMapping("/blog/bContent")
public class ContentController extends BaseController {

	@Autowired
	IBlogContentService bContentService;

	@GetMapping()
	@RequiresPermissions("blog:bContent:bContent")
	String bContent() {
		return "blog/bContent/bContent";
	}

	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("blog:bContent:bContent")
	public PageUtils list(@RequestParam Map<String, Object> params) {

		// 查询列表数据
		Query query = new Query(params);
		Page<BlogContentDo> page = new Page<BlogContentDo>(query.getPage(), query.getLimit());
		Page<BlogContentDo> result = bContentService.selectPage(page.setCondition(convertConditionParam(params)));
		PageUtils pageUtils = new PageUtils(result.getRecords(), result.getTotal());

		return pageUtils;
	}

	@GetMapping("/add")
	@RequiresPermissions("blog:bContent:add")
	String add() {
		return "blog/bContent/add";
	}

	@GetMapping("/edit/{cid}")
	@RequiresPermissions("blog:bContent:edit")
	String edit(@PathVariable("cid") Long cid, Model model) {
		BlogContentDo bContentDO = bContentService.selectById(cid);
		model.addAttribute("bContent", bContentDO);
		return "blog/bContent/edit";
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@RequiresPermissions("blog:bContent:add")
	@PostMapping("/save")
	public R save(BlogContentDo bContent) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		if (bContent.getAllowComment() == null) {
			bContent.setAllowComment(0);
		}
		if (bContent.getAllowFeed() == null) {
			bContent.setAllowFeed(0);
		}
		if (null == bContent.getType()) {
			bContent.setType("article");
		}
		bContent.setGtmCreate(new Date());
		bContent.setGtmModified(new Date());
		boolean count;
		if (bContent.getCid() == null || "".equals(bContent.getCid())) {
			count = bContentService.insert(bContent);
		} else {
			count = bContentService.updateById(bContent);
		}
		if (count) {
			return R.ok().put("cid", bContent.getCid());
		}
		return R.error();
	}

	/**
	 * 修改
	 */
	@RequiresPermissions("blog:bContent:edit")
	@ResponseBody
	@RequestMapping("/update")
	public R update(BlogContentDo bContent) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		bContent.setGtmCreate(new Date());
		bContentService.updateById(bContent);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequiresPermissions("blog:bContent:remove")
	@PostMapping("/remove")
	@ResponseBody
	public R remove(Long id) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		if (bContentService.deleteById(id)) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 删除
	 */
	@RequiresPermissions("blog:bContent:batchRemove")
	@PostMapping("/batchRemove")
	@ResponseBody
	public R remove(@RequestParam("ids[]") Long[] cids) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		bContentService.deleteBatchIds(Arrays.asList(cids));
		return R.ok();
	}
}
