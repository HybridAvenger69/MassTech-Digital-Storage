package com.hybridavenger69.mtstorage.apiimpl.storage;

public enum ItemStorageType {
    ONE_K("1k", 1000),
    FOUR_K("4k", 4000),
    SIXTEEN_K("16k", 16_000),
    SIXTY_FOUR_K("64k", 64_000),
    ONE_TWENTY_EIGHT_K("128k", 128_000),
    TWO_FIFTY_SIX_K("256k", 256_000),
    FIVE_TWELVE_K("512k", 512_000),
    ONE_ZERO_TWENTY_FOUR_K("1024k", 1024_000),
    TWO_ZERO_FOUR_EIGHT_K("2048k" ,2048_000),
    CREATIVE("creative", -1);

    private final String name;
    private final int capacity;

    ItemStorageType(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }
}
