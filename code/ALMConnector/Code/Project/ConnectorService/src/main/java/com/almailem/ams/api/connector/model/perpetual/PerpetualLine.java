package com.almailem.ams.api.connector.model.perpetual;

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
@Table(name = "tblmwperpetualline")
public class PerpetualLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long perpetualLineId;

    private Long perpetualHeaderId;

    @NotBlank(message = "Cycle Count No is mandatory")
    @Column(name = "CYCLE_COUNT_NO", columnDefinition = "nvarchar(50)")
    private String cycleCountNo;

    @NotNull(message = "line No Of Each Item Code is mandatory")
    private Long lineNoOfEachItemCode;

    @NotBlank(message = "Item Code is mandatory")
    @Column(name = "ITEM_CODE", columnDefinition = "nvarchar(50)")
    private String itemCode;

    @Column(name = "ITEM_DESCRIPTION", columnDefinition = "nvarchar(500)")
    private String itemDescription;

    @NotBlank(message = "Unit of Measure is mandatory")
    @Column(name = "UNIT_OF_MEASURE", columnDefinition = "nvarchar(50)")
    private String unitOfMeasure;

    @NotBlank(message = "Manufacturer Code is mandatory")
    @Column(name = "MANUFACTURER_CODE", columnDefinition = "nvarchar(200)")
    private String manufacturerCode;

    @NotBlank(message = "Manufacturer Name is mandatory")
    @Column(name = "MANUFACTURER_NAME", columnDefinition = "nvarchar(200)")
    private String manufacturerName;

    @Column(name = "IS_COMPLETED", columnDefinition = "nvarchar(10)")
    private String isCompleted;

    @Column(name = "IS_CANCELLED", columnDefinition = "nvarchar(10)")
    private String isCancelled;

    private Double countedQty;
}
