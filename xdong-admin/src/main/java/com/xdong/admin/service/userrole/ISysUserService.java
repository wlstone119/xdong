package com.xdong.admin.service.userrole;

import java.util.Map;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.service.IService;
import com.xdong.common.vo.UserVO;
import com.xdong.dal.common.domain.Tree;
import com.xdong.dal.userrole.domain.SysDept;
import com.xdong.dal.userrole.domain.SysUser;
import com.xdong.dto.SysUserDto;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
public interface ISysUserService extends IService<SysUser> {

    SysUserDto get(Long id);

    boolean save(SysUserDto user);

    boolean update(SysUserDto user);

    boolean remove(Long userId);

    boolean batchremove(Long[] userIds);

    boolean exit(Map<String, Object> params);

    Set<String> listRoles(Long userId);

    boolean resetPwd(UserVO userVO, SysUser userDO) throws Exception;

    boolean adminResetPwd(UserVO userVO) throws Exception;

    Tree<SysDept> getTree();

    int getDeptUserNumber(Long deptId);

    Long[] listAllDept();

    /**
     * 更新个人图片
     * 
     * @param file 图片
     * @param avatar_data 裁剪信息
     * @param userId 用户ID
     * @throws Exception
     */
    Map<String, Object> updatePersonalImg(MultipartFile file, String avatar_data, Long userId) throws Exception;
}
