package ac.cn.iscas.nfs.demo;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import ac.cn.iscas.nfs.bean.Person;

public class Demo {

	public static void main(String[] args) {
		
		Configuration config = new Configuration().configure();
		ServiceRegistry sr = new ServiceRegistryBuilder().applySettings(config.getProperties()).build();
		SessionFactory sf = config.buildSessionFactory(sr);
		
		Session ss = sf.openSession();
		Person p = (Person) ss.load(Person.class,1);
		System.out.println("get--->"+p);
		
		ss.close();
		
	}

	private static void get() {
		Configuration config = new Configuration().configure();
		ServiceRegistry sr = new ServiceRegistryBuilder().applySettings(config.getProperties()).build();
		SessionFactory sf = config.buildSessionFactory(sr);
		
		Session ss = sf.openSession();
		Person p = (Person) ss.get(Person.class,1);
		System.out.println("get--->"+p);
		
		ss.close();
	}

	private static void persist() {
		Configuration config = new Configuration().configure();
		SessionFactory factory = config.buildSessionFactory();		
		Session ss = factory.openSession();
		Transaction tx = ss.beginTransaction();
		String username ="temp";
		String passwd ="123456"
		
		Person person = new Person();
		person.setName(username);
		person.setPassword(passwd);
		person.setBirthday(new Date());
		
		ss.save(person);
		
		tx.commit();
		ss.close();
	}

}
