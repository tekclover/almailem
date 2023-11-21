package com.almailem.ams.api.connector.model.supplierinvoice;

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
@Table(name = "tblsupplierinvoiceline")
public class SupplierInvoiceLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplierInvoiceLineId;

    private Long supplierInvoiceHeaderId;

    @NotBlank(message = "Company Code is mandatory")
    @Column(name = "COMPANY_CODE", columnDefinition = "nvarchar(25)")
    private String companyCode;

    @NotBlank(message = "Branch Code is mandatory")
    @Column(name = "BRANCH_CODE", columnDefinition = "nvarchar(25)")
    private String branchCode;

    @NotBlank(message = "Supplier Invoice No is mandatory")
    @Column(name = "SUPPLIER_INVOICE_NO", columnDefinition = "nvarchar(50)")
    private String supplierInvoiceNo;

    @NotNull(message = "Line No for each item is mandatory")
    private Long lineNoForEachItem;

    @NotBlank(message = "Item Code is mandatory")
    @Column(name = "ITEM_CODE", columnDefinition = "nvarchar(50)")
    private String itemCode;

    @NotBlank(message = "Item Description is mandatory")
    @Column(name = "ITEM_DESCRIPTION", columnDefinition = "nvarchar(500)")
    private String itemDescription;

    @Column(name = "CONTAINER_NO", columnDefinition = "nvarchar(50)")
    private String containerNo;

    @NotBlank(message = "Supplier Code is mandatory")
    @Column(name = "SUPPLIER_CODE", columnDefinition = "nvarchar(50)")
    private String supplierCode;

    @Column(name = "SUPPLIER_PART_NO", columnDefinition = "nvarchar(50)")
    private String supplierPartNo;

    @NotBlank(message = "Manufacturer Short Name is mandatory")
    @Column(name = "MANUFACTURER_SHORT_NAME", columnDefinition = "nvarchar(200)")
    private String manufacturerShortName;

    @NotBlank(message = "Manufacturer Code is mandatory")
    @Column(name = "MANUFACTURER_CODE", columnDefinition = "nvarchar(200)")
    private String manufacturerCode;

    @NotBlank(message = "Invoice Date is mandatory")
    private Date invoiceDate;

    @NotNull(message = "Invoice Qty is mandatory")
    private Double invoiceQty;

    @NotBlank(message = "Unit Of Measure is mandatory")
    @Column(name = "UNIT_OF_MEASURE", columnDefinition = "nvarchar(50)")
    private String unitOfMeasure;

    @Column(name = "SUPPLIER_NAME", columnDefinition = "nvarchar(250)")
    private String supplierName;

    @Column(name = "MANUFACTURER_FULL_NAME", columnDefinition = "nvarchar(250)")
    private String manufacturerFullName;

    private Date receivedDate;

    private Double receivedQty;

    @Column(name = "RECEIVED_BY", columnDefinition = "nvarchar(50)")
    private String receivedBy;

    @Column(name = "IS_COMPLETED", columnDefinition = "nvarchar(10)")
    private String isCompleted;

    @Column(name = "IS_CANCELLED", columnDefinition = "nvarchar(10)")
    private String isCancelled;
}