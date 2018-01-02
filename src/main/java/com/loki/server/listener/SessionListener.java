package com.loki.server.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import com.loki.server.utils.SessionContext;

public class SessionListener implements HttpSessionListener {
	private static final Logger logger = Logger.getLogger(SessionListener.class);
    private   SessionContext sessionContext=SessionContext.getInstance();

    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
    		HttpSession session=httpSessionEvent.getSession();
    		logger.debug("session建立,id:"+session.getId());
        	sessionContext.AddSession(session);
    }

    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
	    	HttpSession session=httpSessionEvent.getSession();
	    	logger.debug("session销毁,id:"+session.getId());
        sessionContext.DelSession(session);
    }
}
