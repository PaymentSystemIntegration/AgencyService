package com.sep.psp.dto;

import java.util.Date;

public class UserDTO {
    private Integer userId;
    private String pan;
    private double balance;

    public Integer getUserId(){ return userId; }
    public void setUserId(Integer userId){ this.userId = userId;}
    public String getPan(){ return pan; }
    public void setPan(String pan){ this.pan = pan; }
    public double getBalance(){ return balance; }
    public void setBalance(double balance){ this.balance = balance; }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userId=" + userId +
                ", pan='" + pan + '\'' +
                ", balance=" + balance +
                '}';
    }
}
