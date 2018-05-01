package com.xdong.admin.service.userrole;

import java.util.Collection;
import java.util.List;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;

import com.xdong.dal.userrole.domain.SysUser;
import com.xdong.dal.userrole.domain.UserOnline;

@Service
public interface SessionService {

    List<UserOnline> list();

    List<SysUser> listOnlineUser();

    Collection<Session> sessionList();

    boolean forceLogout(String sessionId);
}
