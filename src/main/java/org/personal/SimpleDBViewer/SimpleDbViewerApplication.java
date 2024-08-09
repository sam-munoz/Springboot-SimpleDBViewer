package org.personal.SimpleDBViewer;

import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import static org.hibernate.cfg.AvailableSettings.*;

import org.hibernate.cfg.Configuration;
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
		SessionFactory sf = new Configuration()
				.addAnnotatedClass(CPUListEntity.class)
				.setProperty(URL, "jdbc:h2:file:./src/main/data/h2db")
				.setProperty(USER, "")
				.setProperty(PASS, "")
				.setProperty("hibernate.agroal.maxSize", 2) 
				.setProperty(SHOW_SQL, true)
                .setProperty(FORMAT_SQL, true)
                .setProperty(HIGHLIGHT_SQL, true)
                .buildSessionFactory();
		
		sf.getSchemaManager().exportMappedObjects(true);
		return sf;
	}
	
	public static void createCPUListEntry(Session s, Long id, String cpuname, boolean closeSession) {
		s.beginTransaction();
		if(id < 0) {
			CPUListEntity c = new CPUListEntity();
			c.setName(cpuname);
			s.save(c);
		} else {
			s.save(new CPUListEntity(id, cpuname));
		}
		s.getTransaction().commit();
		if(closeSession) s.close();
	}
	
	public static void createCPUListEntry(Session s, String cpuname, boolean closeSession) {
		createCPUListEntry(s, -1L, cpuname, closeSession);
	}

	public static void createCPUListEntry(Session s, String cpuname) {
		createCPUListEntry(s, -1L, cpuname, true);
	}

	public static List<CPUListEntity> getAllCPUs(Session s, boolean closeSession) {
		List<CPUListEntity> rtnList = null;
		s.beginTransaction();
		rtnList = s.createSelectionQuery("from CPUListEntity", CPUListEntity.class).getResultList();
		s.getTransaction().commit();
		if(closeSession) s.close();
		return rtnList;
	}

	public static List<CPUListEntity> getAllCPUs(Session s) {
		return getAllCPUs(s, true);
	}
	
	public static void updateCPUListEntry(Session s, CPUListEntity cpu, boolean closeSession) {
		s.beginTransaction();
		s.update(cpu);
		s.getTransaction().commit();
		if(closeSession) s.close();
	}
	
	public static void updateCPUListEntry(Session s, CPUListEntity cpu) {
		updateCPUListEntry(s, cpu, true);
	}
	
	public static void deleteCPUListEntry(Session s, CPUListEntity cpu, boolean closeSession) {
		// my naive way of doing things
		s.beginTransaction();
		s.delete(cpu);
		s.getTransaction().commit();
		if(closeSession) s.close();

		// NOT NEEDED YET
		// get objects in the persistence context
		
		// delete that object
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
		
		SessionFactory sessionFactory = buildSessionFactory();
		Session s = sessionFactory.openSession();
		
		// create a new entry
		createCPUListEntry(s, "i7-11700KF", false);
		createCPUListEntry(s, "i3-8100", false);

		// list all cpus in the database
		List<CPUListEntity> l = getAllCPUs(s, false);
		System.out.println("LIST START");
		for(CPUListEntity c : l) System.out.println(c);
		System.out.println("LIST END");
		
		// update cpu entry
		if(!l.isEmpty()) {
			l.getFirst().setName("i7-10700F");
			updateCPUListEntry(s, l.getFirst(), false);
		}

		// list all cpus in the database
		l = getAllCPUs(s, false);
		System.out.println("LIST START");
		for(CPUListEntity c : l) System.out.println(c);
		System.out.println("LIST END");
		
		// empty database
		while(!l.isEmpty()) {
			deleteCPUListEntry(s, l.get(0), false);
			l = getAllCPUs(s, false);
		}

		// list all cpus in the database
		l = getAllCPUs(s);
		System.out.println("LIST START");
		for(CPUListEntity c : l) System.out.println(c);
		System.out.println("LIST END");
		
		// update an existing cpu entry
		
	}
}
