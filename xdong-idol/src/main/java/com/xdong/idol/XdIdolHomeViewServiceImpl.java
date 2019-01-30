package com.xdong.idol;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xdong.dal.idol.XdIdolHomeViewDoMapper;
import com.xdong.model.entity.idol.XdIdolHomeViewDo;
import com.xdong.spi.idol.IXdIdolHomeViewService;

import org.springframework.stereotype.Service;

/**
 * <p>
 * idol首页试图维护表 服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2018-09-24
 */
@Service
public class XdIdolHomeViewServiceImpl extends ServiceImpl<XdIdolHomeViewDoMapper, XdIdolHomeViewDo> implements IXdIdolHomeViewService {

}
