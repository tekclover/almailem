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

    @NotBlank(message = "SalesOrderNumber is mandatory")
    private String salesOrderNumber;

    @NotBlank(message = "PickListNumber is mandatory")
    private String pickListNumber;

    @NotBlank(message = "Item Code is mandatory")
    private String itemCode;

    @NotBlank(message = "Item Description is mandatory")
    private String itemDescription;

    @NotNull(message = "PickList Quantity is mandatory")
    private Double pickListQty;

    @NotBlank(message = "Manufacturer Short Name is mandatory")
    private String manufacturerShortName;

    @NotBlank(message = "Manufacturer Code is mandatory")
    private String manufacturerCode;

    @NotBlank(message = "UOM is mandatory")
    private String uom;

    private String manufacturerFullName;
}
