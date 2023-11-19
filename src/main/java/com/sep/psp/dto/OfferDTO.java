package com.sep.psp.dto;

public class OfferDTO {
    private Integer id;
    private String name;
    private double amount;
    private boolean deleted;
    private Integer userId;
    private Integer acquirerId;

    public Integer getId(){ return id; }
    public void setId(Integer id){ this.id = id;}
    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }
    public double getAmount() {return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public boolean getDeleted(){ return deleted; }
    public void setDeleted(boolean deleted){ this.deleted = deleted; }
    public Integer getUserId(){ return userId; }
    public void setUserId(Integer userId){ this.userId = userId;}
    public Integer getAcquirerId(){ return acquirerId; }
    public void setAcquirerId(Integer acquirerId){ this.acquirerId = acquirerId;}
}
