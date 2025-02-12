package org.springBootAngular.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="product")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data

public class Product {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotBlank(message = "Username is required")
        @Column(columnDefinition = "VARCHAR2(20)")
        private String name;

        @Column(unique = true)
        private String sku;

        @NotNull(message = "Price is required")
        @Positive(message = "Price must be greater than zero")
        private BigDecimal price;

        @NotNull(message = "stock Quality is required")
        @Column(name="stock_quality")
        private Integer stockQuality;

//        @NotBlank(message = "Description is required")
        private String decription;


        @Column(name="expire_date", nullable = true)
        private LocalDateTime expireDate ;


        private LocalDateTime updateAt ;

        @Column(name="image_url")
        private String imageUrl;

        @Column(name="create_at")
        private final LocalDateTime createAt = LocalDateTime.now();

        @ManyToOne
        @JoinColumn(name="category_id")
        private Category category;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sku='" + sku + '\'' +
                ", price='" + price + '\'' +
                ", stockQuality='" + stockQuality + '\'' +
                ", decription='" + decription + '\'' +
                ", expireDate='" + expireDate + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", createAt=" + createAt +
                '}';
    }
}
