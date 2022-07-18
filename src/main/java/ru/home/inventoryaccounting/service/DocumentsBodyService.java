package ru.home.inventoryaccounting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.home.inventoryaccounting.domain.entity.DocumentBodyEntity;
import ru.home.inventoryaccounting.repository.DocumentBodyRepository;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class DocumentsBodyService {


    private final DocumentBodyRepository documentBodyRepository;


    public Collection<DocumentBodyEntity> save(Collection<DocumentBodyEntity> documentBodyEntitys) {
        return documentBodyRepository.saveAll(documentBodyEntitys);
    }

    public void delete(Collection<DocumentBodyEntity> bodyEntitys) {
        documentBodyRepository.deleteAll(bodyEntitys);
    }

}
