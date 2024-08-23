package org.personal.SimpleDBViewer.Services;

import java.util.List;
import org.personal.SimpleDBViewer.CRUDRepository.CPUListEntityCRUDRepository;
import org.personal.SimpleDBViewer.CRUDRepository.CPURankingSummaryCRUDRepository;
import org.personal.SimpleDBViewer.CRUDRepository.UsersCPURankingCRUDRepository;
import org.personal.SimpleDBViewer.CRUDRepository.UsersEntityCRUDRepository;
import org.personal.SimpleDBViewer.Domain.CPUListEntity;
import org.personal.SimpleDBViewer.Domain.CPURankingSummaryEntity;
import org.personal.SimpleDBViewer.Domain.UsersCPURankingEntity;
import org.personal.SimpleDBViewer.Domain.UsersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetTablesService {
    @Autowired
    CPUListEntityCRUDRepository cpuRepo;

    @Autowired
    UsersEntityCRUDRepository userRepo;

    @Autowired
    UsersCPURankingCRUDRepository rankRepo;

    @Autowired
    CPURankingSummaryCRUDRepository summaryRepo;

    /**
     * Returns all the CPU entities stored in the database.
     * @return Lists all CPUs entities stores the database.
     */
    public List<CPUListEntity> getCPUTable() {
        return this.cpuRepo.getAllCPUs();
    }

    /**
     * Returns all the Users entities stored in the database.
     * @return Lists all Users entities stores the database.
     */
    public List<UsersEntity> getUsersTable() {
        return this.userRepo.getAllUsers();
    }

    /**
     * Returns all the UsersCPURanking entities stored in the database.
     * Method has not been implemented
     * @return Lists all UsersCPURanking entities stores the database.
     */
    public List<UsersCPURankingEntity> getRankingTable() {
        return null;
    }

    /**
     * Returns all the CPURankingSummary entities stored in the database.
     * Method has not been implemented
     * @return Lists all CPURankingSummary entities stores the database.
     */
    public List<CPURankingSummaryEntity> getSummaryTable() {
        return null;
    }
}
