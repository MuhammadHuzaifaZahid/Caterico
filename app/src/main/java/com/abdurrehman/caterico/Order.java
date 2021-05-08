package com.abdurrehman.caterico;

public class Order {
    String  Order_Id,Order_Title,Order_Date,Order_Time,Order_Description,Budget,Status,Added_By;

    public Order() {
    }

    public Order(String order_Id, String order_Title, String order_Date, String order_Time, String order_Description, String budget, String status, String added_By) {
        Order_Id = order_Id;
        Order_Title = order_Title;
        Order_Date = order_Date;
        Order_Time = order_Time;
        Order_Description = order_Description;
        Budget = budget;
        Status = status;
        Added_By = added_By;
    }

    public String getOrder_Id() {
        return Order_Id;
    }

    public void setOrder_Id(String order_Id) {
        Order_Id = order_Id;
    }

    public String getOrder_Title() {
        return Order_Title;
    }

    public void setOrder_Title(String order_Title) {
        Order_Title = order_Title;
    }

    public String getOrder_Date() {
        return Order_Date;
    }

    public void setOrder_Date(String order_Date) {
        Order_Date = order_Date;
    }

    public String getOrder_Time() {
        return Order_Time;
    }

    public void setOrder_Time(String order_Time) {
        Order_Time = order_Time;
    }

    public String getOrder_Description() {
        return Order_Description;
    }

    public void setOrder_Description(String order_Description) {
        Order_Description = order_Description;
    }

    public String getBudget() {
        return Budget;
    }

    public void setBudget(String budget) {
        Budget = budget;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getAdded_By() {
        return Added_By;
    }

    public void setAdded_By(String added_By) {
        Added_By = added_By;
    }
}
