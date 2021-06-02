package world.ucode.domain;

import javax.persistence.*;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @ManyToOne(cascade =CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet walletName;
    private String type;
    private Double spending;
    private String date;
    private int receiverNumber;
    private String description;

    public Transaction(){}

    public Transaction(Wallet walletName, String type,
                       //Category category, Tag tag,
                       Double spending, String date, String description, int receiverNumber) {
        this.walletName = walletName;
        this.type = type;
//        this.category = category;
//        this.tag = tag;
        this.spending = spending;
        this.date = date;
        this.description = description;
        this.receiverNumber = receiverNumber;
    }

    public Wallet getWalletName() {
        return walletName;
    }

    public String getType() {
        return type;
    }

//    public Category getCategory() {
//        return category;
//    }
//
//    public Tag getTag() {
//        return tag;
//    }

    public Double getSpending() {
        return spending;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public void setWalletName(Wallet walletName) {
        this.walletName = walletName;
    }

    public void setType(String type) {
        this.type = type;
    }

//    public void setCategory(Category category) {
//        this.category = category;
//    }
//
//    public void setTag(Tag tag) {
//        this.tag = tag;
 //   }

    public void setBalance(Double spending) {
        this.spending = spending;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReceiverNumber() {
        return receiverNumber;
    }

    public void setReceiverNumber(int receiverNumber) {
        this.receiverNumber = receiverNumber;
    }
}
