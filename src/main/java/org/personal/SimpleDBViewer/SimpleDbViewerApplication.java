package org.personal.SimpleDBViewer;

import org.personal.SimpleDBViwer.CRUDRepository.CPUListEntityCRUDRepository;
import org.personal.SimpleDBViwer.CRUDRepository.UsersCPURankingCRUDRepository;
import org.personal.SimpleDBViwer.CRUDRepository.UsersEntityCRUDRepository;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import static org.hibernate.cfg.AvailableSettings.*;

import org.hibernate.cfg.Configuration;
import org.personal.SimpleDBViewer.domain.CPUListEntity;
import org.personal.SimpleDBViewer.domain.UsersCPURankingEntity;
import org.personal.SimpleDBViewer.domain.UsersEntity;
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
import java.util.ArrayList;
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
				.addAnnotatedClass(UsersEntity.class)
				.addAnnotatedClass(UsersCPURankingEntity.class)
				.buildSessionFactory();
		return sf;
	}
	
	public static void printTable(SessionFactory s, int tableNum) {
		if(tableNum == 0) {
			List<CPUListEntity> l = CPUListEntityCRUDRepository.getAllCPUs(s);
			System.out.println("START PRINTING CPULIST");
			for(Object obj : l) System.out.println(obj);
			System.out.println("END PRINTING");
		} else if(tableNum == 1) {
			List<UsersEntity> l = UsersEntityCRUDRepository.getAllUsersEntities(s);
			System.out.println("START PRINTING USERS");
			for(Object obj : l) System.out.println(obj);
			System.out.println("END PRINTING");
		} else if(tableNum == 2) {
			List<UsersCPURankingEntity> l = UsersCPURankingCRUDRepository.getAllUsersEntities(s);
			System.out.println("START PRINTING RANKINGS");
			for(Object obj : l) System.out.println(obj);
			System.out.println("END PRINTING");
		}
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
		
		// create SessionFactory
		SessionFactory sessionFactory = buildSessionFactory();
		
		// create cpu entities
		List<CPUListEntity> cpulist = new ArrayList<CPUListEntity>();
		cpulist.add(CPUListEntityCRUDRepository.createCPUListEntity(sessionFactory, "i7-11700KF"));
		cpulist.add(CPUListEntityCRUDRepository.createCPUListEntity(sessionFactory, "i3-8100"));
		
		// create user entities
		List<UsersEntity> userslist = new ArrayList<UsersEntity>();
		userslist.add(UsersEntityCRUDRepository.createUsersEntity(sessionFactory, "User 1"));
		userslist.add(UsersEntityCRUDRepository.createUsersEntity(sessionFactory, "User 2"));
		
		// print tables
		printTable(sessionFactory, 0);
		printTable(sessionFactory, 1);
		
		// create a ranking
		List<UsersCPURankingEntity> rankinglist = new ArrayList<UsersCPURankingEntity>();
		rankinglist.add(UsersCPURankingCRUDRepository.createRankingEntity(sessionFactory, userslist.getFirst(), cpulist.getFirst(), 5));

		// print tables
		printTable(sessionFactory, 2);
		
		// delete ranking
		UsersCPURankingCRUDRepository.deleteRankingEntity(sessionFactory, rankinglist.getFirst());

		// print tables
		printTable(sessionFactory, 2);
	}
}