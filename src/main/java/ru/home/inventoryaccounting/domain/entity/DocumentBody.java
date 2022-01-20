package ru.home.inventoryaccounting.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "documents_body")
public class DocumentBody {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "amount", precision = 15, scale = 4)
    private BigDecimal amount;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;

    @Column(name = "price", precision = 15, scale = 4)
    private BigDecimal price;

    @Column(name = "quantity", precision = 15, scale = 2)
    private BigDecimal quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_header_id")
    private DocumentHeader documentHeader;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_document_id")
    private DocumentHeader receiptDocument;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "serial_document_body_id")
    private DocumentBody serialDocumentBody;



}