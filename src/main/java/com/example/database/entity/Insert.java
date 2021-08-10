package com.example.database.entity;

import java.util.List;
public class Insert {
    private int id;
    private int newRepositoryId;
    private String repositoryId;
    private String selectTable;
    private String dataModelName;
    private List<Det> tableList;

    public int getNewRepositoryId() {
        return newRepositoryId;
    }

    public void setNewRepositoryId(int newRepositoryId) {
        this.newRepositoryId = newRepositoryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(String repositoryId) {
        this.repositoryId = repositoryId;
    }

    public String getSelectTable() {
        return selectTable;
    }

    public void setSelectTable(String selectTable) {
        this.selectTable = selectTable;
    }

    public String getDataModelName() {
        return dataModelName;
    }

    public void setDataModelName(String dataModelName) {
        this.dataModelName = dataModelName;
    }

    public List<Det> getTableList() {
        return tableList;
    }

    public void setTableList(List<Det> tableList) {
        this.tableList = tableList;
    }
}
