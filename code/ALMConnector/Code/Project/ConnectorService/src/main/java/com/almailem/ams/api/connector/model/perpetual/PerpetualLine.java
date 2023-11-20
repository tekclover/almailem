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
@Table(name = "tblperpetualline")
public class PerpetualLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long perpetualLineId;

    private Long perpetualHeaderId;

    @NotBlank(message = "CycleCount Number is mandatory")
    private String cycleCountNumber;

    @NotNull(message = "line Number Of Each Item Code is mandatory")
    private Long lineNoOfEachItemCode;

    @NotBlank(message = "itemCode is mandatory")
    private String itemCode;

    private String itemDescription;

    @NotBlank(message = "Unit Of Measure is mandatory")
    private String uom;

    @NotBlank(message = "Manufacturer Code is mandatory")
    private String manufacturerCode;

    @NotBlank(message = "Manufacturer Name is mandatory")
    private String manufacturerName;
}
