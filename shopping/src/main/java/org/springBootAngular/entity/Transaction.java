package org.springBootAngular.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springBootAngular.enums.TransactionStatus;
import org.springBootAngular.enums.TransactonType;


import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    @NotNull(message = "Total Product is required")
    @Column(name="total_product")
    private Integer totalProducts;

    @NotNull(message = "Total price is required")
    @Column(name="total_price")
    private BigDecimal totalPrice;

    @NotNull(message = "transactionType is required")
    @Enumerated(EnumType.STRING)
    @Column(name="transaction_type")
    private TransactonType transactionType;

    @NotNull(message = "transactionStatus is required")
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
    @JoinColumn(name="product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="supplier_id")
    private Supplier supplier;


    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", totalProduct='" + totalProducts + '\'' +
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
