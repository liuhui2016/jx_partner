package com.game.smvc.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.game.smvc.entity.JxUser;
import com.game.smvc.entity.Test;

public class SessionUtils {

	public static JxUser getSessionUser(HttpServletRequest request) {
	    JxUser user = null;
		HttpSession session = request.getSession();
		if (session != null) {
			Object attributeTest = session.getAttribute("user");
			if ((attributeTest instanceof JxUser)) {
				user = (JxUser) attributeTest;
			}
		}
		return user;
	}

	/**
	 * 获取session为test的值
	 */
	public static Test getSessionTest(HttpServletRequest request) {
		Test test = null;
		HttpSession session = request.getSession();
		if (session != null) {
			Object attributeTest = session.getAttribute("test");
			if (attributeTest instanceof Test) {
				test = (Test) attributeTest;
			}
		}
		return test;
	}

	public static void main(String[] args) {
		Object d = null;
		if (d instanceof Test) {
			System.out.println("yes");
		}
		System.out.println("finish");
	}
}
