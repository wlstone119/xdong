package com.xdong.spi.idol;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xdong.model.entity.idol.XdIdolHomeViewDetailDo;

/**
 * <p>
 * idol首页试图详情h维护表 服务类
 * </p>
 *
 * @author wanglei
 * @since 2018-09-24
 */
public interface IXdIdolHomeViewDetailService extends IService<XdIdolHomeViewDetailDo> {
    
    public List<XdIdolHomeViewDetailDo> getHomeViewList(Long homeId);
    
    public List<XdIdolHomeViewDetailDo> getHomeViewDetailByIdolId(Long idolId);

}
