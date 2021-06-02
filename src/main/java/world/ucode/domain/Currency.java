package world.ucode.domain;

import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "currency")
public class Currency {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String curName;
    private String ticker;

    public Currency(){ }

    public Currency(String name, String ticker) {
        this.curName = name;
        this.ticker = ticker;
    }

    public String getCurName() {
        return curName;
    }

    public String getTicker() {
        return ticker;
    }

    public void setId(Integer id) {
        id = id;
    }

    public void setCurName(String curName) {
        this.curName = curName;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
}

