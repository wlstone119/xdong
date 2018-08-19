package com.xdong.spi.admin.common;

import com.baomidou.mybatisplus.service.IService;
import com.xdong.common.utils.PageUtils;
import com.xdong.common.utils.Query;
import com.xdong.model.dto.data.PageDO;
import com.xdong.model.entity.common.SysLogDo;

/**
 * <p>
 * 系统日志 服务类
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
public interface ISysLogService extends IService<SysLogDo> {

    PageUtils queryList(Query query);

    boolean remove(Long id);

    boolean batchRemove(Long[] ids);

}
