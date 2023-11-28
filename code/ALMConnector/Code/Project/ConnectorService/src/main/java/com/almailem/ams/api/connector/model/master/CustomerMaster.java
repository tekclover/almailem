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
@Table(name = "CUSTOMERMASTER")
public class CustomerMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CustomerMasterId")
    private Long customerMasterId;

    @NotBlank(message = "Company Code is mandatory")
    @Column(name = "CompanyCode", columnDefinition = "nvarchar(50)")
    private String companyCode;

    @Column(name = "Branchcode", columnDefinition = "nvarchar(50)")
    private String branchCode;

    @NotBlank(message = "Customer Code is mandatory")
    @Column(name = "Customercode", columnDefinition = "nvarchar(50)")
    private String customerCode;

    @NotBlank(message = "Customer Name is mandatory")
    @Column(name = "CustomerName", columnDefinition = "nvarchar(50)")
    private String customerName;

    @NotBlank(message = "HomeAddress1 is mandatory")
    @Column(name = "HomeAddress1", columnDefinition = "nvarchar(3999)")
    private String homeAddress1;

    @Column(name = "HomeAddress2", columnDefinition = "nvarchar(3999)")
    private String homeAddress2;

    @Column(name = "HomeTelNumber", columnDefinition = "nvarchar(500)")
    private String homeTelNumber;

    @Column(name = "CivilIDNumber", columnDefinition = "nvarchar(200)")
    private String civilIdNumber;

    @Column(name = "MobileNumber", columnDefinition = "nvarchar(200)")
    private String mobileNumber;

    @NotBlank(message = "Created Username is mandatory")
    @Column(name = "CreatedUsername", columnDefinition = "nvarchar(50)")
    private String createdUsername;

    @NotBlank(message = "Customer Creation Date is mandatory")
    @Column(name = "Customercreationdate")
    private Date customerCreationDate;

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