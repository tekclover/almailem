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
@Table(name = "PURCHASERETURNHEADER")
public class PurchaseReturnHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PurchaseReturnHeaderId")
    private Long purchaseReturnHeaderId;

    @NotBlank(message = "Company Code is mandatory")
    @Column(name = "CompanyCode", columnDefinition = "nvarchar(50)")
    private String companyCode;

    @NotBlank(message = "Branch Code is mandatory")
    @Column(name = "Branchcode", columnDefinition = "nvarchar(50)")
    private String branchCode;

    @NotBlank(message = "Return Order No is mandatory")
    @Column(name = "ReturnorderNo", columnDefinition = "nvarchar(50)")
    private String returnOrderNo;

    @NotBlank(message = "Return Order Date is mandatory")
    @Column(name = "Returnorderdate")
    private Date returnOrderDate;

    @Column(name = "SupplierInvoiceNo", columnDefinition = "nvarchar(50)")
    private String supplierInvoiceNo;

    @Column(name = "IS_COMPLETED", columnDefinition = "nvarchar(10)")
    private String isCompleted;

    @Column(name = "Updatedon")
    private Date updatedOn;

    @Column(name = "IS_CANCELLED", columnDefinition = "nvarchar(10)")
    private String isCancelled;

    //ProcessedStatusIdOrderByOrderReceivedOn
    @Column(name = "processedStatusId", columnDefinition = "bigint default'0'")
    private Long processedStatusId = 0L;
    @Column(name = "orderReceivedOn", columnDefinition = "datetime2 default getdate()")
    private Date orderReceivedOn;
    private Date orderProcessedOn;


    @OneToMany(mappedBy = "purchaseReturnHeaderId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<PurchaseReturnLine> purchaseReturnLines;
}