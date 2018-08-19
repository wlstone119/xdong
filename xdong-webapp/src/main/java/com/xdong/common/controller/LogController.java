package com.xdong.common.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.xdong.common.utils.PageUtils;
import com.xdong.common.utils.Query;
import com.xdong.common.utils.R;
import com.xdong.model.entity.common.SysLogDo;
import com.xdong.spi.admin.common.ISysLogService;

@RequestMapping("/common/log")
@Controller
public class LogController extends BaseController {

	private static final String prefix = "common/log";

	@Autowired
	ISysLogService logService;

	@GetMapping()
	String log() {
		return prefix + "/log";
	}

	@ResponseBody
	@GetMapping("/list")
	PageUtils list(@RequestParam Map<String, Object> params) {

		// 查询列表数据
		Query query = new Query(params);
		Page<SysLogDo> page = new Page<SysLogDo>(query.getPage(), query.getLimit());
		page.setDescs(Arrays.asList("gmt_create"));
		page.setCondition(convertConditionParam(params));
		Page<SysLogDo> result = logService.selectPage(page);
		PageUtils pageUtils = new PageUtils(result.getRecords(), result.getTotal());

		return pageUtils;
	}

	@ResponseBody
	@PostMapping("/remove")
	R remove(Long id) {
		if (logService.deleteById(id)) {
			return R.ok();
		}
		return R.error();
	}

	@ResponseBody
	@PostMapping("/batchRemove")
	R batchRemove(@RequestParam("ids[]") Long[] ids) {
		if (logService.deleteBatchIds(Arrays.asList(ids))) {
			return R.ok();
		}
		return R.error();
	}
}
