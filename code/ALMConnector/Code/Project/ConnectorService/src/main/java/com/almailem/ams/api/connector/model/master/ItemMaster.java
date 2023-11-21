package com.almailem.ams.api.connector.model.master;

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
@Table(name = "tblitemmaster")
public class ItemMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemMasterId;

    @NotBlank(message = "Company Code is mandatory")
    @Column(name = "COMPANY_CODE", columnDefinition = "nvarchar(50)")
    private String companyCode;

    @NotBlank(message = "Branch Code is mandatory")
    @Column(name = "BRANCH_CODE", columnDefinition = "nvarchar(50)")
    private String branchCode;

    @NotBlank(message = "Item Code is mandatory")
    @Column(name = "ITEM_CODE", columnDefinition = "nvarchar(50)")
    private String itemCode;

    @NotBlank(message = "Item Description is mandatory")
    @Column(name = "ITEM_DESCRIPTION", columnDefinition = "nvarchar(500)")
    private String itemDescription;

    @NotBlank(message = "Unit of Measure is mandatory")
    @Column(name = "UNIT_OF_MEASURE", columnDefinition = "nvarchar(50)")
    private String unitOfMeasure;

    private Long itemGroupId;

    private Long secondaryItemGroupId;

    @NotBlank(message = "Manufacturer Code is mandatory")
    @Column(name = "MANUFACTURER_CODE", columnDefinition = "nvarchar(200)")
    private String manufacturerCode;

    @NotBlank(message = "Manufacturer Short Name is mandatory")
    @Column(name = "MANUFACTURER_SHORT_NAME", columnDefinition = "nvarchar(200)")
    private String manufacturerShortName;

    @Column(name = "MANUFACTURER_FULL_NAME", columnDefinition = "nvarchar(250)")
    private String manufacturerFullName;

    @NotBlank(message = "Created Username is mandatory")
    @Column(name = "CREATED_USER_NAME", columnDefinition = "nvarchar(50)")
    private String createdUsername;

    @NotBlank(message = "Item Creation Date is mandatory")
    private Date itemCreationDate;

    @Column(name = "IS_NEW", columnDefinition = "nvarchar(20)")
    private String isNew;

    @Column(name = "IS_UPDATE", columnDefinition = "nvarchar(20)")
    private String isUpdate;

    @Column(name = "IS_COMPLETED", columnDefinition = "nvarchar(10)")
    private String isCompleted;

    private Date updatedOn;

    //ProcessedStatusIdOrderByOrderReceivedOn
    @NotNull
    private Long processedStatusId = 0L;

    private Date orderReceivedOn;
    private Date orderProcessedOn;
}
