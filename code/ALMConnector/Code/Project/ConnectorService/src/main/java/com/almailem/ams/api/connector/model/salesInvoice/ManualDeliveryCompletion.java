package com.almailem.ams.api.connector.model.salesinvoice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "DELIVERY")
public class ManualDeliveryCompletion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DeliveryId")
    private Long deliveryId;

    @NotBlank(message = "Company Code is mandatory")
    @Column(name = "companyCode", columnDefinition = "nvarchar(50)")
    private String companyCode;

    @NotBlank(message = "Branch Code is mandatory")
    @Column(name = "branchCode", columnDefinition = "nvarchar(50)")
    private String branchCode;

    @NotBlank(message = "Sales Invoice Number is mandatory")
    @Column(name = "salesInvoiceNumber", columnDefinition = "nvarchar(50)")
    private String salesInvoiceNumber;

    @NotBlank(message = "Delivery Type is mandatory")
    @Column(name = "Deliverytype", columnDefinition = "nvarchar(50)")
    private String deliveryType;

    @NotBlank(message = "Sales Order Number is mandatory")
    @Column(name = "salesOrderNumber", columnDefinition = "nvarchar(50)")
    private String salesOrderNumber;

    @NotBlank(message = "Pick List Number is mandatory")
    @Column(name = "pickListNumber", columnDefinition = "nvarchar(50)")
    private String pickListNumber;

    @Column(name = "IS_COMPLETED", columnDefinition = "nvarchar(10)")
    private String isCompleted;

    @Column(name = "Updatedon")
    private Date updatedOn;

    @Column(name = "IS_UNDELIVERED", columnDefinition = "nvarchar(10)")
    private String isUndelivered;

    //ProcessedStatusIdOrderByOrderReceivedOn
    @Column(name = "processedStatusId", columnDefinition = "bigint default'0'")
    private Long processedStatusId = 0L;
    @Column(name = "orderReceivedOn", columnDefinition = "datetime2 default getdate()")
    private Date orderReceivedOn;
    private Date orderProcessedOn;
}