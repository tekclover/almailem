package com.almailem.ams.api.connector.model.purchasereturn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @NotBlank(message = "Company Code is mandatory")
    @Column(name = "COMPANY_CODE", columnDefinition = "nvarchar(50)")
    private String companyCode;

    @NotBlank(message = "Branch Code is mandatory")
    @Column(name = "BRANCH_CODE", columnDefinition = "nvarchar(50)")
    private String branchCode;

    @NotBlank(message = "Return Order No is mandatory")
    @Column(name = "RETURN_ORDER_NO", columnDefinition = "nvarchar(50)")
    private String returnOrderNo;

    @NotBlank(message = "Return Order Date is mandatory")
    private Date returnOrderDate;

    @Column(name = "SUPPLIER_INVOICE_NO", columnDefinition = "nvarchar(50)")
    private String supplierInvoiceNo;

    @Column(name = "IS_COMPLETED", columnDefinition = "nvarchar(10)")
    private String isCompleted;

    private Date updatedOn;

    @Column(name = "IS_CANCELLED", columnDefinition = "nvarchar(10)")
    private String isCancelled;

    //ProcessedStatusIdOrderByOrderReceivedOn
    @NotNull
    private Long processedStatusId = 0L;

    private Date orderReceivedOn;
    private Date orderProcessedOn;


    @OneToMany(mappedBy = "purchaseReturnHeaderId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<PurchaseReturnLine> purchaseReturnLines;
}
