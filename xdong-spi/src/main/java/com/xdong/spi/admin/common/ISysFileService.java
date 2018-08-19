package com.xdong.spi.admin.common;

import com.baomidou.mybatisplus.service.IService;
import com.xdong.model.entity.common.SysFileDo;

/**
 * <p>
 * 文件上传 服务类
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
public interface ISysFileService extends IService<SysFileDo> {

    boolean batchRemove(Long[] ids);

    /**
     * 判断一个文件是否存在
     * 
     * @param url FileDO中存的路径
     * @return
     */
    Boolean isExist(String url);
}
