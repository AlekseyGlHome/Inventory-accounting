package ru.home.inventoryaccounting.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.home.inventoryaccounting.api.request.DocumentBodyRequest;
import ru.home.inventoryaccounting.domain.dto.DocumentBodyDto;
import ru.home.inventoryaccounting.domain.dto.InventoryDto;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "document_body")
public class DocumentBodyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "amount", precision = 15, scale = 4)
    private BigDecimal amount;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Column(name = "price", precision = 15, scale = 4)
    private BigDecimal price;

    @Column(name = "quantity", precision = 15, scale = 2)
    private BigDecimal quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_header_id")
    private DocumentHeaderEntity documentHeader;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id")
    private InventoryEntity inventory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_id")
    private MovementEntity receiptDocument;

    //    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "serial_document_body_id")
//    private DocumentBodyEntity serialDocumentBody;

    public DocumentBodyEntity(DocumentBodyDto documentBodyDto, DocumentHeaderEntity documentHeaderEntity) {
        setId(documentBodyDto.getId());
        setAmount(documentBodyDto.getAmount());
        setIsDeleted(documentBodyDto.getIsDeleted());
        setPrice(documentBodyDto.getPrice());
        setQuantity(documentBodyDto.getQuantity());
        setDocumentHeader(documentHeaderEntity);
        setInventory(new InventoryEntity(documentBodyDto.getInventory()));
    }
    public DocumentBodyEntity(DocumentBodyRequest documentBodyRequest, InventoryEntity inventoryEntity, DocumentHeaderEntity documentHeaderEntity) {
        setId(documentBodyRequest.getId());
        setAmount(documentBodyRequest.getAmount());
        setIsDeleted(documentBodyRequest.getIsDeleted());
        setPrice(documentBodyRequest.getPrice());
        setQuantity(documentBodyRequest.getQuantity());
        setDocumentHeader(documentHeaderEntity);
        setInventory(inventoryEntity);
    }
}