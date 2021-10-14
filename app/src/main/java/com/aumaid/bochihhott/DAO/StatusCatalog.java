package com.aumaid.bochihhott.DAO;

public class StatusCatalog {

    private String status_id;
    private String status_name;

    public StatusCatalog() {
    }

    public StatusCatalog(String status_id, String status_name) {
        this.status_id = status_id;
        this.status_name = status_name;
    }

    public String getStatus_id() {
        return status_id;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    @Override
    public String toString() {
        return "StatusCatalog{" +
                "status_id='" + status_id + '\'' +
                ", status_name='" + status_name + '\'' +
                '}';
    }
}
