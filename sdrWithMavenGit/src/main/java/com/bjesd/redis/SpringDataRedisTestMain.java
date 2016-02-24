package com.bjesd.redis;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SpringDataRedisTestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		User user = new User();
		user.setName("mingxingyu");
		user.setAge(18);
		user.setCreated(new Date());
		SpringRedisUtil.save("me", user);
		System.out.println(SpringRedisUtil.get("me", User.class));
		*/
		User u = SpringRedisUtil.get("me", User.class);
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(u
				.getCreated()));
	}
}