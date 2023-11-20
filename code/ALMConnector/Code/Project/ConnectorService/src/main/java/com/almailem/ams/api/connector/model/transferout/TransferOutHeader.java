package com.almailem.ams.api.connector.model.transferout;

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
@Table(name = "tbltransferoutheader")
public class TransferOutHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transferOutHeaderId;

    @NotBlank(message = "SourceCompanyCode is mandatory")
    private String sourceCompanyCode;

    @NotBlank(message = "TargetCompanyCode is mandatory")
    private String targetCompanyCode;

    @NotBlank(message = "TransferOrderNo is mandatory")
    private String transferOrderNumber;

    @NotBlank(message = "SourceBranchCode is mandatory")
    private String sourceBranchCode;

    @NotBlank(message = "TargetBranchCode is mandatory")
    private String targetBranchCode;

    @NotBlank(message = "TransferOrder Date is mandatory")
    private Date transferOrderDate;

    private String fulfilmentMethod;

    @OneToMany(mappedBy = "transferOutHeaderId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<TransferOutLine> transferOutLines;
}
