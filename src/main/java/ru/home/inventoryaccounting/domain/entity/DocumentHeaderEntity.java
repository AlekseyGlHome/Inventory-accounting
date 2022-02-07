package ru.home.inventoryaccounting.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "documents_header")
public class DocumentHeaderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "amount", precision = 15, scale = 4)
    private BigDecimal amount;

    @Column(name = "comment", length = 250)
    private String comment;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;

    @Column(name = "document_number", length = 15)
    private String documentNumber;

    @Column(name = "registered", nullable = false)
    private Boolean registered = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id")
    private PartnerEntity partner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id")
    private WarehouseEntity warehouse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_recipient_id")
    private WarehouseEntity warehouseRecipient;

    @Column(name = "type_dok")
    private Integer typeDok;

    @OneToMany(mappedBy = "documentHeader", fetch = FetchType.LAZY)
    private Collection<DocumentBodyEntity> documentBody;

    @OneToMany(mappedBy = "receiptDocument",fetch = FetchType.LAZY)
    private Collection<DocumentBodyEntity> expenseDocuments = new ArrayList<>();

}