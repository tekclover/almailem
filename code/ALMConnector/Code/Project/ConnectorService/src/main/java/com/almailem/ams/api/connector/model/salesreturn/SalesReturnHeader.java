package com.almailem.ams.api.connector.model.salesreturn;

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
@Table(name = "tblsalesreturnheader")
public class SalesReturnHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long salesReturnHeaderId;

    @NotBlank(message = "Company Code is mandatory")
    @Column(name = "COMPANY_CODE", columnDefinition = "nvarchar(25)")
    private String companyCode;

    @NotBlank(message = "Branch Code is mandatory")
    @Column(name = "BRANCH_CODE_OF_RECEIVING_WAREHOUSE", columnDefinition = "nvarchar(25)")
    private String branchCodeOfReceivingWarehouse;

    @NotBlank(message = "Return Order No is mandatory")
    @Column(name = "RETURN_ORDER_NO", columnDefinition = "nvarchar(50)")
    private String returnOrderNo;

    //ProcessedStatusIdOrderByOrderReceivedOn

    @NotNull
    private Long processedStatusId = 0L;

    private Date orderReceivedOn;
    private Date orderProcessedOn;

    @OneToMany(mappedBy = "salesReturnHeaderId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<SalesReturnLine> salesReturnLines;
}
