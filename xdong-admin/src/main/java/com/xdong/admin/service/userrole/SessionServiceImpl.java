package com.xdong.admin.service.userrole;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xdong.model.dto.system.UserOnline;
import com.xdong.model.entity.userrole.SysUserDo;
import com.xdong.spi.admin.userrole.SessionService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 待完善
 *
 * @author bootdo
 */
@Service
public class SessionServiceImpl implements SessionService {

    private final SessionDAO sessionDAO;

    @Autowired
    public SessionServiceImpl(SessionDAO sessionDAO){
        this.sessionDAO = sessionDAO;
    }

    @Override
    public List<UserOnline> list() {
        List<UserOnline> list = new ArrayList<>();
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        for (Session session : sessions) {
            UserOnline UserOnline = new UserOnline();
            if (session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) == null) {
                continue;
            } else {
                SimplePrincipalCollection principalCollection = (SimplePrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                SysUserDo SysUserDo = (SysUserDo) principalCollection.getPrimaryPrincipal();
                UserOnline.setUsername(SysUserDo.getUsername());
            }
            UserOnline.setId((String) session.getId());
            UserOnline.setHost(session.getHost());
            UserOnline.setStartTimestamp(session.getStartTimestamp());
            UserOnline.setLastAccessTime(session.getLastAccessTime());
            UserOnline.setTimeout(session.getTimeout());
            list.add(UserOnline);
        }
        return list;
    }

    @Override
    public List<SysUserDo> listOnlineUser() {
        List<SysUserDo> list = new ArrayList<>();
        SysUserDo SysUserDo;
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        for (Session session : sessions) {
            SimplePrincipalCollection principalCollection = new SimplePrincipalCollection();
            if (session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) == null) {
                continue;
            } else {
                principalCollection = (SimplePrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                SysUserDo = (SysUserDo) principalCollection.getPrimaryPrincipal();
                list.add(SysUserDo);
            }
        }
        return list;
    }

    @Override
    public Collection<Session> sessionList() {
        return sessionDAO.getActiveSessions();
    }

    @Override
    public boolean forceLogout(String sessionId) {
        Session session = sessionDAO.readSession(sessionId);
        session.setTimeout(0);
        return true;
    }
}
