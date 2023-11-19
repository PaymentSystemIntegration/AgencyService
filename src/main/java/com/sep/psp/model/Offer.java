package com.sep.psp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "offer")
public class Offer {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Integer id;
    private String name;
    private double amount;
    private boolean deleted;

    @ManyToOne
    private User user;

    @ManyToOne
    private User acquirerUser;

    public Integer getId(){ return id; }
    public void setId(Integer id){ this.id = id;}
    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }
    public double getAmount() {return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public User getUser() { return user; }
    public void setUser(User user) {this.user = user; }
    public User getAcquirerUser() { return acquirerUser; }
    public void setAcquirerUser(User acquirerUser) {this.acquirerUser = acquirerUser; }
    public boolean getDeleted(){ return deleted; }
    public void setDeleted(boolean deleted){ this.deleted = deleted; }

}
