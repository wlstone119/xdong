package com.xdong.spi.admin.common;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xdong.model.entity.common.SysDictDo;
import com.xdong.model.entity.userrole.SysUserDo;

/**
 * <p>
 * 字典表 服务类
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
public interface ISysDictService extends IService<SysDictDo> {

    List<SysDictDo> listType();

    String getName(String type, String value);

    /**
     * 获取爱好列表
     * 
     * @return
     * @param userDO
     */
    List<SysDictDo> getHobbyList(SysUserDo userDO);

    /**
     * 获取性别列表
     * 
     * @return
     */
    List<SysDictDo> getSexList();

    /**
     * 根据type获取数据
     * 
     * @param map
     * @return
     */
    List<SysDictDo> listByType(String type);

}
