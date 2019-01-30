package com.xdong.idol;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xdong.dal.idol.XdIdolDoMapper;
import com.xdong.model.entity.idol.XdIdolDo;
import com.xdong.spi.idol.IXdIdolService;

import org.springframework.stereotype.Service;

/**
 * <p>
 * idol表 服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2018-09-24
 */
@Service
public class XdIdolServiceImpl extends ServiceImpl<XdIdolDoMapper, XdIdolDo> implements IXdIdolService {

}
