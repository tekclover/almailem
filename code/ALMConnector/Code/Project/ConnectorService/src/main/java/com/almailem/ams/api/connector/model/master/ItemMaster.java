package com.almailem.ams.api.connector.model.master;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Table(name = "tblitemmaster")
public class ItemMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemMasterId;

    @NotBlank(message = "CompanyCode is mandatory")
    private String companyCode;

    @NotBlank(message = "BranchCode is mandatory")
    private String branchCode;

    @NotBlank(message = "Item Code is mandatory")
    private String itemCode;

    @NotBlank(message = "Item Description is mandatory")
    private String itemDescription;

    @NotBlank(message = "Unit Of Measure is mandatory")
    private String uom;

    private Long itemGroupId;

    private Long secondaryItemGroupId;

    @NotBlank(message = "Manufacturer Code is mandatory")
    private String manufacturerCode;

    @NotBlank(message = "Manufacturer Short Name is mandatory")
    private String manufacturerShortName;

    private String manufacturerFullName;

    @NotBlank(message = "Created Username is mandatory")
    private String createdUsername;

    @NotBlank(message = "Item Creation Date is mandatory")
    private Date itemCreationDate;

    @NotBlank(message = "Is_New is mandatory")
    private String isNew;
}
