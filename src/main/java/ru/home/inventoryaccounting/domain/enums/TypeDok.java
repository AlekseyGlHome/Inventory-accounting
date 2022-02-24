package ru.home.inventoryaccounting.domain.enums;

public enum TypeDok {
    GOODS_RECEIPT(1), //Потупление товаров
    CAPITALIZATION_OF_GOODS(2), // Оприходование товаров
    MOVING_GOODS(3), // Перемещение товаров
    GOODS_WRITE_OFF(4), // Списание товаров
    SALES_OF_GOODS(5); // Реализация товаров

    private int typeDoc;

    TypeDok(int typeDoc) {
        this.typeDoc = typeDoc;
    }

    public int getTypeDoc() {
        return typeDoc;
    }
}
