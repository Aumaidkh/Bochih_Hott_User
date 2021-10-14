package com.aumaid.bochihhott.DAO;

public class OrderStatus {

    private String order_status_id;
    private String placed_order_id;
    private String status_catalog_id;
    private String timestamp;

    public OrderStatus() {
    }

    public OrderStatus(String order_status_id, String placed_order_id, String status_catalog_id, String timestamp) {
        this.order_status_id = order_status_id;
        this.placed_order_id = placed_order_id;
        this.status_catalog_id = status_catalog_id;
        this.timestamp = timestamp;
    }

    public String getOrder_status_id() {
        return order_status_id;
    }

    public void setOrder_status_id(String order_status_id) {
        this.order_status_id = order_status_id;
    }

    public String getPlaced_order_id() {
        return placed_order_id;
    }

    public void setPlaced_order_id(String placed_order_id) {
        this.placed_order_id = placed_order_id;
    }

    public String getStatus_catalog_id() {
        return status_catalog_id;
    }

    public void setStatus_catalog_id(String status_catalog_id) {
        this.status_catalog_id = status_catalog_id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "OrderStatus{" +
                "order_status_id='" + order_status_id + '\'' +
                ", placed_order_id='" + placed_order_id + '\'' +
                ", status_catalog_id='" + status_catalog_id + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
