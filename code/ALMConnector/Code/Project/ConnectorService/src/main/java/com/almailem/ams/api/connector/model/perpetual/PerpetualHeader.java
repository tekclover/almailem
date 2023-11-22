package com.almailem.ams.api.connector.model.perpetual;

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
@Table(name = "PERPETUALHEADER")
public class PerpetualHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PerpetualHeaderId")
    private Long perpetualHeaderId;

    @NotBlank(message = "Company Code is mandatory")
    @Column(name = "CompanyCode", columnDefinition = "nvarchar(50)")
    private String companyCode;

    @NotBlank(message = "Cycle Count No is mandatory")
    @Column(name = "CycleCountNo", columnDefinition = "nvarchar(50)")
    private String cycleCountNo;

    @NotBlank(message = "Branch Code is mandatory")
    @Column(name = "Branchcode", columnDefinition = "nvarchar(50)")
    private String branchCode;

    @Column(name = "BranchName", columnDefinition = "nvarchar(500)")
    private String branchName;

    @NotBlank(message = "Cycle Count Creation Date is mandatory")
    @Column(name = "Cyclecountcreationdate")
    private Date cycleCountCreationDate;

    @Column(name = "IS_NEW", columnDefinition = "nvarchar(20)")
    private String isNew;

    @Column(name = "IS_CANCELLED", columnDefinition = "nvarchar(20)")
    private String isCancelled;

    @Column(name = "IS_COMPLETED", columnDefinition = "nvarchar(10)")
    private String isCompleted;

    @Column(name = "Updatedon")
    private Date updatedOn;

    //ProcessedStatusIdOrderByOrderReceivedOn
    private Long processedStatusId = 0L;
    private Date orderReceivedOn;
    private Date orderProcessedOn;


    @OneToMany(mappedBy = "perpetualHeaderId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<PerpetualLine> perpetualLines;
}
