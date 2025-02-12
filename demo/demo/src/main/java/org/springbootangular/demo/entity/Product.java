package org.springbootangular.demo.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

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

        @NotBlank(message = "Price is required")
        private BigDecimal price;

        @NotBlank(message = "stock Quality is required")
        @Column(name="stock_quality")
        private Integer stockQuality;

        @NotBlank(message = "Description is required")
        private String decription;

        @NotBlank(message = "Description is required")
        @Column(name="expire_date")
        private LocalDateTime expireDate;

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
