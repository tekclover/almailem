package com.almailem.ams.api.connector.model.salesinvoice;

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
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SALESINVOICE")
public class SalesInvoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SalesInvoiceId")
    private Long salesInvoiceId;

    @NotBlank(message = "Company Code is mandatory")
    @Column(name = "companyCode", columnDefinition = "nvarchar(50)")
    private String companyCode;

    @NotBlank(message = "Branch Code is mandatory")
    @Column(name = "branchCode", columnDefinition = "nvarchar(50)")
    private String branchCode;

    @NotBlank(message = "Sales Invoice Number is mandatory")
    @Column(name = "salesInvoiceNumber", columnDefinition = "nvarchar(50)")
    private String salesInvoiceNumber;

    @NotBlank(message = "Delivery Type is mandatory")
    @Column(name = "Deliverytype", columnDefinition = "nvarchar(50)")
    private String deliveryType;

    @NotBlank(message = "Sales Order Number is mandatory")
    @Column(name = "salesOrderNumber", columnDefinition = "nvarchar(50)")
    private String salesOrderNumber;

    @NotBlank(message = "Pick List Number is mandatory")
    @Column(name = "pickListNumber", columnDefinition = "nvarchar(50)")
    private String pickListNumber;

    @NotBlank(message = "Invoice Date is mandatory")
    @Column(name = "InvoiceDate")
    private Date invoiceDate;

    @NotBlank(message = "Customer Id is mandatory")
    @Column(name = "CustomerID", columnDefinition = "nvarchar(50)")
    private String customerId;

    @NotBlank(message = "Customer Name is mandatory")
    @Column(name = "CustomerName", columnDefinition = "nvarchar(50)")
    private String customerName;

    @NotBlank(message = "Address is mandatory")
    @Column(name = "Address", columnDefinition = "nvarchar(500)")
    private String address;

    @NotBlank(message = "Phone Number is mandatory")
    @Column(name = "PhoneNumber", columnDefinition = "nvarchar(50)")
    private String phoneNumber;

    @NotBlank(message = "Alternate No is mandatory")
    @Column(name = "AlternateNo", columnDefinition = "nvarchar(50)")
    private String alternateNo;

    @Column(name = "Status", columnDefinition = "nvarchar(50)")
    private String status;

    //ProcessedStatusIdOrderByOrderReceivedOn
    @Column(name = "processedStatusId", columnDefinition = "bigint default'0'")
    private Long processedStatusId = 0L;
    @Column(name = "orderReceivedOn", columnDefinition = "datetime2 default getdate()")
    private Date orderReceivedOn;
    private Date orderProcessedOn;
}