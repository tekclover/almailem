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
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ITEMMASTER")
public class ItemMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ItemMasterId")
    private Long itemMasterId;

    @NotBlank(message = "Company Code is mandatory")
    @Column(name = "CompanyCode", columnDefinition = "nvarchar(50)")
    private String companyCode;

    @NotBlank(message = "Branch Code is mandatory")
    @Column(name = "BranchCode", columnDefinition = "nvarchar(50)")
    private String branchCode;

    @NotBlank(message = "Item Code is mandatory")
    @Column(name = "Itemcode", columnDefinition = "nvarchar(50)")
    private String itemCode;

    @NotBlank(message = "Item Description is mandatory")
    @Column(name = "ItemDescription", columnDefinition = "nvarchar(500)")
    private String itemDescription;

    @NotBlank(message = "Unit of Measure is mandatory")
    @Column(name = "UnitofMeasure", columnDefinition = "nvarchar(50)")
    private String unitOfMeasure;

    @Column(name = "ItemGroupID")
    private Long itemGroupId;

    @Column(name = "SecondaryItemgroupID")
    private Long secondaryItemGroupId;

    @NotBlank(message = "Manufacturer Code is mandatory")
    @Column(name = "ManufacturerCode", columnDefinition = "nvarchar(200)")
    private String manufacturerCode;

    @NotBlank(message = "Manufacturer Short Name is mandatory")
    @Column(name = "ManufacturershortName", columnDefinition = "nvarchar(200)")
    private String manufacturerShortName;

    @Column(name = "ManufacturerFullName", columnDefinition = "nvarchar(250)")
    private String manufacturerFullName;

    @NotBlank(message = "Created Username is mandatory")
    @Column(name = "CreatedUsername", columnDefinition = "nvarchar(50)")
    private String createdUsername;

    @NotBlank(message = "Item Creation Date is mandatory")
    @Column(name = "Itemcreationdate")
    private Date itemCreationDate;

    @Column(name = "IS_NEW", columnDefinition = "nvarchar(20)")
    private String isNew;

    @Column(name = "IS_UPDATE", columnDefinition = "nvarchar(20)")
    private String isUpdate;

    @Column(name = "IS_COMPLETED", columnDefinition = "nvarchar(10)")
    private String isCompleted;

    @Column(name = "Updatedon")
    private Date updatedOn;

    //ProcessedStatusIdOrderByOrderReceivedOn
    @Column(name = "processedStatusId", columnDefinition = "bigint default'0'")
    private Long processedStatusId = 0L;
    @Column(name = "orderReceivedOn", columnDefinition = "datetime2 default getdate()")
    private Date orderReceivedOn;
    private Date orderProcessedOn;
//    private String remarks;
}
