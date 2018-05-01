package com.xdong.admin.service.notify;

import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xdong.common.utils.PageUtils;
import com.xdong.dal.notify.domain.OaNotifyDto;
import com.xdong.dal.notify.domain.OaNotify;

/**
 * <p>
 * 通知通告 服务类
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
public interface IOaNotifyService extends IService<OaNotify> {

    boolean save(OaNotifyDto notify);

    boolean remove(Long id);

    boolean batchRemove(Long[] ids);

    OaNotify get(Long id);

    PageUtils list(Page<OaNotify> page);

    PageUtils selfList(Map<String, Object> map);

}
