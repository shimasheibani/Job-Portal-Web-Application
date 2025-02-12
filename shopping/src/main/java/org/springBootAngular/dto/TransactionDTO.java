package org.springBootAngular.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springBootAngular.enums.TransactionStatus;
import org.springBootAngular.enums.TransactonType;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDTO {

    private Long id;

    private BigDecimal totalProduct;


    private String totalPrice;


    private TransactonType transactionType;


    private TransactionStatus transactionStatus;


    private String description;

    private String note;


    private LocalDateTime updateAt ;


    private final LocalDateTime createAt = LocalDateTime.now();



    private ProductDTO product;


    private UserDTO user;


    private SupplierDTO supplier;



}
