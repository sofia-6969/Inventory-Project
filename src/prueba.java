import java.util.Scanner;

public class prueba {
    private static final int MAX_PRODUCTOS = 10;
    // Constantes para categorías
    private static final int MAX_CATEGORIAS = 5;
    
    // Nombres de categorías
    private static final String[] CATEGORIAS = {
            "Alimentos", "Bebidas", "Limpieza", "Electrónica", "Ropa"
    };

    private static String[] nombres = new String[MAX_PRODUCTOS];
    private static int[] cantidades = new int[MAX_PRODUCTOS];
    private static double[] precios = new double[MAX_PRODUCTOS];
    private static double[] valoresTotal = new double[MAX_PRODUCTOS];

    // Array para almacenar la información por categoría
    private static int[] categoriaProducto = new int[MAX_PRODUCTOS]; // Categoría de cada producto

    // Variable para llevar cuenta de cuántos productos hay en el sistema
    private static int cantidadProductos = 0;

    // Scanner para entrada de datos
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean continuar = true;

        while (continuar) {
            showMenu();
            int opcion = leerOpcion();
            switch (opcion) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    updateQuantity();
                    break;
                case 3:
                    generateReport();
                    break;
                case 4:
                    generateReportByCategory();
                    break;
                case 5:
                    continuar = false;
                    System.out.println("\nGracias por usar el sistema de inventario. ¡Hasta pronto!");
                    break;
                default:
                    System.out.println("\nOpción no válida. Por favor, intente nuevamente.");
            }
        }

        // Cerrar el scanner al finalizar
        scanner.close();
    }

    /**
     * Muestra el menú principal de la aplicación
     */
    private static void showMenu() {
        System.out.println("\n===== SISTEMA DE INVENTARIO =====");
        System.out.println("1. Agregar un nuevo producto");
        System.out.println("2. Actualizar cantidad de un producto");
        System.out.println("3. Generar reporte de inventario general");
        System.out.println("4. Generar reporte por categoría");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opción: ");
    }

    /**
     * Lee la opción seleccionada por el usuario
     */
    private static int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1; // Opción inválida
        }
    }

    /**
     * Agrega un nuevo producto al inventario
     */
    private static void addProduct() {
        if (cantidadProductos >= MAX_PRODUCTOS) {
            System.out.println("\nEl inventario está lleno. No se pueden agregar más productos.");
            return;
        }

        System.out.println("\n----- AGREGAR NUEVO PRODUCTO -----");

        System.out.print("Ingrese el nombre del producto: ");
        String nombre = scanner.nextLine();

        int cantidad = 0;
        boolean cantidadValida = false;
        while (!cantidadValida) {
            System.out.print("Ingrese la cantidad en inventario: ");
            try {
                cantidad = Integer.parseInt(scanner.nextLine());
                if (cantidad >= 0) {
                    cantidadValida = true;
                } else {
                    System.out.println("La cantidad no puede ser negativa. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
            }
        }

        double precio = 0;
        boolean precioValido = false;
        while (!precioValido) {
            System.out.print("Ingrese el precio por unidad: ");
            try {
                precio = Double.parseDouble(scanner.nextLine());
                if (precio >= 0) {
                    precioValido = true;
                } else {
                    System.out.println("El precio no puede ser negativo. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
            }
        }

        // Seleccionar categoría para el producto
        int categoria = seleccionarCategoria();

        // Guardar el nuevo producto
        nombres[cantidadProductos] = nombre;
        cantidades[cantidadProductos] = cantidad;
        precios[cantidadProductos] = precio;
        valoresTotal[cantidadProductos] = cantidad * precio;

        // Guardar la categoría del producto
        categoriaProducto[cantidadProductos] = categoria;

        System.out.println("\nProducto agregado exitosamente:");
        System.out.printf("Producto: %s - Cantidad: %d - Precio por unidad: %.2f - Valor total: %.2f\n",
                nombre, cantidad, precio, valoresTotal[cantidadProductos]);
        System.out.printf("Categoría: %s\n", CATEGORIAS[categoria]);

        cantidadProductos++;
    }

    /**
     * Muestra las categorías disponibles y solicita al usuario seleccionar una
     */
    private static int seleccionarCategoria() {
        System.out.println("\nCategorías disponibles:");
        for (int i = 0; i < CATEGORIAS.length; i++) {
            System.out.printf("%d. %s\n", (i + 1), CATEGORIAS[i]);
        }

        int categoria = -1;
        boolean seleccionValida = false;
        while (!seleccionValida) {
            System.out.print("Seleccione una categoría (1-" + CATEGORIAS.length + "): ");
            try {
                categoria = Integer.parseInt(scanner.nextLine()) - 1;
                if (categoria >= 0 && categoria < CATEGORIAS.length) {
                    seleccionValida = true;
                } else {
                    System.out.println("Selección inválida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
            }
        }
        return categoria;
    }

    /**
     * Actualiza la cantidad de un producto existente
     */
    private static void updateQuantity() {
        if (cantidadProductos == 0) {
            System.out.println("\nNo hay productos en el inventario.");
            return;
        }

        System.out.println("\n----- ACTUALIZAR CANTIDAD DE PRODUCTO -----");

        // Mostrar productos disponibles
        System.out.println("Productos disponibles:");
        for (int i = 0; i < cantidadProductos; i++) {
            System.out.printf("%d. %s (Cantidad actual: %d) - Categoría: %s\n",
                    (i + 1), nombres[i], cantidades[i],
                    CATEGORIAS[categoriaProducto[i]]);
        }

        // Seleccionar producto a actualizar
        int indice = -1;
        boolean seleccionValida = false;
        while (!seleccionValida) {
            System.out.print("\nSeleccione el número del producto a actualizar: ");
            try {
                indice = Integer.parseInt(scanner.nextLine()) - 1;
                if (indice >= 0 && indice < cantidadProductos) {
                    seleccionValida = true;
                } else {
                    System.out.println("Selección inválida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
            }
        }

        // Ingresar nueva cantidad
        int nuevaCantidad = 0;
        boolean cantidadValida = false;
        while (!cantidadValida) {
            System.out.printf("Ingrese la nueva cantidad para '%s' (cantidad actual: %d): ",
                    nombres[indice], cantidades[indice]);
            try {
                nuevaCantidad = Integer.parseInt(scanner.nextLine());
                if (nuevaCantidad >= 0) {
                    cantidadValida = true;
                } else {
                    System.out.println("La cantidad no puede ser negativa. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
            }
        }

        // Actualizar cantidad y valor total
        cantidades[indice] = nuevaCantidad;
        valoresTotal[indice] = cantidades[indice] * precios[indice];

        System.out.println("\nCantidad actualizada exitosamente:");
        System.out.printf("Producto: %s - Nueva cantidad: %d - Valor total actualizado: %.2f\n",
                nombres[indice], cantidades[indice], valoresTotal[indice]);
    }

    /**
     * Genera un reporte del inventario
     */
    private static void generateReport() {
        if (cantidadProductos == 0) {
            System.out.println("\nNo hay productos en el inventario.");
            return;
        }

        System.out.println("\n========== REPORTE DE INVENTARIO GENERAL ==========");

        double valorTotalInventario = 0;

        System.out.println("\nLista de productos:");
        System.out.println("------------------------------------------------------------------");
        System.out.printf("%-20s %-15s %-15s %-15s %-15s\n",
                "PRODUCTO", "CANTIDAD", "PRECIO UNIT.", "VALOR TOTAL", "CATEGORÍA");
        System.out.println("------------------------------------------------------------------");

        for (int i = 0; i < cantidadProductos; i++) {
            System.out.printf("%-20s %-15d $%-14.2f $%-14.2f %-15s\n",
                    nombres[i], cantidades[i], precios[i], valoresTotal[i],
                    CATEGORIAS[categoriaProducto[i]]);
            valorTotalInventario += valoresTotal[i];
        }

        System.out.println("------------------------------------------------------------------");
        System.out.printf("VALOR TOTAL DEL INVENTARIO: $%.2f\n", valorTotalInventario);
        System.out.println("------------------------------------------------------------------");
    }

    /**
     * Genera un reporte del inventario organizado por categoría
     */
    private static void generateReportByCategory() {
        if (cantidadProductos == 0) {
            System.out.println("\nNo hay productos en el inventario.");
            return;
        }

        System.out.println("\n========== REPORTE DE INVENTARIO POR CATEGORÍA ==========");

        double valorTotalInventario = 0;

        // Recorrer cada categoría
        for (int cat = 0; cat < MAX_CATEGORIAS; cat++) {
            boolean tieneProductos = false;

            // Verificar si hay productos en esta categoría
            for (int i = 0; i < cantidadProductos; i++) {
                if (categoriaProducto[i] == cat) {
                    tieneProductos = true;
                    break;
                }
            }

            if (!tieneProductos) {
                continue;  // Saltar esta categoría si no tiene productos
            }

            System.out.printf("\n== CATEGORÍA: %s ==\n", CATEGORIAS[cat]);
            System.out.println("--------------------------------------------------");
            System.out.printf("%-20s %-15s %-15s %-15s\n",
                    "PRODUCTO", "CANTIDAD", "PRECIO UNIT.", "VALOR TOTAL");
            System.out.println("--------------------------------------------------");

            double valorTotalCategoria = 0;

            // Recorrer productos de esta categoría
            for (int i = 0; i < cantidadProductos; i++) {
                if (categoriaProducto[i] == cat) {
                    System.out.printf("%-20s %-15d $%-14.2f $%-14.2f\n",
                            nombres[i],
                            cantidades[i],
                            precios[i],
                            valoresTotal[i]);

                    valorTotalCategoria += valoresTotal[i];
                }
            }

            System.out.println("--------------------------------------------------");
            System.out.printf("VALOR TOTAL DE LA CATEGORÍA: $%.2f\n", valorTotalCategoria);
            System.out.println("--------------------------------------------------");

            valorTotalInventario += valorTotalCategoria;
        }

        System.out.println("\n--------------------------------------------------");
        System.out.printf("VALOR TOTAL DEL INVENTARIO: $%.2f\n", valorTotalInventario);
        System.out.println("--------------------------------------------------");
    }
}