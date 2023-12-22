package com.almailem.ams.api.connector.model.perpetual;

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
@Table(name = "PERPETUALHEADER")
public class PerpetualHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PerpetualHeaderId")
    private Long perpetualHeaderId;

    @NotBlank(message = "Company Code is mandatory")
    @Column(name = "CompanyCode", columnDefinition = "nvarchar(50)", nullable = false)
    private String companyCode;

    @NotBlank(message = "Cycle Count No is mandatory")
    @Column(name = "CycleCountNo", columnDefinition = "nvarchar(50)", nullable = false)
    private String cycleCountNo;

    @NotBlank(message = "Branch Code is mandatory")
    @Column(name = "Branchcode", columnDefinition = "nvarchar(50)", nullable = false)
    private String branchCode;

    @Column(name = "BranchName", columnDefinition = "nvarchar(500)")
    private String branchName;

    @NotNull(message = "Cycle Count Creation Date is mandatory")
    @Column(name = "Cyclecountcreationdate")
    private Date cycleCountCreationDate;

    @Column(name = "IS_NEW", columnDefinition = "nvarchar(20)", nullable = false)
    private String isNew;

    @Column(name = "IS_CANCELLED", columnDefinition = "nvarchar(20)", nullable = false)
    private String isCancelled;

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


    @OneToMany(mappedBy = "perpetualHeaderId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<PerpetualLine> perpetualLines;
}
