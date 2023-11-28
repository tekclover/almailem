package com.almailem.ams.api.connector.model.purchasereturn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PURCHASERETURNLINE")
public class PurchaseReturnLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PurchaseReturnLineId")
    private Long purchaseReturnLineId;

    @Column(name = "PurchaseReturnHeaderId")
    private Long purchaseReturnHeaderId;

    @NotBlank(message = "Return Order No is mandatory")
    @Column(name = "ReturnorderNo", columnDefinition = "nvarchar(50)")
    private String returnOrderNo;

    @NotNull(message = "Line No 0f Each Item Code is mandatory")
    @Column(name = "Linenoofeachitemcode")
    private Long lineNoOfEachItemCode;

    @NotBlank(message = "Item Code is mandatory")
    @Column(name = "Itemcode", columnDefinition = "nvarchar(50)")
    private String itemCode;

    @Column(name = "Itemdescription", columnDefinition = "nvarchar(500)")
    private String itemDescription;

    @NotNull(message = "Return Order Qty is mandatory")
    @Column(name = "Returnorderqty")
    private Double returnOrderQty;

    @NotBlank(message = "UOM is mandatory")
    @Column(name = "UnitofMeasure", columnDefinition = "nvarchar(50)")
    private String unitOfMeasure;

    @NotBlank(message = "Manufacturer Code is mandatory")
    @Column(name = "ManufacturerCode", columnDefinition = "nvarchar(200)")
    private String manufacturerCode;

    @NotBlank(message = "Manufacturer Short Name is mandatory")
    @Column(name = "ManufacturershortName", columnDefinition = "nvarchar(200)")
    private String manufacturerShortName;

    @Column(name = "Manufacturerfullname", columnDefinition = "nvarchar(250)")
    private String manufacturerFullName;

    @Column(name = "IS_COMPLETED", columnDefinition = "nvarchar(10)")
    private String isCompleted;

    @Column(name = "IS_CANCELLED", columnDefinition = "nvarchar(10)")
    private String isCancelled;

    @Column(name = "SupplierInvoiceNo", columnDefinition = "nvarchar(50)")
    private String supplierInvoiceNo;
}
