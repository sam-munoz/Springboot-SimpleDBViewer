async function fetchData(url, tableName) {
    let response = await fetch(url);
    if(response.ok) {
        let json = await response.json();
        populateTableWithData(json, tableName);
    }
}

function generateRequestData(event) {
    baseurl = "http://localhost:8080/tables/"
    curr_select = event.target[event.target.selectedIndex].innerText.toLowerCase()
    if(curr_select === "cpus") {
        return [baseurl + "cpus", "CPUListEnttiy"];
    } else if(curr_select === "users") {
        return [baseurl + "users", "UsersEntity"];
    } else if(curr_select === "rankings") {
        return [baseurl + "rankings", "UsersCPURankingEntity"];
    } else if(curr_select === "summaries") {
        return [baseurl + "summaries", "CPURankingSummaryEntity"];
    }
}

function populateTableWithData(globalData, tableName) {
    // terminate program if no data is provided
    if(globalData == null) {
        console.log("table population failed");
        return;
    }

    // create root of table
    let tableRoot = document.createElement("table");
    tableRoot.id = "db-table";

    // create the header row
    let tableHeaderRow = document.createElement("tr");
    let dataKeys = Object.keys(globalData[0]);
    for(let i=0; i<dataKeys.length; i++) {
        let tableHeaderCell = document.createElement("th");
        tableHeaderCell.innerText = dataKeys[i].toUpperCase();
        tableHeaderRow.appendChild(tableHeaderCell);
    }
    tableRoot.appendChild(tableHeaderRow);

    // create the remaining rows
    for(let i=0; i<globalData.length; i++) {
        let tableDataRow = document.createElement("tr");
        for(let j=0; j<dataKeys.length; j++) {
            let tableDataCell = document.createElement("td");
            if(globalData[i][dataKeys[j]]) {
                tableDataCell.innerText = globalData[i][dataKeys[j]];
            } else {
                tableDataCell.innerText = "N/A";
            }
            tableDataRow.appendChild(tableDataCell);
        }
        tableRoot.appendChild(tableDataRow);
    }

    // get div.root
    let rootDiv = document.getElementsByClassName("root")[0];

    // remove the old table if it exists
    let oldTable = document.getElementById("db-table")
    if(oldTable !== null) {
        rootDiv.removeChild(oldTable);
    }


    // add the table to the DOM tree
    rootDiv.appendChild(tableRoot);

    // update the table name
    if(tableName !== null) {
        document.getElementById("table-name").innerText = tableName; 
    }
}

// add event handlers
document.getElementById("select-db-table").addEventListener("change", (e) => {
    // get data for fetching data
    requestData = generateRequestData(e);
    console.log(requestData);

    // fetch and update page
    fetchData(requestData[0], requestData[1]);
});