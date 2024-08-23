package org.personal.SimpleDBViewer.Controllers;

import java.util.List;
import org.personal.SimpleDBViewer.Domain.CPUListEntity;
import org.personal.SimpleDBViewer.Domain.CPURankingSummaryEntity;
import org.personal.SimpleDBViewer.Domain.UsersCPURankingEntity;
import org.personal.SimpleDBViewer.Domain.UsersEntity;
import org.personal.SimpleDBViewer.Services.GetTablesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class handles all view/GET requests to the data stored in the database tables
 */
//@Controller
@RestController
public class ViewTablesController {
    @Autowired
    private GetTablesService tablesService;

    @GetMapping("/")
    public String home() {
        return "Hello, world!";
    }

    /**
     * Handles GET requests at the URL /table/cpus
     * @return Returns JSON data of all the entries of the CPUListEntity table.
     */
    @GetMapping("/tables/cpus")
    @CrossOrigin(origins="http://localhost:5500")
    public List<CPUListEntity> viewCPUTable() {
        return this.tablesService.getCPUTable();
    }

    /**
     * Handles GET requests at the URL /table/users
     * Method has not been implemented
     * @return Returns JSON data of all the entries of the UsersEntity table.
     */
    @GetMapping("/tables/users")
    @CrossOrigin(origins="http://localhost:5500")
    public List<UsersEntity> viewUsersTable() {
        return this.tablesService.getUsersTable();
    }

    /**
     * Handles GET requests at the URL /table/rankings
     * Method has not been implemented
     * @return Returns JSON data of all the entries of the UsersCPURanking table.
     */
    @GetMapping("/tables/rankings")
    public List<UsersCPURankingEntity> viewRankingTable() {
        return null;
    }

    /**
     * Handles GET requests at the URL /table/cpus
     * Method has not been implemented
     * @return Returns JSON data of all the entries of the CPUListEntity table.
     */
    @GetMapping("/tables/summaries")
    public List<CPURankingSummaryEntity> viewSummaryTable() {
        return null;
    }
}
