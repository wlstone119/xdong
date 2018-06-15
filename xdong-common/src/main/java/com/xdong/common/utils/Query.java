package com.xdong.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查询参数
 */
public class Query extends LinkedHashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	// 页数
	private int page;
	// 偏移量
	private int offset;
	// 每页条数
	private int limit;

	public Query(Map<String, Object> params) {
		this.putAll(params);
		if (params.get("offset") == null)
			params.put("offset", 0);
		if (params.get("limit") == null)
			params.put("limit", 10);

		// 分页参数
		this.offset = Integer.parseInt(params.get("offset").toString());
		this.limit = Integer.parseInt(params.get("limit").toString());
		this.put("offset", offset);
		this.put("page", offset / limit + 1);
		this.put("limit", limit);
		this.page = Integer.parseInt(this.get("page").toString());
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.put("offset", offset);
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

}
