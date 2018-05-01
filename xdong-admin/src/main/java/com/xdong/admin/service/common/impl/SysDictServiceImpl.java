package com.xdong.admin.service.common.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xdong.admin.service.common.ISysDictService;
import com.xdong.dal.common.dao.SysDictMapper;
import com.xdong.dal.common.domain.SysDict;
import com.xdong.dal.userrole.domain.SysUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements ISysDictService {

    @Override
    public List<SysDict> listType() {
        return selectByMap(new HashMap<String, Object>());
    }

    @Override
    public String getName(String type, String value) {
        Map<String, Object> param = new HashMap<String, Object>(16);
        param.put("type", type);
        param.put("value", value);

        List<SysDict> dictList = selectByMap(param);

        if (CollectionUtils.isNotEmpty(dictList)) {
            return dictList.get(0).getName();
        }
        return null;
    }

    @Override
    public List<SysDict> getHobbyList(SysUser userDO) {
        Map<String, Object> param = new HashMap<>(16);
        param.put("type", "hobby");
        List<SysDict> hobbyList = selectByMap(param);

        if (StringUtils.isNotEmpty(userDO.getHobby())) {
            String userHobbys[] = userDO.getHobby().split(";");
            for (String userHobby : userHobbys) {
                for (SysDict hobby : hobbyList) {
                    if (!Objects.equals(userHobby, hobby.getId().toString())) {
                        continue;
                    }
                    hobby.setRemarks("true");
                    break;
                }
            }
        }
        return hobbyList;
    }

    @Override
    public List<SysDict> getSexList() {
        Map<String, Object> param = new HashMap<>(16);
        param.put("type", "sex");
        return selectByMap(param);
    }

    @Override
    public List<SysDict> listByType(String type) {
        Map<String, Object> param = new HashMap<>(16);
        param.put("type", type);
        return selectByMap(param);
    }

}
