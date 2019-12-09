package com.songyubing.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.songyubing.common.util.RandomUtil;
import com.songyubing.common.util.UserUtils;
import com.songyubing.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-redis.xml")
public class RedisTest {
	//注入redis
	@Resource
	private RedisTemplate<String, User> redisTemplate;

	// 测试jdk
	@Test
	public void testJDK() {
		// 生成对象
		List<User> list = new ArrayList<User>();
		for (int i = 1; i <= 50000; i++) {
			// 随机姓名
			String name = UserUtils.getName();
			// 随即性别
			String sex = UserUtils.getSex();
			// 随机手机号
			String phone = "13" + RandomUtil.randomNumber(9);
			// 邮箱
			String email = UserUtils.getMail();
			// 生日
			String birthday = UserUtils.getBirthday();
			User u = new User(i, name, sex, phone, email, birthday);
			// 存储到list中
			list.add(u);
		}
		// 开始时间
		long start = System.currentTimeMillis();
		// 存入到redis中
		redisTemplate.opsForList().leftPushAll("user-jdk", list);
		// 结束时间
		long end = System.currentTimeMillis();
		System.out.println("系列化方式：jdk");
		System.out.println("保存数量：50000");
		System.out.println("所耗时间：" + (end - start));

	}

	// json
	@Test
	public void testjson() {
		// 生成对象
		List<User> list = new ArrayList<User>();
		for (int i = 1; i <= 50000; i++) {
			// 随机姓名
			String name = UserUtils.getName();
			// 随即性别
			String sex = UserUtils.getSex();
			// 随机手机号
			String phone = "13" + RandomUtil.randomNumber(9);
			// 邮箱
			String email = UserUtils.getMail();
			// 生日
			String birthday = UserUtils.getBirthday();
			User u = new User(i, name, sex, phone, email, birthday);
			// 存储到list中
			list.add(u);
		}
		// 开始时间
		long start = System.currentTimeMillis();
		// 存入到redis中
		redisTemplate.opsForList().leftPushAll("user-json", list);
		// 结束时间
		long end = System.currentTimeMillis();
		System.out.println("系列化方式：json");
		System.out.println("保存数量：50000");
		System.out.println("所耗时间：" + (end - start));
	}

	// hash
	@Test
	public void testhash() {
		// 生成对象
		Map<String, User> map = new HashMap<String, User>();
		for (int i = 1; i <= 50000; i++) {
			// 随机姓名
			String name = UserUtils.getName();
			// 随即性别
			String sex = UserUtils.getSex();
			// 随机手机号
			String phone = "13" + RandomUtil.randomNumber(9);
			// 邮箱
			String email = UserUtils.getMail();
			// 生日
			String birthday = UserUtils.getBirthday();
			User u = new User(i, name, sex, phone, email, birthday);
			map.put(i+"", u);
		}
		// 开始时间
		long start = System.currentTimeMillis();
		//以hash类型保存到redis中
		redisTemplate.opsForHash().putAll("user-hash", map);
		// 结束时间
		long end = System.currentTimeMillis();
		System.out.println("系列化方式：string");
		System.out.println("保存数量：50000");
		System.out.println("所耗时间：" + (end - start));
	}
}
