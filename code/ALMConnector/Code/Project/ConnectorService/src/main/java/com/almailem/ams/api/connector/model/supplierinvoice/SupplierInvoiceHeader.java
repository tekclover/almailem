package com.almailem.ams.api.connector.model.supplierinvoice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tblsupplierinvoiceheader")
public class SupplierInvoiceHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplierInvoiceHeaderId;

    @NotBlank(message = "Company Code is mandatory")
    @Column(name = "COMPANY_CODE", columnDefinition = "nvarchar(25)")
    private String companyCode;

    @NotBlank(message = "Branch Code is mandatory")
    @Column(name = "BRANCH_CODE", columnDefinition = "nvarchar(25)")
    private String branchCode;

    @NotBlank(message = "Purchase Order No is mandatory")
    @Column(name = "PURCHASE_ORDER_NO", columnDefinition = "nvarchar(50)")
    private String purchaseOrderNo;

    @NotBlank(message = "Supplier Invoice No is mandatory")
    @Column(name = "SUPPLIER_INVOICE_NO", columnDefinition = "nvarchar(50)")
    private String supplierInvoiceNo;

    @OneToMany(mappedBy = "supplierInvoiceHeaderId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<SupplierInvoiceLine> supplierInvoiceLines;
    
    //ProcessedStatusIdOrderByOrderReceivedOn
    
    @NotNull
    private Long processedStatusId = 0L;
    
    private Date orderReceivedOn;
    private Date orderProcessedOn;
}
