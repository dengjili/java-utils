package priv.dengjl.bean;

import java.util.Date;
import java.util.Map;

import priv.dengjl.bean.utils.PropertyUtils;

public class PersonTest {
	public static void main(String[] args) throws Exception {
		Person person = new Person();
		person.setName("张三");
		person.setAge(11);
		person.setDate(new Date());
		
		Map<String, Object> map = PropertyUtils.bean2Map(person);
		System.out.println(map);
		
		Person map2Bean = PropertyUtils.map2Bean(Person.class, map);
		System.out.println(map2Bean);
		
		Person map2Bean2 = PropertyUtils.map2Bean2(Person.class, map);
		System.out.println(map2Bean2);
	}
}
