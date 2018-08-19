package com.xdong.spi.admin.userrole;

import java.util.Collection;
import java.util.List;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;

import com.xdong.model.dto.system.UserOnline;
import com.xdong.model.entity.userrole.SysUserDo;

@Service
public interface SessionService {

    List<UserOnline> list();

    List<SysUserDo> listOnlineUser();

    Collection<Session> sessionList();

    boolean forceLogout(String sessionId);
}
