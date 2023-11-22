package com.almailem.ams.api.connector.model.supplierinvoice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SUPPLIERINVOICEHEADER")
public class SupplierInvoiceHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SupplierInvoiceHeaderId")
    private Long supplierInvoiceHeaderId;

    @NotBlank(message = "Company Code is mandatory")
    @Column(name = "CompanyCode", columnDefinition = "nvarchar(25)")
    private String companyCode;

    @NotBlank(message = "Branch Code is mandatory")
    @Column(name = "Branchcode", columnDefinition = "nvarchar(25)")
    private String branchCode;

    @NotBlank(message = "Purchase Order No is mandatory")
    @Column(name = "PurchaseorderNo", columnDefinition = "nvarchar(50)")
    private String purchaseOrderNo;

    @NotBlank(message = "Supplier Invoice No is mandatory")
    @Column(name = "SupplierInvoiceNo", columnDefinition = "nvarchar(50)")
    private String supplierInvoiceNo;

    @Column(name = "IS_COMPLETED", columnDefinition = "nvarchar(10)")
    private String isCompleted;

    @Column(name = "Updatedon")
    private Date updatedOn;

    @Column(name = "IS_CANCELLED", columnDefinition = "nvarchar(10)")
    private String isCancelled;

    //ProcessedStatusIdOrderByOrderReceivedOn
    private Long processedStatusId = 0L;
    private Date orderReceivedOn;
    private Date orderProcessedOn;


    @OneToMany(mappedBy = "supplierInvoiceHeaderId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<SupplierInvoiceLine> supplierInvoiceLines;
}
