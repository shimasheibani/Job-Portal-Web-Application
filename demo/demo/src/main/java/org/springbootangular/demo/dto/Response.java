package org.springbootangular.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springbootangular.demo.enums.UserRole;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    //generic
    private int status;
    private String message;
    //for login
    private String token;
    private UserRole role;
    private String expirationTime;

    //for pagination
    private Integer totalPage;
    private Long totalElement;

    //data output
    private UserDTO user;
    private List<UserDTO> users;

    private SupplierDTO supplier;
    private List<SupplierDTO> suppliers;

    private ProductDTO product;
    private List<ProductDTO> products;

    private CategoryDTO category;
    private List<CategoryDTO> categories;

    private TransactionDTO transaction;
    private List<TransactionDTO> transactions;

    @Builder.Default
    private final LocalDateTime timeStamp = LocalDateTime.now();
}
