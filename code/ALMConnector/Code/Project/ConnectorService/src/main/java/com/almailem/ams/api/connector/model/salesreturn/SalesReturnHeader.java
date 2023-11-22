package com.almailem.ams.api.connector.model.salesreturn;

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
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SALESRETURNHEADER")
public class SalesReturnHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SalesReturnHeaderId")
    private Long salesReturnHeaderId;

    @NotBlank(message = "Company Code is mandatory")
    @Column(name = "CompanyCode", columnDefinition = "nvarchar(25)")
    private String companyCode;

//    @NotBlank(message = "Branch Code of Receiving Warehouse is mandatory")
    @Column(name = "BranchcodeofreceivingWarehouse", columnDefinition = "nvarchar(25)")
    private String branchCodeOfReceivingWarehouse;

    @Column(name = "Branchcode", columnDefinition = "nvarchar(25)")
    private String branchCode;

    @NotBlank(message = "Return Order No is mandatory")
    @Column(name = "ReturnOrderNo", columnDefinition = "nvarchar(50)")
    private String returnOrderNo;

    @Column(name = "IS_COMPLETED", columnDefinition = "nvarchar(10)")
    private String isCompleted;

    @Column(name = "Updatedon")
    private Date updatedOn;

    @Column(name = "IS_CANCELLED", columnDefinition = "nvarchar(10)")
    private String isCancelled;

    //ProcessedStatusIdOrderByOrderReceivedOn
    private Long processedStatusId = 0L;
    private Date orderReceivedOn;
    private Date orderProcessedOn;

    @OneToMany(mappedBy = "salesReturnHeaderId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<SalesReturnLine> salesReturnLines;
}
