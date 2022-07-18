package ru.home.inventoryaccounting.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "document_registration")
public class DocumentRegistrationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    //@Column(name = "document_header_id", nullable = false)
    @OneToOne()
    @JoinColumn(name = "document_header_id")
    private DocumentHeaderEntity documentHeaderEntity;

    public DocumentRegistrationEntity(DocumentHeaderEntity documentHeaderEntity) {
        setDocumentHeaderEntity(documentHeaderEntity);
    }

}
