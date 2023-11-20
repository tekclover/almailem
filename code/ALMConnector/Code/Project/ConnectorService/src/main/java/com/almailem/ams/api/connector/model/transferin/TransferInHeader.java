package com.almailem.ams.api.connector.model.transferin;

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
@Table(name = "tbltransferinheader")
public class TransferInHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transferInHeaderId;

    @NotBlank(message = "SourceCompanyCode is mandatory")
    @Column(name = "SOURCE_COMPANY_CODE", columnDefinition = "nvarchar(25)")
    private String sourceCompanyCode;

    @NotBlank(message = "TargetCompanyCode is mandatory")
    @Column(name = "TARGET_COMPANY_CODE", columnDefinition = "nvarchar(25)")
    private String targetCompanyCode;

    @NotBlank(message = "TransferOrderNo is mandatory")
    @Column(name = "TRANSFER_ORDER_NO", columnDefinition = "nvarchar(50)")
    private String transferOrderNo;

    @NotBlank(message = "SourceBranchCode is mandatory")
    @Column(name = "SOURCE_BRANCH_CODE", columnDefinition = "nvarchar(25)")
    private String sourceBranchCode;

    @NotBlank(message = "TargetBranchCode is mandatory")
    @Column(name = "TARGET_BRANCH_CODE", columnDefinition = "nvarchar(25)")
    private String targetBranchCode;

    @NotBlank(message = "TransferOrder Date is mandatory")
    private Date transferOrderDate;

    //ProcessedStatusIdOrderByOrderReceivedOn

    @NotNull
    private Long processedStatusId = 0L;

    private Date orderReceivedOn;
    private Date orderProcessedOn;

    @OneToMany(mappedBy = "transferInHeaderId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TransferInLine> transferInLines;
}
