package world.ucode.domain;

import javax.persistence.*;

@Entity
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String tagName;
    private String description;

    public Tag() {}

    public Tag(String tagName, String description) {
        this.tagName = tagName;
        this.description = description;
    }

    public String getTagName() {
        return tagName;
    }

    public String getDescription() {
        return description;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
