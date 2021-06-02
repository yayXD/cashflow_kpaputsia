package world.ucode.domain;

import javax.persistence.*;

@Entity
@Table(name = "category")
public class Category {
        @Id
        @GeneratedValue(strategy= GenerationType.AUTO)
        private Integer id;
        private String categoryName;
        private String description;

        public Category() {}

    public Category(String categoryName, String description) {
        this.categoryName = categoryName;
        this.description = description;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getDescription() {
        return description;
    }
}
