package com.loki.server.listener;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.loki.server.utils.SessionContext;

public class SessionListener implements HttpSessionListener {
    private   SessionContext sessionContext=SessionContext.getInstance();

    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
    		HttpSession session=httpSessionEvent.getSession();
    		System.out.println("session建立,id:"+session.getId());
        	sessionContext.AddSession(session);
    }

    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
	    	HttpSession session=httpSessionEvent.getSession();
	    	System.out.println("session销毁,id:"+session.getId());
        sessionContext.DelSession(session);
    }
}
