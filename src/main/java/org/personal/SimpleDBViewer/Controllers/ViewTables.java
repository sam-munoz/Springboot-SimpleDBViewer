package org.personal.SimpleDBViewer.Controllers;

import jakarta.websocket.server.PathParam;
import org.personal.SimpleDBViewer.Services.GetTablesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewTables {
    @Autowired
    private GetTablesService tablesService;

    /**
     * Handles GET requests at the URL /table/{tableName} where tableName is on the table names in the database.
     * Method has not been implemented.
     * @return Returns JSON data of all the entries of the requested table.
     */
    @GetMapping("/tables/{tableName}")
    public List<Object> viewTable() {
        return null;
    }
}
