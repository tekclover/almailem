package com.almailem.ams.api.connector.model.picklist;

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
@Table(name = "tblpicklistheader")
public class PickListHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pickListHeaderId;

    @NotBlank(message = "Company Code is mandatory")
    @Column(name = "COMPANY_CODE", columnDefinition = "nvarchar(50)")
    private String companyCode;

    @NotBlank(message = "Branch Code is mandatory")
    @Column(name = "BRANCH_CODE", columnDefinition = "nvarchar(50)")
    private String branchCode;

    @NotBlank(message = "Sales Order No is mandatory")
    @Column(name = "SALES_ORDER_NO", columnDefinition = "nvarchar(50)")
    private String salesOrderNo;

    @NotBlank(message = "Pick List No is mandatory")
    @Column(name = "PICK_LIST_NO", columnDefinition = "nvarchar(50)")
    private String pickListNo;

    @NotBlank(message = "Pick List Date is mandatory")
    private Date pickListdate;

    @Column(name = "IS_COMPLETED", columnDefinition = "nvarchar(10)")
    private String isCompleted;

    private Date updatedOn;

    @Column(name = "IS_CANCELLED", columnDefinition = "nvarchar(10)")
    private String isCancelled;

    //ProcessedStatusIdOrderByOrderReceivedOn
    @NotNull
    private Long processedStatusId = 0L;

    private Date orderReceivedOn;
    private Date orderProcessedOn;


    @OneToMany(mappedBy = "pickListHeaderId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<PickListLine> pickListLines;
}
