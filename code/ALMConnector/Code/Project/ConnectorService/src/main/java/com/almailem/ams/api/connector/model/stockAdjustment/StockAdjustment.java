package com.almailem.ams.api.connector.model.stockAdjustment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tblstockadjustment")
public class StockAdjustment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockAdjustmentId;

    @NotBlank(message = "Company Code is mandatory")
    private String companyCode;

    @NotBlank(message = "Stock count Branch code is mandatory")
    private String branchCode;

    private String branchName;

    @NotBlank(message = "Date of Adjustment is mandatory")
    private Date dateofAdjustment;

    @NotBlank(message = "Is Damaged is mandatory")
    private String isDamaged;

    @NotBlank(message = "Item Code is mandatory")
    private String itemCode;

    private String itemDescription;

    @NotNull(message = "Adjustment Qty is mandatory")
    private Double adjustmentQty;

    @NotBlank(message = "UOM is mandatory")
    private String uom;

    @NotBlank(message = "Mfr Code is mandatory")
    private String mfrCode;

    private String mfrName;

    private String remarks;

}
