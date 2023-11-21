package com.almailem.ams.api.connector.model.salesreturn;

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

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tblsalesreturnline")
public class SalesReturnLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long salesReturnLineId;

    private Long salesReturnHeaderId;

    @NotNull(message = "Line No of Each Item is mandatory")
    private Long lineNoOfEachItem;

    @NotBlank(message = "Item Code is mandatory")
    @Column(name = "ITEM_CODE", columnDefinition = "nvarchar(50)")
    private String itemCode;

    @NotBlank(message = "Item Description is mandatory")
    @Column(name = "ITEM_DESCRIPTION", columnDefinition = "nvarchar(500)")
    private String itemDescription;

    @NotBlank(message = "Reference Invoice No is mandatory")
    @Column(name = "REFERENCE_INVOICE_NO", columnDefinition = "nvarchar(50)")
    private String referenceInvoiceNo;

    @Column(name = "SOURCE_BRANCH_CODE", columnDefinition = "nvarchar(50)")
    private String sourceBranchCode;

    @Column(name = "SUPPLIER_PART_NO", columnDefinition = "nvarchar(50)")
    private String supplierPartNo;

    @NotBlank(message = "Manufacturer Short Name is mandatory")
    @Column(name = "MANUFACTURER_SHORT_NAME", columnDefinition = "nvarchar(200)")
    private String manufacturerShortName;

    @NotBlank(message = "Return Order Date is mandatory")
    private String returnOrderDate;

    @NotNull(message = "Return Qty is mandatory")
    private Double returnQty;

    @NotBlank(message = "UOM is mandatory")
    @Column(name = "UNIT_OF_MEASURE", columnDefinition = "nvarchar(50)")
    private String unitOfMeasure;

    private Long noOfPacks;

    @Column(name = "COUNTRY_OF_ORIGIN", columnDefinition = "nvarchar(250)")
    private String countryOfOrigin;

    @NotBlank(message = "Manufacturer Code is mandatory")
    @Column(name = "MANUFACTURER_CODE", columnDefinition = "nvarchar(200)")
    private String manufacturerCode;

    @Column(name = "MANUFACTURER_FULL_NAME", columnDefinition = "nvarchar(250)")
    private String manufacturerFullName;

    @Column(name = "IS_COMPLETED", columnDefinition = "nvarchar(10)")
    private String isCompleted;

    @Column(name = "IS_CANCELLED", columnDefinition = "nvarchar(10)")
    private String isCancelled;
}