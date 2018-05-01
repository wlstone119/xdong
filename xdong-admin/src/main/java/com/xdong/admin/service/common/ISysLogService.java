package com.xdong.admin.service.common;

import com.baomidou.mybatisplus.service.IService;
import com.xdong.common.utils.PageUtils;
import com.xdong.common.utils.Query;
import com.xdong.dal.common.domain.PageDO;
import com.xdong.dal.common.domain.SysLog;

/**
 * <p>
 * 系统日志 服务类
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
public interface ISysLogService extends IService<SysLog> {

    PageUtils queryList(Query query);

    boolean remove(Long id);

    boolean batchRemove(Long[] ids);

}
