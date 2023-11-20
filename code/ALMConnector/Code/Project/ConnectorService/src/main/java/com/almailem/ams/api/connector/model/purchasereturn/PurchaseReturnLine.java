package com.almailem.ams.api.connector.model.purchasereturn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Table(name = "tblpurchasereturnline")
public class PurchaseReturnLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchaseReturnLineId;

    private Long purchaseReturnHeaderId;

    @NotBlank(message = "ReturnOrder Number is mandatory")
    private String returnOrderNumber;

    @NotNull(message = "Line Number for Each Item is mandatory")
    private Long lineNoForEachItem;

    @NotBlank(message = "Item Code is mandatory")
    private String itemCode;

    private String itemDescription;

    @NotNull(message = "ReturnOrder Quantity is mandatory")
    private Double returnOrderQty;

    @NotBlank(message = "Manufacturer Short Name is mandatory")
    private String manufacturerShortName;

    @NotBlank(message = "Manufacturer Code is mandatory")
    private String manufacturerCode;

    @NotBlank(message = "UOM is mandatory")
    private String uom;

    private String manufacturerFullName;
}
