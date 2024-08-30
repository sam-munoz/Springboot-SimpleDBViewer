package org.personal.SimpleDBViewer;

import org.hibernate.SessionFactory;

import org.hibernate.cfg.Configuration;
import org.personal.SimpleDBViewer.CRUDRepository.CPUListEntityCRUDRepository;
import org.personal.SimpleDBViewer.CRUDRepository.CPURankingSummaryCRUDRepository;
import org.personal.SimpleDBViewer.CRUDRepository.UsersCPURankingCRUDRepository;
import org.personal.SimpleDBViewer.CRUDRepository.UsersEntityCRUDRepository;
import org.personal.SimpleDBViewer.Domain.CPUListEntity;
import org.personal.SimpleDBViewer.Domain.CPURankingSummaryEntity;
import org.personal.SimpleDBViewer.Domain.UsersCPURankingEntity;
import org.personal.SimpleDBViewer.Domain.UsersEntity;
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

	public static void main(String[] args) {
		// Start up SpringBoot application
		ConfigurableApplicationContext container = SpringApplication.run(SimpleDbViewerApplication.class, args);

		// initialize cpu table
		CPUListEntityCRUDRepository cpuRepo = container.getBean(CPUListEntityCRUDRepository.class);
		CPUListEntity c0 = cpuRepo.createCPU(new CPUListEntity("i7-11700KF"));
		CPUListEntity c1 = cpuRepo.createCPU(new CPUListEntity("i3-8100"));

		// initialize user table
		UsersEntityCRUDRepository userRepo = container.getBean(UsersEntityCRUDRepository.class);
		UsersEntity u0 = userRepo.createUser(new UsersEntity("Sam"));
		UsersEntity u1 = userRepo.createUser(new UsersEntity("Ricardo", "best"));
		UsersEntity u2 = userRepo.createUser(new UsersEntity("Israel"));

		// initialize the summary table
		CPURankingSummaryCRUDRepository summaryRepo = container.getBean(CPURankingSummaryCRUDRepository.class);
		CPURankingSummaryEntity s0 = summaryRepo.createSummary(new CPURankingSummaryEntity(c0, 12, 2L));

		// initialize the ranking table
		UsersCPURankingCRUDRepository rankingRepo = container.getBean(UsersCPURankingCRUDRepository.class);
		UsersCPURankingEntity r0 = rankingRepo.createRanking(new UsersCPURankingEntity(c0, u0, 9));

	}
}