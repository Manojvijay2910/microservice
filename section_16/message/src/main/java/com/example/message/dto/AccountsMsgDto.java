package com.example.message.dto;

/**
 * @param accountNumber The account number
 * @param name The name of the account holder
 * @param email The email address of the account holder
 * @param mobileNumber The mobile number of the account holder
 */
public record AccountsMsgDto(Long accountNumber, String name, String email, String mobileNumber) {
}