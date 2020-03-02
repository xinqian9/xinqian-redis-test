package com.xinqian.redis.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bw.utils.RandomUtil;
import com.xinqian.redis.domain.User;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:redis.xml")
@SuppressWarnings("all")
public class TestRedis {
	@Autowired
	RedisTemplate redisTemplate;
	/**
	 * 
	 * @Title: testJDK 
	 * @Description: 使用jdk序列化方式
	 * @return: void
	 */
	@Test
	public void testJDK() {
		String genders[]= {"男","女"};
		String name="";
		String gender="";
		String email="";
		String prefix="";
		String suffix="";
		String suffixs[]= {"@qq.com","@163.com","@sian.com","@gmail.com","@sohu.com","@hotmail.com","@foxmail.com"};
		int count=0;
		ArrayList<User> list=new ArrayList<User>();
		for (int i = 1; i <= 100000; i++) {
			String tel="13";
			//随机生成3个汉字，作为姓名
			name = RandomUtil.getRandomChineseString(3);
			//随机生成男|女
			gender=genders[RandomUtil.getRandomNum(0, 1)];
			//随机生成13+九位数字的电话号码
			for (int j = 0; j < 9; j++) {
				tel+=(int)(Math.random()*10);
			}
			//随机生成邮箱以3-20个随机字母 + @qq.com  | @163.com | @sian.com | @gmail.com | @sohu.com | @hotmail.com | @foxmail.com
			prefix=RandomUtil.getRandomChars(3, 20);
			suffix=suffixs[RandomUtil.getRandomNum(0, suffixs.length-1)];
			email=prefix+suffix;
			//随机生成日期
			User user = new User(i, name, gender, tel, email, "2010-1-1");
			list.add(user);
			count++;
		}
		long start = System.currentTimeMillis();
		redisTemplate.opsForList().leftPushAll("user_list",list.toArray());
		long end = System.currentTimeMillis();
		System.out.println("序列化的方式为：jdk");
		Long size = redisTemplate.opsForList().size("user_list");
		System.out.println("共存入："+size+"个对象");
		System.out.println("耗时："+(end-start)+"ms");
	}
	
	/**
	 * 
	 * @Title: testJDK 
	 * @Description: 使用jaon序列化方式
	 * @return: void
	 */
	@Test
	public void testJson() {
		String genders[]= {"男","女"};
		String name="";
		String gender="";
		String email="";
		String prefix="";
		String suffix="";
		String suffixs[]= {"@qq.com","@163.com","@sian.com","@gmail.com","@sohu.com","@hotmail.com","@foxmail.com"};
		int count=0;
		ArrayList<User> list=new ArrayList<User>();
		for (int i = 1; i <= 100000; i++) {
			String tel="13";
			//随机生成3个汉字，作为姓名
			name = RandomUtil.getRandomChineseString(3);
			//随机生成男|女
			gender=genders[RandomUtil.getRandomNum(0, 1)];
			//随机生成13+九位数字的电话号码
			for (int j = 0; j < 9; j++) {
				tel+=(int)(Math.random()*10);
			}
			//随机生成邮箱以3-20个随机字母 + @qq.com  | @163.com | @sian.com | @gmail.com | @sohu.com | @hotmail.com | @foxmail.com
			prefix=RandomUtil.getRandomChars(3, 20);
			suffix=suffixs[RandomUtil.getRandomNum(0, suffixs.length-1)];
			email=prefix+suffix;
			//随机生成日期
			User user = new User(i, name, gender, tel, email, "2010-1-1");
			list.add(user);
			count++;
		}
		long start = System.currentTimeMillis();
		redisTemplate.opsForList().leftPushAll("user_list",list.toArray());
		long end = System.currentTimeMillis();
		System.out.println("序列化的方式为：json");
		Long size = redisTemplate.opsForList().size("user_list");
		System.out.println("共存入："+size+"个对象");
		System.out.println("耗时："+(end-start)+"ms");
	}
	
	/**
	 * 
	 * @Title: testJDK 
	 * @Description: 使用hash序列化方式
	 * @return: void
	 */
	@Test
	public void testHash() {
		String genders[]= {"男","女"};
		String name="";
		String gender="";
		String email="";
		String prefix="";
		String suffix="";
		String suffixs[]= {"@qq.com","@163.com","@sian.com","@gmail.com","@sohu.com","@hotmail.com","@foxmail.com"};
		int count=0;
		//ArrayList<User> list=new ArrayList<User>();
		HashMap<String,String> map = new HashMap<String, String>();
		for (int i = 1; i <= 100000; i++) {
			String tel="13";
			//随机生成3个汉字，作为姓名
			name = RandomUtil.getRandomChineseString(3);
			//随机生成男|女
			gender=genders[RandomUtil.getRandomNum(0, 1)];
			//随机生成13+九位数字的电话号码
			for (int j = 0; j < 9; j++) {
				tel+=(int)(Math.random()*10);
			}
			//随机生成邮箱以3-20个随机字母 + @qq.com  | @163.com | @sian.com | @gmail.com | @sohu.com | @hotmail.com | @foxmail.com
			prefix=RandomUtil.getRandomChars(3, 20);
			suffix=suffixs[RandomUtil.getRandomNum(0, suffixs.length-1)];
			email=prefix+suffix;
			//随机生成日期
			User user = new User(i, name, gender, tel, email, "2010-1-1");
			map.put(i+"",user.toString());
			count++;
		}
		long start = System.currentTimeMillis();
		//redisTemplate.opsForList().leftPushAll("user_list",list.toArray());
		redisTemplate.opsForHash().putAll("user_hash",map);
		long end = System.currentTimeMillis();
		System.out.println("序列化的方式为：hash");
		Long size = redisTemplate.opsForHash().size("user_hash");
		System.out.println("共存入："+size+"个对象");
		System.out.println("耗时："+(end-start)+"ms");
	}
}
