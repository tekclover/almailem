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
@Table(name = "TRANSFERINHEADER")
public class TransferInHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TransferInHeaderId")
    private Long transferInHeaderId;

    @NotBlank(message = "SourceCompanyCode is mandatory")
    @Column(name = "SourceCompanyCode", columnDefinition = "nvarchar(25)", nullable = false)
    private String sourceCompanyCode;

    @NotBlank(message = "TargetCompanyCode is mandatory")
    @Column(name = "TargetCompanyCode", columnDefinition = "nvarchar(25)", nullable = false)
    private String targetCompanyCode;

    @NotBlank(message = "TransferOrderNo is mandatory")
    @Column(name = "TransferOrderNo", columnDefinition = "nvarchar(50)", nullable = false)
    private String transferOrderNo;

    @NotBlank(message = "SourceBranchCode is mandatory")
    @Column(name = "SourceBranchCode", columnDefinition = "nvarchar(25)", nullable = false)
    private String sourceBranchCode;

    @NotBlank(message = "TargetBranchCode is mandatory")
    @Column(name = "TargetBranchcode", columnDefinition = "nvarchar(25)", nullable = false)
    private String targetBranchCode;

    @NotNull(message = "TransferOrder Date is mandatory")
    @Column(name = "transferOrderdate")
    private Date transferOrderDate;

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

    @OneToMany(mappedBy = "transferInHeaderId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TransferInLine> transferInLines;
}