package com.almailem.ams.api.connector.model.transferout;

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
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbltransferoutheader")
public class TransferOutHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transferOutHeaderId;

    @NotBlank(message = "Source Company Code is mandatory")
    @Column(name = "SOURCE_COMPANY_CODE", columnDefinition = "nvarchar(50)")
    private String sourceCompanyCode;

    @NotBlank(message = "Target Company Code is mandatory")
    @Column(name = "TARGET_COMPANY_CODE", columnDefinition = "nvarchar(50)")
    private String targetCompanyCode;

    @NotBlank(message = "Transfer Order Number is mandatory")
    @Column(name = "TRANSFER_ORDER_NUMBER", columnDefinition = "nvarchar(50)")
    private String transferOrderNumber;

    @NotBlank(message = "Source Branch Code is mandatory")
    @Column(name = "SOURCE_BRANCH_CODE", columnDefinition = "nvarchar(50)")
    private String sourceBranchCode;

    @NotBlank(message = "Target Branch Code is mandatory")
    @Column(name = "TARGET_BRANCH_CODE", columnDefinition = "nvarchar(50)")
    private String targetBranchCode;

    @NotBlank(message = "Transfer Order Date is mandatory")
    private Date transferOrderDate;

    @Column(name = "FULFILMENT_METHOD", columnDefinition = "nvarchar(10)")
    private String fulfilmentMethod;

    @Column(name = "IS_COMPLETED", columnDefinition = "nvarchar(10)")
    private String isCompleted;

    private Date updatedOn;

    //ProcessedStatusIdOrderByOrderReceivedOn
    @NotNull
    private Long processedStatusId = 0L;

    private Date orderReceivedOn;
    private Date orderProcessedOn;


    @OneToMany(mappedBy = "transferOutHeaderId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<TransferOutLine> transferOutLines;
}
