package com.almailem.ams.api.connector.model.salesInvoice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tblsalesinvoice")
public class SalesInvoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long salesInvoiceId;

    @NotBlank(message = "Company Code is mandatory")
    @Column(name = "COMPANY_CODE", columnDefinition = "nvarchar(50)")
    private String companyCode;

    @NotBlank(message = "Branch Code is mandatory")
    @Column(name = "BRANCH_CODE", columnDefinition = "nvarchar(50)")
    private String branchCode;

    @NotBlank(message = "Sales Invoice Number is mandatory")
    @Column(name = "SALES_INVOICE_NUMBER", columnDefinition = "nvarchar(50)")
    private String salesInvoiceNumber;

    @NotBlank(message = "Delivery Type is mandatory")
    @Column(name = "DELIVERY_TYPE", columnDefinition = "nvarchar(50)")
    private String deliveryType;

    @NotBlank(message = "Sales Order Number is mandatory")
    @Column(name = "SALES_ORDER_NUMBER", columnDefinition = "nvarchar(50)")
    private String salesOrderNumber;

    @NotBlank(message = "Pick List Number is mandatory")
    @Column(name = "PICK_LIST_NUMBER", columnDefinition = "nvarchar(50)")
    private String pickListNumber;

    @NotBlank(message = "Invoice Date is mandatory")
    private Date invoiceDate;

    @NotBlank(message = "Customer Id is mandatory")
    @Column(name = "CUSTOMER_ID", columnDefinition = "nvarchar(50)")
    private String customerId;

    @NotBlank(message = "Customer Name is mandatory")
    @Column(name = "CUSTOMER_NAME", columnDefinition = "nvarchar(50)")
    private String customerName;

    @NotBlank(message = "Address is mandatory")
    @Column(name = "ADDRESS", columnDefinition = "nvarchar(500)")
    private String address;

    @NotBlank(message = "Phone Number is mandatory")
    @Column(name = "PHONE_NUMBER", columnDefinition = "nvarchar(50)")
    private String phoneNumber;

    @NotBlank(message = "Alternate No is mandatory")
    @Column(name = "ALTERNATE_NO", columnDefinition = "nvarchar(50)")
    private String alternateNo;

    @Column(name = "STATUS", columnDefinition = "nvarchar(50)")
    private String status;

    @Column(name = "IS_COMPLETED", columnDefinition = "nvarchar(10)")
    private String isCompleted;

    private Date updatedOn;

    @Column(name = "IS_UNDELIVERED", columnDefinition = "nvarchar(10)")
    private String isUndelivered;

    //ProcessedStatusIdOrderByOrderReceivedOn
    @NotNull
    private Long processedStatusId = 0L;

    private Date orderReceivedOn;
    private Date orderProcessedOn;
}
