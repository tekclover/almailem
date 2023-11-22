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
@Table(name = "SALESRETURNLINE")
public class SalesReturnLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SalesReturnLineId")
    private Long salesReturnLineId;

    @Column(name = "SalesReturnHeaderId")
    private Long salesReturnHeaderId;

    @NotNull(message = "Line No of Each Item is mandatory")
    @Column(name = "LineNoofeachitem")
    private Long lineNoOfEachItem;

    @NotBlank(message = "Item Code is mandatory")
    @Column(name = "Itemcode", columnDefinition = "nvarchar(50)")
    private String itemCode;

    @NotBlank(message = "Item Description is mandatory")
    @Column(name = "Itemdescription", columnDefinition = "nvarchar(500)")
    private String itemDescription;

    @NotBlank(message = "Reference Invoice No is mandatory")
    @Column(name = "ReferenceInvoiceNo", columnDefinition = "nvarchar(50)")
    private String referenceInvoiceNo;

    @Column(name = "Sourcebranchcode", columnDefinition = "nvarchar(50)")
    private String sourceBranchCode;

    @Column(name = "SupplierPartNo", columnDefinition = "nvarchar(50)")
    private String supplierPartNo;

    @NotBlank(message = "Manufacturer Short Name is mandatory")
    @Column(name = "ManufacturershortName", columnDefinition = "nvarchar(200)")
    private String manufacturerShortName;

    @NotBlank(message = "Return Order Date is mandatory")
    @Column(name = "ReturnorderDate")
    private String returnOrderDate;

    @NotNull(message = "Return Qty is mandatory")
    @Column(name = "Returnqty")
    private Double returnQty;

    @NotBlank(message = "UOM is mandatory")
    @Column(name = "UnitofMeasure", columnDefinition = "nvarchar(50)")
    private String unitOfMeasure;

    @Column(name = "NoofPacks")
    private Long noOfPacks;

    @Column(name = "CountryofOrigin", columnDefinition = "nvarchar(250)")
    private String countryOfOrigin;

    @NotBlank(message = "Manufacturer Code is mandatory")
    @Column(name = "ManufacturerCode", columnDefinition = "nvarchar(200)")
    private String manufacturerCode;

    @Column(name = "Manufacturerfullname", columnDefinition = "nvarchar(250)")
    private String manufacturerFullName;

    @Column(name = "IS_COMPLETED", columnDefinition = "nvarchar(10)")
    private String isCompleted;

    @Column(name = "IS_CANCELLED", columnDefinition = "nvarchar(10)")
    private String isCancelled;
}