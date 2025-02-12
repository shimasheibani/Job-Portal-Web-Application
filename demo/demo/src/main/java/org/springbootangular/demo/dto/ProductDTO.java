package org.springbootangular.demo.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springbootangular.demo.entity.Category;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {

        private Long id;

        private Long productId;
        private Long categoryId;
        private Long supplierId;

        private String name;

        private String sku;

        private BigDecimal price;

        private Integer stockQuality;

        private String decription;

        private LocalDateTime expireDate;

        private String imageUrl;

        private final LocalDateTime createAt = LocalDateTime.now();


}
