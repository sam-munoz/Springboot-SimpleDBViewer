package org.personal.SimpleDBViewer;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.personal.SimpleDBViewer.domain.CPUListEntity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/*
 * Packages only needed for the for the executeDBCommands method
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

@SpringBootApplication
public class SimpleDbViewerApplication {
	/*
	 * This code was written based on the following Youtube video
	 * 	- https://www.youtube.com/watch?v=KgXq2UBNEhA
	 * We are only JDBC to make a connection to the database and run SQL queries to the database
	 * This is very similar to the connection to databases in other languages such as JavaScript and PHP
	 */
	public static void executeDBCommands() {
		try(Connection connection = DriverManager.getConnection("jdbc:h2:./src/main/data/h2db")) {
			// create valid connection
			System.out.println("connection.isValid(0) = " + connection.isValid(0));
			
			// insert data into the database
			PreparedStatement ps = connection.prepareStatement("insert into cpulist(cpulist_id, cpulist_name) values (?, ?)");
			String cpuName = "i7-11700K";
			ps.setLong(1, 1000);
			ps.setString(2, cpuName);
			int insertCount = ps.executeUpdate();
			System.out.println("insertCount = " + insertCount);
			
			// view data inserted
			ps = connection.prepareStatement("select * from cpulist where cpulist_name = ?");
			ps.setString(1, cpuName);
			ResultSet results = ps.executeQuery();
			while(results.next()) {
				System.out.println(results.getLong("cpulist_id") + ", " + results.getString("cpulist_name"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static SessionFactory buildSessionFactory() {
		final StandardServiceRegistry registry = 
				new StandardServiceRegistryBuilder().build();
		
		try {
			return new MetadataSources(registry).addAnnotatedClass(CPUListEntity.class).buildMetadata().buildSessionFactory();
		} catch(Exception e) {
			StandardServiceRegistryBuilder.destroy(registry);
		}
		return null;
	}

	public static List<CPUListEntity> getAllCPUs(Session s, boolean closeSession) {
		Transaction t = s.beginTransaction();
		try {
			List<CPUListEntity> cpus = s.createQuery("SELECT id, name from CPUListEntity", CPUListEntity.class).list();
			t.commit();
			return cpus;
		} catch(Exception e) {
			t.rollback();
			e.printStackTrace();
		} finally {
			if(closeSession && s.isOpen()) {
				s.close();
			}
		}
		return null;
	}

	public static List<CPUListEntity> getAllCPUs(Session s) {
		return getAllCPUs(s, true);
	} 
	
	public static void createCPUListEntry(Session s, Long id, String cpuname, boolean closeSession) {
		Transaction t = s.beginTransaction();
		try {
			CPUListEntity cpu = new CPUListEntity();
			cpu.setId(id);
			cpu.setName(cpuname);
			s.persist(cpu);
			t.commit();
		} catch(Exception e) {
			t.rollback();
			e.printStackTrace();
		} finally {
			if(closeSession && s.isOpen()) {
				s.close();
			}
		}
	}

	public static void createCPUListEntry(Session s, Long id, String cpuname) {
		createCPUListEntry(s, id, cpuname, true);
	}
	
	public static void deleteCPUListEntry(Session s, CPUListEntity cpu, boolean closeSession) {
		Transaction t = s.beginTransaction();
		try {
			s.remove(cpu);
			t.commit();
		} catch(Exception e) {
			t.rollback();
			e.printStackTrace();
		} finally {
			if(closeSession && s.isOpen()) {
				s.close();
			}
		}
	}

	public static void deleteCPUListEntry(Session s, CPUListEntity cpu) {
		deleteCPUListEntry(s, cpu, true);
	}
	
	
	
//	@Bean
//	public CommandLineRunner run(DBRepository repo) {
//		return (args) {
//			
//		};
//	}
	
	public static void main(String[] args) {
//		ConfigurableApplicationContext container = SpringApplication.run(SimpleDbViewerApplication.class, args);
//		
//		for(String beanName : container.getBeanDefinitionNames()) {
//			System.out.println(beanName);
//		}
		
//		executeDBCommands();
		
		SessionFactory sf = buildSessionFactory();
		Session session = sf.openSession();
		createCPUListEntry(session, 1000L, "i7 11700KF", false);
		List<CPUListEntity> list = getAllCPUs(session, false);
		System.out.println("START LIST");
		for(CPUListEntity c : list) System.out.println(c);
		System.out.println("END LIST");
		if(!list.isEmpty()) {
			deleteCPUListEntry(session, list.get(0));
		}
		list = getAllCPUs(session);
		System.out.println("START LIST");
		for(CPUListEntity c : list) System.out.println(c);
		System.out.println("END LIST");
	}
}
