package com.xdong.admin.service.common;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xdong.dal.common.domain.SysDict;
import com.xdong.dal.userrole.domain.SysUser;

/**
 * <p>
 * 字典表 服务类
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
public interface ISysDictService extends IService<SysDict> {

    List<SysDict> listType();

    String getName(String type, String value);

    /**
     * 获取爱好列表
     * 
     * @return
     * @param userDO
     */
    List<SysDict> getHobbyList(SysUser userDO);

    /**
     * 获取性别列表
     * 
     * @return
     */
    List<SysDict> getSexList();

    /**
     * 根据type获取数据
     * 
     * @param map
     * @return
     */
    List<SysDict> listByType(String type);

}
