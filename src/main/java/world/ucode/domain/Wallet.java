package world.ucode.domain;

import javax.persistence.*;

@Entity
@Table(name = "wallet")
public class Wallet {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String name;
    private Double balance;

    @ManyToOne(cascade =CascadeType.ALL, fetch = FetchType.EAGER)
   @JoinColumn(name = "currency_id", nullable = false)
    private Currency currencyNames;
   // private String currencyName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Registration ownerLogin;
    private String data;
    private int itemNumber;

    @ManyToOne(cascade =CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(cascade =CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;
    public Wallet(){}

    public Wallet(String name, Double balance, Currency currencyNames, Registration registration, String data,
                  int itemNumber, Category category, Tag tag) {
        this.name = name;
        this.balance = balance;
        this.currencyNames = currencyNames;
        this.ownerLogin = registration;
        this.data = data;
        this.itemNumber = itemNumber;
        this.category = category;
        this.tag = tag;

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public Double getBalance() {
        return balance;
    }

    public Registration getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(Registration ownerLogin) {
        this.ownerLogin = ownerLogin;
    }

    public Currency getCurrencyNames() {
        return currencyNames;
    }

    public void setCurrencyNames(Currency  currencyNames) {
        this.currencyNames = currencyNames;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(int itemNumber) {
        this.itemNumber = itemNumber;
    }

    public Category getCategory() {
        return category;
    }

    public Tag getTag() {
        return tag;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}

