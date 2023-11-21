package com.almailem.ams.api.connector.model.stockAdjustment;

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
    @Column(name = "COMPANY_CODE", columnDefinition = "nvarchar(50)")
    private String companyCode;

    @NotBlank(message = "Stock Count Branch Code is mandatory")
    @Column(name = "STOCK_COUNT_BRANCH_CODE", columnDefinition = "nvarchar(50)")
    private String branchCode;

    @Column(name = "BRANCH_NAME", columnDefinition = "nvarchar(500)")
    private String branchName;

    @NotBlank(message = "Date of Adjustment is mandatory")
    private Date dateOfAdjustment;

    @Column(name = "IS_CYCLECOUNT", columnDefinition = "nvarchar(10)")
    private String isCycleCount;

    @Column(name = "IS_DAMAGE", columnDefinition = "nvarchar(10)")
    private String isDamage;

    @NotBlank(message = "Item Code is mandatory")
    @Column(name = "ITEM_CODE", columnDefinition = "nvarchar(50)")
    private String itemCode;

    @Column(name = "ITEM_DESCRIPTION", columnDefinition = "nvarchar(500)")
    private String itemDescription;

    @NotNull(message = "Adjustment Qty is mandatory")
    private Double adjustmentQty;

    @NotBlank(message = "Unit of Measure is mandatory")
    @Column(name = "UNIT_OF_MEASURE", columnDefinition = "nvarchar(50)")
    private String unitOfMeasure;

    @NotBlank(message = "Manufacturer Code is mandatory")
    @Column(name = "MANUFACTURER_CODE", columnDefinition = "nvarchar(200)")
    private String manufacturerCode;

    @NotBlank(message = "Manufacturer Name is mandatory")
    @Column(name = "MANUFACTURER_NAME", columnDefinition = "nvarchar(200)")
    private String manufacturerName;

    @Column(name = "REMARKS", columnDefinition = "nvarchar(250)")
    private String remarks;

    @Column(name = "AMS_REFERENCE_NO", columnDefinition = "nvarchar(50)")
    private String amsReferenceNo;

    @Column(name = "IS_COMPLETED", columnDefinition = "nvarchar(10)")
    private String isCompleted;

    private Date updatedOn;

    //ProcessedStatusIdOrderByOrderReceivedOn
    @NotNull
    private Long processedStatusId = 0L;

    private Date orderReceivedOn;
    private Date orderProcessedOn;
}
