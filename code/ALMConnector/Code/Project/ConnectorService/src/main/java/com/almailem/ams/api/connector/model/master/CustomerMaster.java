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
@Table(name = "tblcustomermaster")
public class CustomerMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerMasterId;

    @NotBlank(message = "Company Code is mandatory")
    @Column(name = "COMPANY_CODE", columnDefinition = "nvarchar(50)")
    private String companyCode;

    @Column(name = "BRANCH_CODE", columnDefinition = "nvarchar(50)")
    private String branchCode;

    @NotBlank(message = "Customer Code is mandatory")
    @Column(name = "CUSTOMER_CODE", columnDefinition = "nvarchar(50)")
    private String customerCode;

    @NotBlank(message = "Customer Name is mandatory")
    @Column(name = "CUSTOMER_NAME", columnDefinition = "nvarchar(50)")
    private String customerName;

    @NotBlank(message = "HomeAddress1 is mandatory")
    @Column(name = "HOME_ADDRESS_1", columnDefinition = "nvarchar(3999)")
    private String homeAddress1;

    @Column(name = "HOME_ADDRESS_2", columnDefinition = "nvarchar(3999)")
    private String homeAddress2;

    @Column(name = "HOME_TEL_NUMBER", columnDefinition = "nvarchar(500)")
    private String homeTelNumber;

    @Column(name = "CIVIL_ID_NUMBER", columnDefinition = "nvarchar(200)")
    private String civilIdNumber;

    @Column(name = "MOBILE_NUMBER", columnDefinition = "nvarchar(200)")
    private String mobileNumber;

    @NotBlank(message = "Created Username is mandatory")
    @Column(name = "CREATED_USER_NAME", columnDefinition = "nvarchar(50)")
    private String createdUsername;

    @NotBlank(message = "Customer Creation Date is mandatory")
    private Date customerCreationDate;

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
