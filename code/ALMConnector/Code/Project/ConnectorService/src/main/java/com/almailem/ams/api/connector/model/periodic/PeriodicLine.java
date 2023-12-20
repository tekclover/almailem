package com.almailem.ams.api.connector.model.periodic;

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
@Table(name = "PERIODICLINE")
public class PeriodicLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PeriodicLineId")
    private Long periodicLineId;

    @Column(name = "PeriodicHeaderId")
    private Long periodicHeaderId;

    @NotBlank(message = "Cycle Count No is mandatory")
    @Column(name = "CycleCountNo", columnDefinition = "nvarchar(50)")
    private String cycleCountNo;

    @NotNull(message = "line NO Of Each Item Code is mandatory")
    @Column(name = "Linenoofeachitemcode")
    private Long lineNoOfEachItemCode;

    @NotBlank(message = "Item Code is mandatory")
    @Column(name = "Itemcode", columnDefinition = "nvarchar(50)")
    private String itemCode;

    @Column(name = "Itemdescription", columnDefinition = "nvarchar(500)")
    private String itemDescription;

    @NotBlank(message = "Unit of Measure is mandatory")
    @Column(name = "UnitofMeasure", columnDefinition = "nvarchar(50)")
    private String unitOfMeasure;

    @NotBlank(message = "Manufacturer Code is mandatory")
    @Column(name = "ManufacturerCode", columnDefinition = "nvarchar(200)")
    private String manufacturerCode;

    @NotBlank(message = "Manufacturer Name is mandatory")
    @Column(name = "ManufacturerName", columnDefinition = "nvarchar(200)")
    private String manufacturerName;

    @NotNull(message = "FrozenQty is mandatory")
    @Column(name = "FrozenQty")
    private Double frozenQty;

    @Column(name = "IS_COMPLETED", columnDefinition = "nvarchar(10)")
    private String isCompleted;

    @Column(name = "IS_CANCELLED", columnDefinition = "nvarchar(20)")
    private String isCancelled;

    @Column(name = "CountedQty")
    private Double countedQty;
}
