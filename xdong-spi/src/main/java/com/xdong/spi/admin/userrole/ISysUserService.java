package com.xdong.spi.admin.userrole;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.service.IService;
import com.xdong.common.vo.UserVO;
import com.xdong.model.dto.userrole.SysUserDto;
import com.xdong.model.entity.userrole.SysDeptDo;
import com.xdong.model.entity.userrole.SysUserDo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
public interface ISysUserService extends IService<SysUserDo> {

    SysUserDto get(Long id);

    boolean save(SysUserDto user);

    boolean update(SysUserDto user);

    boolean remove(Long userId);

    boolean batchremove(Long[] userIds);

    boolean exit(Map<String, Object> params);

    Set<String> listRoles(Long userId);

    boolean resetPwd(UserVO userVO, SysUserDo userDO) throws Exception;

    boolean adminResetPwd(UserVO userVO) throws Exception;

    Tree<SysDeptDo> getTree();

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

    List<SysUserDo> getSysUserListById(Long deptId);

    void reload();
}
