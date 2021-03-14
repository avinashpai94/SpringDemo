package com.example.demo.models.pet;

public enum PetType {
    DOG (1),
    CAT(2),
    BIRD(3),
    FISH(4),
    REPTILE(5),
    OTHER(6);

    private final Integer value;

    PetType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static boolean contains(Integer type) {
        for (PetType p : PetType.values()) {
            if (p.getValue().equals(type)) {
                return true;
            }
        }
        return false;
    }

}