package org.springBootAngular.service;

import org.springBootAngular.dto.Response;
import org.springBootAngular.dto.TransactionDTO;
import org.springBootAngular.dto.TransactionRequest;
import org.springBootAngular.entity.Transaction;
import org.springBootAngular.enums.TransactionStatus;

public interface TransactionService {

    Response restockInventory(TransactionRequest transactionRequest);
    Response sell(TransactionRequest transactionRequest);
    Response returnToSupplier(TransactionRequest transactionRequest);
    Response getAllTransactions(int page, int size, String searchBox);
    Response getAllTransactionsByMonthAndYear(int month, int year);
    Response getTransactionById(Long id);
    Response updateTransactionStatus(Long transactionId, TransactionStatus  transactionStatus);

    Response deleteTransaction(Long id);
}
