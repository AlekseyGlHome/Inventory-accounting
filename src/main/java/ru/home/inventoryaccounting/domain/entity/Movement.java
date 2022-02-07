package ru.home.inventoryaccounting.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "movements")
public class Movement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "amount", precision = 15, scale = 4)
    private BigDecimal amount;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "price", precision = 15, scale = 4)
    private BigDecimal price;

    @Column(name = "quantity", precision = 15, scale = 2)
    private BigDecimal quantity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "document_header_id", nullable = false)
    private DocumentHeaderEntity documentHeader;

    @ManyToOne(optional = false)
    @JoinColumn(name = "inventory_id", nullable = false)
    private InventoryEntity inventory;

    @ManyToOne
    @JoinColumn(name = "receipt_document_id")
    private DocumentHeaderEntity receiptDocument;

    @ManyToOne
    @JoinColumn(name = "serial_docu_body_id")
    private DocumentBodyEntity serialDocuBody;

    @ManyToOne(optional = false)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private WarehouseEntity warehouse;

    @ManyToOne(optional = false)
    @JoinColumn(name = "serial_document_body_id", nullable = false)
    private DocumentBodyEntity serialDocumentBody;
}