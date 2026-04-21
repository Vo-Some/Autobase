package com.autobase.main.order.typeoforder;

import java.util.Scanner;

public class CargoTypeSelector {

    public static CargoType selectCargoType(Scanner sc) {
        System.out.println("Select cargo type:");
        System.out.println("1. BUILDING");
        System.out.println("2. FOOD");
        System.out.println("3. TECH");
        System.out.print("Choice (1-3): ");

        int choice = sc.nextInt();
        sc.nextLine(); // consume newline

        return switch (choice) {
            case 1 -> CargoType.BUILDING;
            case 2 -> CargoType.FOOD;
            case 3 -> CargoType.TECH;
            default -> {
                System.out.println("Invalid choice! Setting to BUILDING by default.");
                yield CargoType.BUILDING;
            }
        };
    }
}

