package org.springbootangular.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springbootangular.demo.enums.TransactionStatus;
import org.springbootangular.demo.enums.TransactonType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name="transaction")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Total Product is required")
    @Column(name="total_product")
    private BigDecimal totalProduct;

    @NotBlank(message = "Total price is required")
    @Column(name="total_price")
    private String totalPrice;

    @NotBlank(message = "transactionType is required")
    @Enumerated(EnumType.STRING)
    @Column(name="transaction_type")
    private TransactonType transactionType;

    @NotBlank(message = "transactionStatus is required")
    @Column(name="transaction_status")
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    @NotBlank(message = "Description is required")
    private String description;

    private String note;

    @Column(name="update_at")
    private LocalDateTime updateAt ;

    @Column(name="create_at")
    private final LocalDateTime createAt = LocalDateTime.now();

//    @OneToMany(mappedBy = "Transaction")
//    private List<Product> products;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name="product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="supplier_id")
    private Supplier supplier;


    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", totalProduct='" + totalProduct + '\'' +
                ", totalPrice='" + totalPrice + '\'' +
                ", transactionType=" + transactionType +
                ", transactionStatus=" + transactionStatus +
                ", description='" + description + '\'' +
                ", note='" + note + '\'' +
                ", updateAt=" + updateAt +
                ", createAt=" + createAt +
                '}';
    }
}
