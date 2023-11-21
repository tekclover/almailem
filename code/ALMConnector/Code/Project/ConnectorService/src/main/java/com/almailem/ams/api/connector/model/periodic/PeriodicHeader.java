package com.almailem.ams.api.connector.model.periodic;

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
@Table(name = "tblmwperiodicheader")
public class PeriodicHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long periodicHeaderId;

    @NotBlank(message = "Company Code is mandatory")
    @Column(name = "COMPANY_CODE", columnDefinition = "nvarchar(50)")
    private String companyCode;

    @NotBlank(message = "Cycle Count No is mandatory")
    @Column(name = "CYCLE_COUNT_NO", columnDefinition = "nvarchar(50)")
    private String cycleCountNo;

    @NotBlank(message = "Branch Code is mandatory")
    @Column(name = "BRANCH_CODE", columnDefinition = "nvarchar(50)")
    private String branchCode;

    @Column(name = "BRANCH_NAME", columnDefinition = "nvarchar(500)")
    private String branchName;

    @NotBlank(message = "Cycle Count Creation Date is mandatory")
    private Date cycleCountCreationDate;

    @Column(name = "IS_NEW", columnDefinition = "nvarchar(20)")
    private String isNew;

    @Column(name = "IS_CANCELLED", columnDefinition = "nvarchar(20)")
    private String isCancelled;

    @Column(name = "IS_COMPLETED", columnDefinition = "nvarchar(10)")
    private String isCompleted;

    private Date updatedOn;

    //ProcessedStatusIdOrderByOrderReceivedOn
    @NotNull
    private Long processedStatusId = 0L;

    private Date orderReceivedOn;
    private Date orderProcessedOn;


    @OneToMany(mappedBy = "periodicHeaderId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<PeriodicLine> periodicLines;
}
