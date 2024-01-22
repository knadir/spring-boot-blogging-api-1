package com.khai.blogapi.controller;

import com.khai.blogapi.payload.ApiResponse;
import com.khai.blogapi.payload.BankRequest;
import com.khai.blogapi.payload.BankResponse;
import com.khai.blogapi.payload.PageResponse;
import com.khai.blogapi.security.CurrentUser;
import com.khai.blogapi.security.UserPrincipal;
import com.khai.blogapi.service.BankService;
import com.khai.blogapi.service.BlogService;
import com.khai.blogapi.utils.AppConstant;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api/banks")
public class BankController {

  @Autowired
  BankService bankService;

  @Autowired
  BlogService blogService;

  @GetMapping
  public ResponseEntity<PageResponse<BankResponse>> getAllBanks(
    @RequestParam(
      value = "page",
      defaultValue = AppConstant.DEFAULT_PAGE_NUMBER
    ) Integer page,
    @RequestParam(
      value = "size",
      defaultValue = AppConstant.DEFAULT_PAGE_SIZE
    ) Integer size
  ) {
    PageResponse<BankResponse> bankResponse = bankService.getAllBanks(
      page,
      size
    );
    return new ResponseEntity<>(bankResponse, HttpStatus.OK);
  }

  @GetMapping("/getAll")
  public ResponseEntity<List<BankResponse>> getBanks() {
    List<BankResponse> bankResponse = bankService.getBanks();
    return new ResponseEntity<>(bankResponse, HttpStatus.OK);
  }

  @GetMapping("/{bank_id}")
  public ResponseEntity<BankResponse> getBankById(
    @PathVariable("bank_id") Long bankId
  ) {
    BankResponse bankResponse = bankService.getBankById(bankId);
    return new ResponseEntity<>(bankResponse, HttpStatus.OK);
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  public ResponseEntity<BankResponse> createBank(
    @RequestBody final BankRequest bankRequest,
    @CurrentUser UserPrincipal userPrincipal
  ) {
    //System.out.println("bankRequest..." + bankRequest);
    //System.out.println("userPrincipal..." + userPrincipal);

    BankResponse bankResponse = bankService.createBank(
      bankRequest,
      userPrincipal
    );
    return new ResponseEntity<>(bankResponse, HttpStatus.CREATED);
  }

  @DeleteMapping("/{bank_id}")
  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  public ResponseEntity<ApiResponse> deleteBankById(
    @PathVariable("bank_id") Long bankId,
    @CurrentUser UserPrincipal userPrincipal
  ) {
    ApiResponse response = bankService.deleteBankById(bankId, userPrincipal);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PutMapping("/{bank_id}")
  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  public ResponseEntity<BankResponse> updateBankById(
    @PathVariable("bank_id") Long bankId,
    @RequestBody BankRequest bankRequest,
    @CurrentUser UserPrincipal userPrincipal
  ) {
    BankResponse bankResponse = bankService.updateBankById(
      bankId,
      bankRequest,
      userPrincipal
    );
    return new ResponseEntity<>(bankResponse, HttpStatus.OK);
  }
}
