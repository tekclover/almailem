package com.almailem.ams.api.connector.model.purchasereturn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tblpurchasereturnheader")
public class PurchaseReturnHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchaseReturnHeaderId;

    @NotBlank(message = "CompanyCode is mandatory")
    private String companyCode;

    @NotBlank(message = "BranchCode is mandatory")
    private String branchCode;

    @NotBlank(message = "ReturnOrderNumber is mandatory")
    private String returnOrderNumber;

    @NotBlank(message = "ReturnOrder Date is mandatory")
    private Date returnOrderDate;

    private String supplierInvoiceNumber;

    @OneToMany(mappedBy = "purchaseReturnHeaderId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<PurchaseReturnLine> purchaseReturnLines;
}
