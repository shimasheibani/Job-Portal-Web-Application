package org.springbootangular.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class TransactionRequest {
    @Positive(message="Product id is requierd")
    private Long productId;

    @Positive(message="Quantiry is requierd")
    private Integer Quantity;

    @Positive(message="Supplier id is requierd")
    private Long supplierId;
    private String description;
}
