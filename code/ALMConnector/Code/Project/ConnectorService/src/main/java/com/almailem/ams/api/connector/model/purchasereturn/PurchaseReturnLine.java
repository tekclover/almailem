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
@Table(name = "tblpurchasereturnline")
public class PurchaseReturnLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchaseReturnLineId;

    private Long purchaseReturnHeaderId;

    @NotBlank(message = "Return Order No is mandatory")
    @Column(name = "RETURN_ORDER_NO", columnDefinition = "nvarchar(50)")
    private String returnOrderNo;

    @NotNull(message = "Line No 0f Each Item Code is mandatory")
    private Long lineNoOfEachItemCode;

    @NotBlank(message = "Item Code is mandatory")
    @Column(name = "ITEM_CODE", columnDefinition = "nvarchar(50)")
    private String itemCode;

    @Column(name = "ITEM_DESCRIPTION", columnDefinition = "nvarchar(500)")
    private String itemDescription;

    @NotNull(message = "Return Order Qty is mandatory")
    private Double returnOrderQty;

    @NotBlank(message = "UOM is mandatory")
    @Column(name = "UNIT_OF_MEASURE", columnDefinition = "nvarchar(50)")
    private String unitOfMeasure;

    @NotBlank(message = "Manufacturer Code is mandatory")
    @Column(name = "MANUFACTURER_CODE", columnDefinition = "nvarchar(200)")
    private String manufacturerCode;

    @NotBlank(message = "Manufacturer Short Name is mandatory")
    @Column(name = "MANUFACTURER_SHORT_NAME", columnDefinition = "nvarchar(200)")
    private String manufacturerShortName;

    @Column(name = "MANUFACTURER_FULL_NAME", columnDefinition = "nvarchar(250)")
    private String manufacturerFullName;

    @Column(name = "IS_COMPLETED", columnDefinition = "nvarchar(10)")
    private String isCompleted;

    @Column(name = "IS_CANCELLED", columnDefinition = "nvarchar(10)")
    private String isCancelled;
}
