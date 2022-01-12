package ru.home.inventoryaccounting.domain.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
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
    private DocumentsHeader documentHeader;

    @ManyToOne(optional = false)
    @JoinColumn(name = "inventory_id", nullable = false)
    private Inventory inventory;

    @ManyToOne
    @JoinColumn(name = "receipt_document_id")
    private DocumentsHeader receiptDocument;

    @ManyToOne
    @JoinColumn(name = "serial_docu_body_id")
    private DocumentsBody serialDocuBody;

    @ManyToOne(optional = false)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @ManyToOne(optional = false)
    @JoinColumn(name = "serial_document_body_id", nullable = false)
    private DocumentsBody serialDocumentBody;

    public DocumentsBody getSerialDocumentBody() {
        return serialDocumentBody;
    }

    public void setSerialDocumentBody(DocumentsBody serialDocumentBody) {
        this.serialDocumentBody = serialDocumentBody;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public DocumentsBody getSerialDocuBody() {
        return serialDocuBody;
    }

    public void setSerialDocuBody(DocumentsBody serialDocuBody) {
        this.serialDocuBody = serialDocuBody;
    }

    public DocumentsHeader getReceiptDocument() {
        return receiptDocument;
    }

    public void setReceiptDocument(DocumentsHeader receiptDocument) {
        this.receiptDocument = receiptDocument;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public DocumentsHeader getDocumentHeader() {
        return documentHeader;
    }

    public void setDocumentHeader(DocumentsHeader documentHeader) {
        this.documentHeader = documentHeader;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}