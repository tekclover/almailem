package com.almailem.ams.api.connector.model.picklist;

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
@Table(name = "tblpicklistheader")
public class PickListHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pickListHeaderId;

    @NotBlank(message = "CompanyCode is mandatory")
    private String companyCode;

    @NotBlank(message = "BranchCode is mandatory")
    private String branchCode;

    @NotBlank(message = "SalesOrderNumber is mandatory")
    private String salesOrderNumber;

    @NotBlank(message = "PickListNumber is mandatory")
    private String pickListNumber;

    @NotBlank(message = "PickListDate is mandatory")
    private Date pickListdate;

    @OneToMany(mappedBy = "pickListHeaderId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<PickListLine> pickListLines;
}
