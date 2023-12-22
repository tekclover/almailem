package com.almailem.ams.api.connector.model.picklist;

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
@Table(name = "PICKLISTHEADER")
public class PickListHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PickListHeaderId")
    private Long pickListHeaderId;

    @NotBlank(message = "Company Code is mandatory")
    @Column(name = "CompanyCode", columnDefinition = "nvarchar(50)", nullable = false)
    private String companyCode;

    @NotBlank(message = "Branch Code is mandatory")
    @Column(name = "Branchcode", columnDefinition = "nvarchar(50)", nullable = false)
    private String branchCode;

    @NotBlank(message = "Sales Order No is mandatory")
    @Column(name = "SalesorderNo", columnDefinition = "nvarchar(50)", nullable = false)
    private String salesOrderNo;

    @NotBlank(message = "Pick List No is mandatory")
    @Column(name = "PickListNo", columnDefinition = "nvarchar(50)", nullable = false)
    private String pickListNo;

    @NotNull(message = "Pick List Date is mandatory")
    @Column(name = "PickListdate")
    private Date pickListdate;

    @Column(name = "IS_COMPLETED", columnDefinition = "nvarchar(10)")
    private String isCompleted;

    @Column(name = "Updatedon")
    private Date updatedOn;

    @Column(name = "IS_CANCELLED", columnDefinition = "nvarchar(10)")
    private String isCancelled;

    //ProcessedStatusIdOrderByOrderReceivedOn
    @Column(name = "processedStatusId", columnDefinition = "bigint default'0'")
    private Long processedStatusId = 0L;

    @Column(name = "orderReceivedOn", columnDefinition = "datetime2 default getdate()")
    private Date orderReceivedOn;

    private Date orderProcessedOn;


    @OneToMany(mappedBy = "pickListHeaderId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<PickListLine> pickListLines;
}
