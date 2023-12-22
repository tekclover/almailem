package com.almailem.ams.api.connector.model.perpetual;

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
@Table(name = "PERPETUALLINE")
public class PerpetualLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PerpetualLineId")
    private Long perpetualLineId;

    @Column(name = "PerpetualHeaderId")
    private Long perpetualHeaderId;

    @NotBlank(message = "Cycle Count No is mandatory")
    @Column(name = "CycleCountNo", columnDefinition = "nvarchar(50)", nullable = false)
    private String cycleCountNo;

    @NotNull(message = "line No Of Each Item Code is mandatory")
    @Column(name = "Linenoofeachitemcode")
    private Long lineNoOfEachItemCode;

    @NotBlank(message = "Item Code is mandatory")
    @Column(name = "Itemcode", columnDefinition = "nvarchar(50)", nullable = false)
    private String itemCode;

    @Column(name = "Itemdescription", columnDefinition = "nvarchar(500)")
    private String itemDescription;

    @NotBlank(message = "Unit of Measure is mandatory")
    @Column(name = "UnitofMeasure", columnDefinition = "nvarchar(50)", nullable = false)
    private String unitOfMeasure;

    @NotBlank(message = "Manufacturer Code is mandatory")
    @Column(name = "ManufacturerCode", columnDefinition = "nvarchar(200)", nullable = false)
    private String manufacturerCode;

    @NotBlank(message = "Manufacturer Name is mandatory")
    @Column(name = "ManufacturerName", columnDefinition = "nvarchar(200)", nullable = false)
    private String manufacturerName;

    @NotNull(message = "FrozenQty is mandatory")
    @Column(name = "FrozenQty")
    private Double frozenQty;

    @Column(name = "IS_COMPLETED", columnDefinition = "nvarchar(10)")
    private String isCompleted;

    @Column(name = "IS_CANCELLED", columnDefinition = "nvarchar(10)")
    private String isCancelled;

    @Column(name = "CountedQty")
    private Double countedQty;
}
