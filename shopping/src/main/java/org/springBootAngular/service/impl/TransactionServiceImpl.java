package org.springBootAngular.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springBootAngular.dto.Response;
import org.springBootAngular.dto.TransactionDTO;
import org.springBootAngular.dto.TransactionRequest;
import org.springBootAngular.entity.*;
import org.springBootAngular.enums.TransactionStatus;
import org.springBootAngular.enums.TransactonType;
import org.springBootAngular.exception.NotFoundException;
import org.springBootAngular.repository.*;
import org.springBootAngular.service.TransactionService;
import org.springBootAngular.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final UserRepository userRepository;
    private final UserService userService;



    @Override
    public Response restockInventory(TransactionRequest transactionRequest) {
        Long productId = transactionRequest.getProductId();
        Long supplierId =  transactionRequest.getSupplierId();
        Integer quantity = transactionRequest.getQuantity();
        if(productId == null) throw new NotFoundException("Product id is required");

        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product not found"));
        Supplier supplier = supplierRepository.findById(supplierId).orElseThrow(() -> new NotFoundException("Supplier not found"));
        User user = userService.getCurentLoggedUser();

        product.setStockQuality(product.getStockQuality() + quantity);
        productRepository.save(product);

        Transaction transaction = Transaction.builder()
                .transactionType(TransactonType.PURCHASE)
                .transactionStatus(TransactionStatus.COMPLETE )
                .product(product)
                .user(user)
                .supplier(supplier)
                .totalProducts(quantity)
                .totalPrice(product.getPrice().multiply((BigDecimal.valueOf(quantity)) ))
                .description(transactionRequest.getDescription())
                .build();
        transactionRepository.save(transaction);
        return Response.builder()
                .status(200)
                .message("Transaction Made successfully")
                .build();
    }

    @Override
    public Response sell(TransactionRequest transactionRequest) {
        Long productId = transactionRequest.getProductId();
        Integer quantity = transactionRequest.getQuantity();
        if(productId == null) throw new NotFoundException("Product id is required");

        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product not found"));
        User user = userService.getCurentLoggedUser();

        product.setStockQuality(product.getStockQuality() - quantity);
        productRepository.save(product);

        Transaction transaction = Transaction.builder()
                .transactionType(TransactonType.SALE)
                .transactionStatus(TransactionStatus.COMPLETE )
                .product(product)
                .user(user)
                .totalProducts(quantity)
                .totalPrice(product.getPrice().multiply((BigDecimal.valueOf(quantity)) ))
                .description(transactionRequest.getDescription())
                .build();
        transactionRepository.save(transaction);
        return Response.builder()
                .status(200)
                .message("Transaction Sale Made successfully")
                .build();
    }

    @Override
    public Response returnToSupplier(TransactionRequest transactionRequest) {
        Long productId = transactionRequest.getProductId();
        Integer quantity = transactionRequest.getQuantity();
        Long supplierId = transactionRequest.getSupplierId();
        User user = userService.getCurentLoggedUser();

        if(productId == null) throw new NotFoundException("Product id is required");

        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product not found"));
        product.setStockQuality(product.getStockQuality() + quantity);
        productRepository.save(product);

        Supplier supplier = supplierRepository.findById(supplierId).orElseThrow(() -> new NotFoundException("Supplier not found"));


        Transaction transaction = Transaction.builder()
                .transactionStatus(TransactionStatus.CANCELLED)
                .description(transactionRequest.getDescription())
                .transactionType(TransactonType.RETURN_TO_SUPLIER)
                .totalProducts(transactionRequest.getQuantity())
                .user(user)
                .supplier(supplier)
                .product(product)
                .totalPrice(BigDecimal.ZERO)
                .build();
        transactionRepository.save(transaction);
        return Response.builder()
                .status(200)
                .message("Transaction return  successfully initialized")
                .build();


    }

    @Override
    public Response getAllTransactions(int page, int size, String searchBox) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<Transaction> transactionPage = transactionRepository.searchTransactions(searchBox, Pageable.unpaged());
        List<TransactionDTO> transactionDTOs = modelMapper.map(transactionPage.getContent(), new TypeToken<List<TransactionDTO>>(){}.getType());
        transactionDTOs.forEach(transactionItem->{
            transactionItem.setUser(null);
            transactionItem.setSupplier(null);
            transactionItem.setProduct(null);
        });
        return Response.builder()
                .status(200)
                .message("All transactions created successfully")
                .transactions(transactionDTOs)
                .build();
    }

    @Override
    public Response getAllTransactionsByMonthAndYear(int month, int year) {
        List<Transaction> transactions = transactionRepository.findAllByMonthAndYear(month, year);
        List<TransactionDTO> transactionDTOs = modelMapper.map(transactions, new TypeToken<List<TransactionDTO>>(){}.getType());
        transactionDTOs.forEach(transactionItem->{
            transactionItem.setUser(null);
            transactionItem.setSupplier(null);
            transactionItem.setProduct(null);
        });
        return Response.builder()
                .status(200)
                .message("Transaction retrieved successfully")
                .transactions(transactionDTOs)
                .build();
    }

    @Override
    public Response getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(()-> new NotFoundException("Transaction Not Found"));
        TransactionDTO transactionDTO = modelMapper.map(transaction, TransactionDTO.class);
        transactionDTO.getUser().setTransactions(null);
        return Response.builder()
                .status(200)
                .message("Transaction retrieved successfully")
                .transaction(transactionDTO)
                .build();

    }

    @Override
    public Response updateTransactionStatus(Long transactionId, TransactionStatus transactionStatus) {
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(()-> new NotFoundException("Transaction Not Found"));

        transaction.setTransactionStatus(transactionStatus);
        transaction.setUpdateAt(LocalDateTime.now());

        transactionRepository.save(transaction);

        return Response.builder()
                .status(200)
                .message("Transaction Update Successfully")
                .build();
    }



    @Override
    public Response deleteTransaction(Long id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(()-> new NotFoundException("Transaction Not Found"));
        transactionRepository.delete(transaction);
        return Response.builder()
                .status(200)
                .message("Transaction Delete Successfully")
                .build();
    }
}
