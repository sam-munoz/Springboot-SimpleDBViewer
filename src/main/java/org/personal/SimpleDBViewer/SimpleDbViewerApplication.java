package org.personal.SimpleDBViewer;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;

import org.hibernate.cfg.Configuration;
import org.personal.SimpleDBViewer.CRUDRepository.CPUListEntityCRUDRepository;
import org.personal.SimpleDBViewer.CRUDRepository.CPURankingSummaryCRUDRepository;
import org.personal.SimpleDBViewer.CRUDRepository.UsersCPURankingCRUDRepository;
import org.personal.SimpleDBViewer.CRUDRepository.UsersEntityCRUDRepository;
import org.personal.SimpleDBViewer.Domain.CPUListEntity;
import org.personal.SimpleDBViewer.Domain.CPURankingSummaryEntity;
import org.personal.SimpleDBViewer.Domain.UsersCPURankingEntity;
import org.personal.SimpleDBViewer.Domain.UsersCPURankingId;
import org.personal.SimpleDBViewer.Domain.UsersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;

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

	@Autowired
	private EntityManagerFactory entityManager;

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
	
	protected static SessionFactory buildSessionFactory() {
		SessionFactory sf = new Configuration()
				.addAnnotatedClass(CPUListEntity.class)
				.addAnnotatedClass(UsersEntity.class)
				.addAnnotatedClass(UsersCPURankingEntity.class)
				.addAnnotatedClass(CPURankingSummaryEntity.class)
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
		} else if(tableNum == 3) {
			List<CPURankingSummaryEntity> l = CPURankingSummaryCRUDRepository.getAllCPUSummaryEntities(s);
			System.out.println("START PRINTING SUMMARIES");
			for(Object obj : l) System.out.println(obj);
			System.out.println("END PRINTING");
		}
	}
	
	public static UsersCPURankingEntity createRanking(SessionFactory s, UsersCPURankingEntity rank) {
		// get cpu ranking summary for the input rank if it exists
		// probably should be a one liner if I create selection getter methods
		List<CPURankingSummaryEntity> summaries = CPURankingSummaryCRUDRepository.getAllCPUSummaryEntities(s);
		int index = 0;
		if(summaries.isEmpty()) {
			index = summaries.size();
		} else {
			while(!rank.getId().getCpu().equals(summaries.get(index).getCpuId())) {
				index++;
			}
		}
		// if an element found in the list
		CPURankingSummaryEntity currSummary = null;
		if(index < summaries.size()) {
			currSummary = summaries.get(index);
		}
		
		// add new ranking to the database
		UsersCPURankingCRUDRepository.createRankingEntity(s, rank);
		
		// update the summary if it exists or create a new summary
		if(currSummary != null) {
			currSummary.setCount(currSummary.getCount() + 1);
			currSummary.setRankSum(currSummary.getRankSum() + rank.getRanking());
			CPURankingSummaryCRUDRepository.updateCPUSummaryEntity(s, currSummary);
		} else {
			CPURankingSummaryCRUDRepository.createCPUSummaryEntity(s, rank.getId().getCpu(), rank.getRanking());
		}
		return rank;
	}
	
	public static UsersCPURankingEntity createRanking(SessionFactory s, CPUListEntity cpu, UsersEntity user, Integer ranking) {
		UsersCPURankingEntity rank = new UsersCPURankingEntity(new UsersCPURankingId(cpu, user), ranking);
		return createRanking(s, rank);
	}

//	@Bean
//	public CommandLineRunner run(DBRepository repo) {
//		return (args) {
//			
//		};
//	}

@RequestMapping("/")
String home() {
	return "Hello, world!";
}
	
	public static void main(String[] args) {
		ConfigurableApplicationContext container = SpringApplication.run(SimpleDbViewerApplication.class, args);

		CPUListEntityCRUDRepository repo = container.getBean(CPUListEntityCRUDRepository.class);
		repo.createCPUListEntity("Ryzen 5 5600X");

//		for(String beanName : container.getBeanDefinitionNames()) {
//			System.out.println(beanName);
//		}

//		executeDBCommands();
	}
}