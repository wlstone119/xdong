package com.xdong.admin.service.common.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xdong.admin.service.common.ISysLogService;
import com.xdong.common.utils.PageUtils;
import com.xdong.common.utils.Query;
import com.xdong.dal.blog.domain.BlogContent;
import com.xdong.dal.common.dao.SysLogMapper;
import com.xdong.dal.common.domain.PageDO;
import com.xdong.dal.common.domain.SysLog;

import java.util.Arrays;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统日志 服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {

    @Override
    public PageUtils queryList(Query query) {

        EntityWrapper<SysLog> wrapper = new EntityWrapper<SysLog>();
        Page<SysLog> page = new Page<SysLog>(query.getPage(), query.getLimit());
        Page<SysLog> result = selectPage(page, wrapper);

        PageUtils pageUtils = new PageUtils(result.getRecords(), result.getTotal());
        return pageUtils;
    }

    @Override
    public boolean remove(Long id) {
        return deleteById(id);
    }

    @Override
    public boolean batchRemove(Long[] ids) {
        return deleteBatchIds(Arrays.asList(ids));
    }

}
