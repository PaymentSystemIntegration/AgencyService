package com.sep.psp.dto;

import com.sep.psp.model.User;

public class OfferDTO {
    private Integer id;
    private String name;
    private double amount;
    private boolean deleted;
    private Integer userId;
    private Integer acquirerId;
    private String panMerchant;
    private double balanceMerchant;
    private String panAcquirer;
    private double balanceAcquirer;
    private User userByAcquirerId;
    private User userByMerchantId;

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
    public String getPanMerchant() { return panMerchant; }
    public void setPanMerchant(String panMerchant) { this.panMerchant = panMerchant; }
    public double getBalanceMerchant() { return balanceMerchant; }
    public void setBalanceMerchant(double balanceMerchant) {this.balanceMerchant = balanceMerchant;}
    public String getPanAcquirer() {return panAcquirer;}
    public void setPanAcquirer(String panAcquirer) {this.panAcquirer = panAcquirer;}
    public double getBalanceAcquirer() {return balanceAcquirer;}
    public void setBalanceAcquirer(double balanceAcquirer) {this.balanceAcquirer = balanceAcquirer;}
    public User getUserByMerchantId(){ return userByMerchantId; }
    public void setUserByMerchantId(User userByMerchantId){ this.userByMerchantId = userByMerchantId; }
    public User getUserByAcquirerId(){ return userByAcquirerId; }
    public void setUserByAcquirerId(User userByAcquirerId){ this.userByAcquirerId = userByAcquirerId; }
    @Override
    public String toString() {
        return "OfferDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", panMerchant=" + panMerchant +
                ", panAcquirer=" + panAcquirer +
                ", balanceMerchant='" + balanceMerchant + '\'' +
                ", balanceAcquirer'" + balanceAcquirer + '\'' +
                ", deleted=" + deleted +
                ", userId=" + userId +
                ", acquirerId=" + acquirerId +
                ", userByAcquirerId=" + userByAcquirerId +
                ", userByMerchantId=" + userByMerchantId +
                '}';
    }

}
