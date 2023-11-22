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
@Table(name = "SUPPLIERINVOICELINE")
public class SupplierInvoiceLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SupplierInvoiceLineId")
    private Long supplierInvoiceLineId;

    @Column(name = "SupplierInvoiceHeaderId")
    private Long supplierInvoiceHeaderId;

    @NotBlank(message = "Company Code is mandatory")
    @Column(name = "CompanyCode", columnDefinition = "nvarchar(25)")
    private String companyCode;

    @NotBlank(message = "Branch Code is mandatory")
    @Column(name = "Branchcode", columnDefinition = "nvarchar(25)")
    private String branchCode;

    @NotBlank(message = "Supplier Invoice No is mandatory")
    @Column(name = "SupplierInvoiceNo", columnDefinition = "nvarchar(50)")
    private String supplierInvoiceNo;

    @NotNull(message = "Line No for each item is mandatory")
    @Column(name = "LineNoforeachitem")
    private Long lineNoForEachItem;

    @NotBlank(message = "Item Code is mandatory")
    @Column(name = "Itemcode", columnDefinition = "nvarchar(50)")
    private String itemCode;

    @NotBlank(message = "Item Description is mandatory")
    @Column(name = "ItemDescription", columnDefinition = "nvarchar(500)")
    private String itemDescription;

    @Column(name = "ContainerNo", columnDefinition = "nvarchar(50)")
    private String containerNo;

//    @NotBlank(message = "Supplier Code is mandatory")
    @Column(name = "Suppliercode", columnDefinition = "nvarchar(50)")
    private String supplierCode;

    @Column(name = "SupplierPartNo", columnDefinition = "nvarchar(50)")
    private String supplierPartNo;

    @NotBlank(message = "Manufacturer Short Name is mandatory")
    @Column(name = "ManufacturershortName", columnDefinition = "nvarchar(200)")
    private String manufacturerShortName;

    @NotBlank(message = "Manufacturer Code is mandatory")
    @Column(name = "Manufacturercode", columnDefinition = "nvarchar(200)")
    private String manufacturerCode;

//    @NotBlank(message = "Invoice Date is mandatory")
    @Column(name = "Invoicedate")
    private Date invoiceDate;

    @NotNull(message = "Invoice Qty is mandatory")
    @Column(name = "InvoiceQty")
    private Double invoiceQty;

    @NotBlank(message = "Unit Of Measure is mandatory")
    @Column(name = "UnitofMeasure", columnDefinition = "nvarchar(50)")
    private String unitOfMeasure;

    @Column(name = "SupplierName", columnDefinition = "nvarchar(250)")
    private String supplierName;

    @Column(name = "Manufacturerfullname", columnDefinition = "nvarchar(250)")
    private String manufacturerFullName;

    @Column(name = "Receiveddate")
    private Date receivedDate;

    @Column(name = "ReceivedQty")
    private Double receivedQty;

    @Column(name = "Receivedby", columnDefinition = "nvarchar(50)")
    private String receivedBy;

    @Column(name = "IS_COMPLETED", columnDefinition = "nvarchar(10)")
    private String isCompleted;

    @Column(name = "IS_CANCELLED", columnDefinition = "nvarchar(10)")
    private String isCancelled;
}