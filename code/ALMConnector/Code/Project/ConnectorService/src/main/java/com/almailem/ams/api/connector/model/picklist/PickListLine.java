package com.almailem.ams.api.connector.model.picklist;

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
@Table(name = "tblpicklistline")
public class PickListLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pickListLineId;

    private Long pickListHeaderId;

    @NotBlank(message = "Sales Order No is mandatory")
    @Column(name = "SALES_ORDER_NO", columnDefinition = "nvarchar(50)")
    private String salesOrderNo;

    @NotBlank(message = "Pick List No is mandatory")
    @Column(name = "PICK_LIST_NO", columnDefinition = "nvarchar(50)")
    private String pickListNo;

    @NotBlank(message = "Item Code is mandatory")
    @Column(name = "ITEM_CODE", columnDefinition = "nvarchar(50)")
    private String itemCode;

    @NotBlank(message = "Item Description is mandatory")
    @Column(name = "ITEM_DESCRIPTION", columnDefinition = "nvarchar(500)")
    private String itemDescription;

    @NotNull(message = "Pick List Qty is mandatory")
    private Double pickListQty;

    @NotBlank(message = "Unit of Measure is mandatory")
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

    private Double pickedQty;

    @Column(name = "IS_COMPLETED", columnDefinition = "nvarchar(10)")
    private String isCompleted;

    @Column(name = "IS_CANCELLED", columnDefinition = "nvarchar(10)")
    private String isCancelled;

    @Column(name = "IS_ALL_PICKED", columnDefinition = "nvarchar(10)")
    private String isAllPicked;
}
