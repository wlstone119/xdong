package com.xdong.spi.idol;

import com.baomidou.mybatisplus.service.IService;
import com.xdong.model.entity.idol.XdIdolHomeDo;

/**
 * <p>
 * idol首页试图展示表 服务类
 * </p>
 *
 * @author wanglei
 * @since 2018-09-24
 */
public interface IXdIdolHomeService extends IService<XdIdolHomeDo> {
    
    public XdIdolHomeDo getIdolHome(Long idolId);

}
