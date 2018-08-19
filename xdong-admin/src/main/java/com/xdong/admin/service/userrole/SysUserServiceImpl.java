package com.xdong.admin.service.userrole;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xdong.admin.service.activiti.util.BuildTree;
import com.xdong.common.config.XdongConfig;
import com.xdong.common.utils.FileType;
import com.xdong.common.utils.FileUtil;
import com.xdong.common.utils.ImageUtils;
import com.xdong.common.utils.MD5Utils;
import com.xdong.common.vo.UserVO;
import com.xdong.dal.userrole.SysUserMapper;
import com.xdong.model.dto.userrole.SysUserDto;
import com.xdong.model.entity.common.SysFileDo;
import com.xdong.model.entity.userrole.SysDeptDo;
import com.xdong.model.entity.userrole.SysUserDo;
import com.xdong.model.entity.userrole.SysUserRoleDo;
import com.xdong.spi.admin.common.ISysFileService;
import com.xdong.spi.admin.userrole.ISysDeptService;
import com.xdong.spi.admin.userrole.ISysUserRoleService;
import com.xdong.spi.admin.userrole.ISysUserService;
import com.xdong.spi.admin.userrole.Tree;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.imageio.ImageIO;

import org.activiti.editor.language.json.converter.util.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserDo> implements ISysUserService {

	@Autowired
	private ISysUserRoleService sysUserRoleService;

	@Autowired
	private ISysDeptService sysDeptService;

	@Autowired
	private XdongConfig bootdoConfig;

	@Autowired
	private ISysFileService sysFileService;

	@Override
	public SysUserDto get(Long id) {
		List<Long> roleIds = sysUserRoleService.listRoleId(id);

		SysUserDto userDto = new SysUserDto();
		SysUserDo user = selectById(id);
		BeanUtils.copyProperties(user, userDto);

		userDto.setDeptName(sysDeptService.selectById(user.getDeptId()).getName());
		userDto.setRoleIds(roleIds);

		return userDto;
	}

	@Transactional
	@Override
	public boolean save(SysUserDto userDto) {
		SysUserDo user = new SysUserDo();
		BeanUtils.copyProperties(userDto, user);
		boolean result = insert(user);

		saveUserRole(user.getUserId(), userDto.getRoleIds());

		return result;
	}

	@Override
	public boolean update(SysUserDto userDto) {
		SysUserDo user = new SysUserDo();
		BeanUtils.copyProperties(userDto, user);

		boolean r = updateById(user);
		saveUserRole(user.getUserId(), userDto.getRoleIds());
		return r;
	}

	private void saveUserRole(Long userId, List<Long> roles) {

		if (CollectionUtils.isNotEmpty(roles)) {
			sysUserRoleService.removeByUserId(userId);
			List<SysUserRoleDo> list = new ArrayList<>();
			for (Long roleId : roles) {
				SysUserRoleDo ur = new SysUserRoleDo();
				ur.setUserId(userId);
				ur.setRoleId(roleId);
				list.add(ur);
			}
			if (list.size() > 0) {
				sysUserRoleService.insertBatch(list);
			}
		}
	}

	@Override
	public boolean remove(Long userId) {
		sysUserRoleService.removeByUserId(userId);
		return deleteById(userId);
	}

	@Override
	public boolean exit(Map<String, Object> params) {
		return selectByMap(params).size() > 0;
	}

	@Override
	public Set<String> listRoles(Long userId) {
		return null;
	}

	@Override
	public boolean resetPwd(UserVO userVO, SysUserDo userDO) throws Exception {
		if (Objects.equals(userVO.getUser().getUserId(), userDO.getUserId())) {
			if (Objects.equals(MD5Utils.encrypt(userDO.getUsername(), userVO.getPwdOld()), userDO.getPassword())) {
				userDO.setPassword(MD5Utils.encrypt(userDO.getUsername(), userVO.getPwdNew()));
				return updateById(userDO);
			} else {
				throw new Exception("输入的旧密码有误！");
			}
		} else {
			throw new Exception("你修改的不是你登录的账号！");
		}
	}

	@Override
	public boolean adminResetPwd(UserVO userVO) throws Exception {
		SysUserDo userDO = get(userVO.getUser().getUserId());
		if ("admin".equals(userDO.getUsername())) {
			throw new Exception("超级管理员的账号不允许直接重置！");
		}
		userDO.setPassword(MD5Utils.encrypt(userDO.getUsername(), userVO.getPwdNew()));
		return updateById(userDO);
	}

	@Override
	public int getDeptUserNumber(Long deptId) {
		EntityWrapper<SysUserDo> wrapper = new EntityWrapper<SysUserDo>();
		wrapper.eq("dept_id", deptId);
		List<SysUserDo> userList = selectList(wrapper);
		return CollectionUtils.isNotEmpty(userList) ? userList.size() : 0;
	}

	@Override
	public Tree<SysDeptDo> getTree() {
		List<Tree<SysDeptDo>> trees = new ArrayList<Tree<SysDeptDo>>();
		List<SysDeptDo> depts = sysDeptService.selectByMap(new HashMap<String, Object>(16));
		Long[] pDepts = sysDeptService.listParentDept();
		Long[] uDepts = listAllDept();
		Long[] allDepts = (Long[]) ArrayUtils.addAll(pDepts, uDepts);
		for (SysDeptDo dept : depts) {
			if (!ArrayUtils.contains(allDepts, dept.getDeptId())) {
				continue;
			}
			Tree<SysDeptDo> tree = new Tree<SysDeptDo>();
			tree.setId(dept.getDeptId().toString());
			tree.setParentId(dept.getParentId().toString());
			tree.setText(dept.getName());
			Map<String, Object> state = new HashMap<>(16);
			state.put("opened", true);
			state.put("mType", "dept");
			tree.setState(state);
			trees.add(tree);
		}
		List<SysUserDo> users = selectByMap(new HashMap<String, Object>(16));
		for (SysUserDo user : users) {
			Tree<SysDeptDo> tree = new Tree<SysDeptDo>();
			tree.setId(user.getUserId().toString());
			tree.setParentId(user.getDeptId().toString());
			tree.setText(user.getName());
			Map<String, Object> state = new HashMap<>(16);
			state.put("opened", true);
			state.put("mType", "user");
			tree.setState(state);
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		Tree<SysDeptDo> t = BuildTree.build(trees);
		return t;
	}

	@Override
	public Map<String, Object> updatePersonalImg(MultipartFile file, String avatar_data, Long userId) throws Exception {
		String fileName = file.getOriginalFilename();
		fileName = FileUtil.renameToUUID(fileName);
		SysFileDo sysFile = new SysFileDo(FileType.fileType(fileName), "/files/" + fileName, new Date());
		// 获取图片后缀
		String prefix = fileName.substring((fileName.lastIndexOf(".") + 1));
		String[] str = avatar_data.split(",");
		// 获取截取的x坐标
		int x = (int) Math.floor(Double.parseDouble(str[0].split(":")[1]));
		// 获取截取的y坐标
		int y = (int) Math.floor(Double.parseDouble(str[1].split(":")[1]));
		// 获取截取的高度
		int h = (int) Math.floor(Double.parseDouble(str[2].split(":")[1]));
		// 获取截取的宽度
		int w = (int) Math.floor(Double.parseDouble(str[3].split(":")[1]));
		// 获取旋转的角度
		int r = Integer.parseInt(str[4].split(":")[1].replaceAll("}", ""));
		try {
			BufferedImage cutImage = ImageUtils.cutImage(file, x, y, w, h, prefix);
			BufferedImage rotateImage = ImageUtils.rotateImage(cutImage, r);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			boolean flag = ImageIO.write(rotateImage, prefix, out);
			// 转换后存入数据库
			byte[] b = out.toByteArray();
			FileUtil.uploadFile(b, bootdoConfig.getUploadPath(), fileName);
		} catch (Exception e) {
			throw new Exception("图片裁剪错误！！");
		}
		Map<String, Object> result = new HashMap<>();
		if (sysFileService.insert(sysFile)) {
			SysUserDo userDO = new SysUserDo();
			userDO.setUserId(userId);
			userDO.setPicId(sysFile.getId());
			if (updateById(userDO)) {
				result.put("url", sysFile.getUrl());
			}
		}
		return result;
	}

	@Override
	public Long[] listAllDept() {
		Set<Long> deptIds = new HashSet<Long>();
		List<SysUserDo> userList = selectByMap(new HashMap<String, Object>());
		for (SysUserDo user : userList) {
			deptIds.add(user.getDeptId());
		}
		return deptIds.toArray(new Long[deptIds.size()]);
	}

	@Transactional
	@Override
	public boolean batchremove(Long[] userIds) {
		deleteBatchIds(Arrays.asList(userIds));
		sysUserRoleService.removeBatchByUserId(userIds);
		return true;
	}

	@Cacheable(value = "deptUserCache")
	@Override
	public List<SysUserDo> getSysUserListById(Long deptId) {
		System.out.println("select data from db.....");
		EntityWrapper<SysUserDo> wrapper = new EntityWrapper<SysUserDo>();
		wrapper.eq("dept_id", deptId);
		return selectList(wrapper);
	}

	@CacheEvict(value = "deptUserCache", allEntries = true) // 清空 accountCache 缓存
	public void reload() {
	}

}
