package com.almailem.ams.api.connector.model.stockreceipt;

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
@Table(name = "STOCKRECEIPTLINE")
public class StockReceiptLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StockReceiptLineId")
    private Long stockReceiptLineId;

    @Column(name = "StockReceiptHeaderId")
    private Long stockReceiptHeaderId;

    @NotBlank(message = "Company Code is mandatory")
    @Column(name = "CompanyCode", columnDefinition = "nvarchar(25)")
    private String companyCode;

    @NotBlank(message = "Branch Code is mandatory")
    @Column(name = "Branchcode", columnDefinition = "nvarchar(25)")
    private String branchCode;

    @NotBlank(message = "Receipt Number is mandatory")
    @Column(name = "ReceiptNo", columnDefinition = "nvarchar(50)")
    private String receiptNo;

    @NotNull(message = "Line No for Each Item is mandatory")
    @Column(name = "LineNoforeachitem")
    private Long lineNoForEachItem;

    @NotBlank(message = "Item Code is mandatory")
    @Column(name = "Itemcode", columnDefinition = "nvarchar(50)")
    private String itemCode;

    @NotBlank(message = "Item Description is mandatory")
    @Column(name = "ItemDescription", columnDefinition = "nvarchar(500)")
    private String itemDescription;

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

    @NotBlank(message = "Receipt Date is mandatory")
    @Column(name = "Receiptdate")
    private Date receiptDate;

    @NotNull(message = "Receipt Quantity is mandatory")
    @Column(name = "ReceiptQty")
    private Double receiptQty;

    @NotBlank(message = "UOM is mandatory")
    @Column(name = "UnitofMeasure", columnDefinition = "nvarchar(50)")
    private String unitOfMeasure;

    @Column(name = "SupplierName", columnDefinition = "nvarchar(250)")
    private String supplierName;

    @Column(name = "Manufacturerfullname", columnDefinition = "nvarchar(250)")
    private String manufacturerFullName;

    @Column(name = "IS_COMPLETED", columnDefinition = "nvarchar(10)")
    private String isCompleted;

}