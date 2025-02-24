// package org.personal.SimpleDBViewer;

import org.personal.SimpleDBViewer.Domain.CPUListEntity;
import org.personal.SimpleDBViewer.Domain.CPURankingSummaryEntity;
import org.personal.SimpleDBViewer.Domain.UsersCPURankingEntity;
import org.personal.SimpleDBViewer.Domain.UsersEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.personal.SimpleDBViewer.domain")
public class EntityBeanConfig {
	
	@Bean({"CPUListEntity"})
	public CPUListEntity createCPUListEntity() {
		return new CPUListEntity();
	}
	
	@Bean({"CPURankingSummaryEntity"})
	public CPURankingSummaryEntity createCPURankingSummaryEntity() {
		return new CPURankingSummaryEntity();
	}

	@Bean({"UsersCPURankingEntity"})
	public UsersCPURankingEntity createUsersCPURankingEntity() {
		return new UsersCPURankingEntity();
	}
	
	@Bean({"UsersEntity"})
	public UsersEntity createUsersEntity() {
		return new UsersEntity();
	}
}
