package com.spring.distributed.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.distributed.service.TransferService;

@RestController
@RequestMapping("/api")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transferMoney(@RequestParam Long fromAccountId,
            @RequestParam Long toAccountId,
            @RequestParam double amount) {
        try {
            transferService.transfer(fromAccountId, toAccountId, amount);
            return ResponseEntity.ok("Transfer successful");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Transfer failed: " + e.getMessage());
        }
    }

    @PostMapping("/local-transfer")
    public ResponseEntity<String> localTransferMoney(@RequestParam Long fromAccountId,
            @RequestParam Long toAccountId,
            @RequestParam double amount) {
        try {
            transferService.localTransfer(fromAccountId, toAccountId, amount);
            return ResponseEntity.ok("Transfer successful");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Transfer failed: " + e.getMessage());
        }
    }
}
