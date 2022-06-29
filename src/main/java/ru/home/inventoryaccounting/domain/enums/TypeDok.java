package ru.home.inventoryaccounting.domain.enums;

public enum TypeDok {
    RECEIVING(0), //Потупление товаров
    POSTING(1), // Оприходование товаров
    MOVING(2), // Перемещение товаров
    WRITE_OFF(3), //Списание товаров
    SALES(4); // Реализация товаров

//    RECEIPT_GOODS(1), //Потупление товаров
//    POSTING_OF_GOODS(2), // Оприходование товаров
//    MOVING_GOODS(3), // Перемещение товаров
//    WRITE_OFF_OF_GOODS(4), // Списание товаров
//    SALES_OF_GOODS(5); // Реализация товаров

    private final int typeDoc;

    TypeDok(int typeDoc) {
        this.typeDoc = typeDoc;
    }

    public int getTypeDoc() {
        return typeDoc;
    }
}
