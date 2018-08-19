package com.xdong.spi.admin.notify;

import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xdong.common.utils.PageUtils;
import com.xdong.model.dto.notify.OaNotifyDto;
import com.xdong.model.entity.notify.OaNotifyDo;

/**
 * <p>
 * 通知通告 服务类
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
public interface IOaNotifyService extends IService<OaNotifyDo> {

    boolean save(OaNotifyDto notify);

    boolean remove(Long id);

    boolean batchRemove(Long[] ids);

    OaNotifyDo get(Long id);

    PageUtils list(Page<OaNotifyDo> page);

    PageUtils selfList(Map<String, Object> map);

}
