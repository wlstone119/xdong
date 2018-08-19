package com.xdong.admin.service.common;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xdong.common.utils.PageUtils;
import com.xdong.common.utils.Query;
import com.xdong.dal.common.SysLogMapper;
import com.xdong.model.entity.common.SysLogDo;
import com.xdong.spi.admin.common.ISysLogService;

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
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLogDo> implements ISysLogService {

    @Override
    public PageUtils queryList(Query query) {

        EntityWrapper<SysLogDo> wrapper = new EntityWrapper<SysLogDo>();
        Page<SysLogDo> page = new Page<SysLogDo>(query.getPage(), query.getLimit());
        Page<SysLogDo> result = selectPage(page, wrapper);

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
