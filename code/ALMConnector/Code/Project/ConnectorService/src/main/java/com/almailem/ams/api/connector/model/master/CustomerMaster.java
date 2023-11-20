package com.almailem.ams.api.connector.model.master;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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

    @NotBlank(message = "CompanyCode is mandatory")
    private String companyCode;

    private String branchCode;

    @NotBlank(message = "Customer Code is mandatory")
    private String customerCode;

    @NotBlank(message = "Customer Name is mandatory")
    private String customerName;

    @NotBlank(message = "HomeAddress1 is mandatory")
    private String homeAddress1;

    private String homeAddress2;

    private String homeTelNumber;

    private String civilIdNumber;

    private String mobileNumber;

    @NotBlank(message = "Created Username is mandatory")
    private String createdUsername;

    @NotBlank(message = "Item Creation Date is mandatory")
    private Date itemCreationDate;

    @NotBlank(message = "Is_New is mandatory")
    private String isNew;
}
