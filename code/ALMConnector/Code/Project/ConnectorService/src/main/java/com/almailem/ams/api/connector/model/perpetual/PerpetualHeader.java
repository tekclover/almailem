package com.almailem.ams.api.connector.model.perpetual;

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
@Table(name = "tblperpetualheader")
public class PerpetualHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long perpetualHeaderId;

    @NotBlank(message = "CompanyCode is mandatory")
    private String companyCode;

    @NotBlank(message = "CycleCount Number is mandatory")
    private String cycleCountNumber;

    @NotBlank(message = "BranchCode is mandatory")
    private String branchCode;

    private String branchName;

    @NotBlank(message = "CycleCount Creation Date is mandatory")
    private Date cycleCountCreationDate;

    @NotBlank(message = "Is_New is mandatory")
    private String isNew;


    @OneToMany(mappedBy = "perpetualHeaderId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<PerpetualLine> perpetualLines;
}
