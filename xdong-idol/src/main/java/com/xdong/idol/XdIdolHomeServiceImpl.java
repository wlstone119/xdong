package com.xdong.idol;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xdong.dal.idol.XdIdolHomeDoMapper;
import com.xdong.model.entity.idol.XdIdolHomeDo;
import com.xdong.spi.idol.IXdIdolHomeService;

import org.springframework.stereotype.Service;

/**
 * <p>
 * idol首页试图展示表 服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2018-09-24
 */
@Service
public class XdIdolHomeServiceImpl extends ServiceImpl<XdIdolHomeDoMapper, XdIdolHomeDo> implements IXdIdolHomeService {

    @Override
    public XdIdolHomeDo getIdolHome(Long idolId) {
        EntityWrapper<XdIdolHomeDo> entityWrapper = new EntityWrapper<XdIdolHomeDo>(); 
        entityWrapper.eq("idol_id", idolId);

        return selectOne(entityWrapper);
    }

}
