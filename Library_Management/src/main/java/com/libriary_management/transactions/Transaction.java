package com.libriary_management.transactions;

import java.time.LocalDate;

public class Transaction {
    private String transactionId;
    private String userId;
    private String isbn;
    private LocalDate issueDate; 
    private LocalDate returnDate;
    private LocalDate dueDate; //add due date based on return period
    private String status; // "ISSUED" or "RETURNED"
    private static final int DEFAULT_RETURN_PERIOD_DAYS = 14;

    public Transaction() {}

    public Transaction(String transactionId, String userId, String isbn, LocalDate issueDate, String status) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.isbn = isbn;
        this.issueDate = issueDate;
        this.dueDate=issueDate.plusDays(DEFAULT_RETURN_PERIOD_DAYS);
        this.status = status;
    }

    public void returnBook() {
    	this.status="RETURNED";
    	this.returnDate=LocalDate.now();
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getisbn() {
        return isbn;
    }

    public void setisbn(String isbn) {
        this.isbn = isbn;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
	@Override
    public String toString() {
        return "Transaction [transactionId=" + transactionId + ", userId=" + userId + ", isbn=" + isbn + 
               ", issueDate=" + issueDate + ", returnDate=" + returnDate + ", dueDate=" + dueDate + 
               ", status=" + status + "]";
    }
}