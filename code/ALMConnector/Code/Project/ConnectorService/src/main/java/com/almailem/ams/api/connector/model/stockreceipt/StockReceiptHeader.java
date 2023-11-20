package com.almailem.ams.api.connector.model.stockreceipt;

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
@Table(name = "tblstockreceiptheader")
public class StockReceiptHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockReceiptHeaderId;

    @NotBlank(message = "Company Code is mandatory")
    @Column(name = "COMPANY_CODE", columnDefinition = "nvarchar(25)")
    private String companyCode;

    @NotBlank(message = "Branch Code is mandatory")
    @Column(name = "BRANCH_CODE", columnDefinition = "nvarchar(25)")
    private String branchCode;

    @NotBlank(message = "Receipt Number is mandatory")
    @Column(name = "RECEIPT_NO", columnDefinition = "nvarchar(50)")
    private String receiptNo;

    //ProcessedStatusIdOrderByOrderReceivedOn

    @NotNull
    private Long processedStatusId = 0L;

    private Date orderReceivedOn;
    private Date orderProcessedOn;

    //MiddleWare Fields
    private Long middlewareId;
    private String middlewareTable;


    @OneToMany(mappedBy = "stockReceiptHeaderId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<StockReceiptLine> stockReceiptLines;
}
