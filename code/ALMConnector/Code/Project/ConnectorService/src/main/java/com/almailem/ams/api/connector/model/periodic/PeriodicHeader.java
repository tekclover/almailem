package com.almailem.ams.api.connector.model.periodic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
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
@Table(name = "tblperiodicheader")
public class PeriodicHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long periodicHeaderId;

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


    @OneToMany(mappedBy = "periodicHeaderId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<PeriodicLine> periodicLines;
}
