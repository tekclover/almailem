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
import java.util.Date;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TRANSFEROUTHEADER")
public class TransferOutHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TransferOutHeaderId")
    private Long transferOutHeaderId;

    @NotBlank(message = "Source Company Code is mandatory")
    @Column(name = "SourceCompanyCode", columnDefinition = "nvarchar(50)")
    private String sourceCompanyCode;

    @NotBlank(message = "Target Company Code is mandatory")
    @Column(name = "TargetCompanyCode", columnDefinition = "nvarchar(50)")
    private String targetCompanyCode;

    @NotBlank(message = "Transfer Order Number is mandatory")
    @Column(name = "TransferOrdernumber", columnDefinition = "nvarchar(50)")
    private String transferOrderNumber;

    @NotBlank(message = "Source Branch Code is mandatory")
    @Column(name = "Sourcebranchcode", columnDefinition = "nvarchar(50)")
    private String sourceBranchCode;

    @NotBlank(message = "Target Branch Code is mandatory")
    @Column(name = "TargetbranchCode", columnDefinition = "nvarchar(50)")
    private String targetBranchCode;

    @NotBlank(message = "Transfer Order Date is mandatory")
    @Column(name = "TransferOrderdate")
    private Date transferOrderDate;

    @Column(name = "FulfilmentMethod", columnDefinition = "nvarchar(10)")
    private String fulfilmentMethod;

    @Column(name = "IS_COMPLETED", columnDefinition = "nvarchar(10)")
    private String isCompleted;

    @Column(name = "Updatedon")
    private Date updatedOn;

    //ProcessedStatusIdOrderByOrderReceivedOn
    private Long processedStatusId = 0L;
    private Date orderReceivedOn;
    private Date orderProcessedOn;


    @OneToMany(mappedBy = "transferOutHeaderId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<TransferOutLine> transferOutLines;
}
