package org.springBootAngular.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springBootAngular.dto.Response;
import org.springBootAngular.dto.TransactionRequest;
import org.springBootAngular.enums.TransactionStatus;
import org.springBootAngular.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@Slf4j
@AllArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/purchase")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Response> purchaseInventory(@RequestBody @Valid TransactionRequest transactionRequest){
        return ResponseEntity.ok(transactionService.restockInventory(transactionRequest));
    }
    @PostMapping("/sell")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Response> sell(@RequestBody @Valid TransactionRequest transactionRequest){
        return ResponseEntity.ok(transactionService.sell(transactionRequest));
    }
    @PostMapping("/return")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Response> returnToSupplier(@RequestBody @Valid TransactionRequest transactionRequest){
        return ResponseEntity.ok(transactionService.returnToSupplier(transactionRequest));
    }
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Response> getAllTransactions(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "1000") int size,
                                                       @RequestParam(required = false) String searchBox){
        return ResponseEntity.ok(transactionService.getAllTransactions(page, size, searchBox));
    }
    @GetMapping("/allbyyearandmonth")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Response> getAllTransactionsByMonthAndYear(@RequestParam int month, @RequestParam int year){
        return ResponseEntity.ok(transactionService.getAllTransactionsByMonthAndYear(month, year));
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Response> getTransactionById(@PathVariable Long id){
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Response> updateTransactionStatus(@PathVariable Long id, @RequestBody @Valid TransactionStatus transactionStatus){
        return ResponseEntity.ok(transactionService.updateTransactionStatus(id, transactionStatus));
    }
}
