import java.util.Scanner;

public class Main {
    private static final int MAX_PRODUCTOS = 10;

    private static final int MAX_CATEGORIAS = 5;
    private static final int MAX_SECCIONES = 5;
    private static final int MAX_PRODUCTOS_POR_SECCION = 20;


    private static final String[] CATEGORIAS = {
            "Alimentos", "Bebidas", "Limpieza", "Electrónica", "Ropa"
    };

    private static final String[] SECCIONES = {
            "Principal", "Secundaria", "Promoción", "Temporada", "Especial"
    };

    private static String[] nombres = new String[MAX_PRODUCTOS];
    private static int[] cantidades = new int[MAX_PRODUCTOS];
    private static double[] precios = new double[MAX_PRODUCTOS];
    private static double[] valoresTotal = new double[MAX_PRODUCTOS];

    private static int[] categoriaProducto = new int[MAX_PRODUCTOS]; 
    private static int[] seccionProducto = new int[MAX_PRODUCTOS]; 

    private static int[][][] productosPorCategoriaSeccion = new int[MAX_CATEGORIAS][MAX_SECCIONES][MAX_PRODUCTOS_POR_SECCION];

    private static int[][] contadorProductosPorCS = new int[MAX_CATEGORIAS][MAX_SECCIONES];

    private static int cantidadProductos = 0;

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

        scanner.close();
    }

    private static void showMenu() {
        System.out.println("\n===== SISTEMA DE INVENTARIO =====");
        System.out.println("1. Agregar un nuevo producto");
        System.out.println("2. Actualizar cantidad de un producto");
        System.out.println("3. Generar reporte de inventario general");
        System.out.println("4. Generar reporte por categoría y sección");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1; 
        }
    }

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

        int categoria = seleccionarCategoria();

        int seccion = seleccionarSeccion();

        if (contadorProductosPorCS[categoria][seccion] >= MAX_PRODUCTOS_POR_SECCION) {
            System.out.println("\nNo hay más espacio en esta categoría y sección. Por favor, elija otra combinación.");
            return;
        }

        nombres[cantidadProductos] = nombre;
        cantidades[cantidadProductos] = cantidad;
        precios[cantidadProductos] = precio;
        valoresTotal[cantidadProductos] = cantidad * precio;

        categoriaProducto[cantidadProductos] = categoria;
        seccionProducto[cantidadProductos] = seccion;

        int indiceCS = contadorProductosPorCS[categoria][seccion];
        productosPorCategoriaSeccion[categoria][seccion][indiceCS] = cantidadProductos;
        contadorProductosPorCS[categoria][seccion]++;

        System.out.println("\nProducto agregado exitosamente:");
        System.out.printf("Producto: %s - Cantidad: %d - Precio por unidad: %.2f - Valor total: %.2f\n",
                nombre, cantidad, precio, valoresTotal[cantidadProductos]);
        System.out.printf("Categoría: %s - Sección: %s\n",
                CATEGORIAS[categoria], SECCIONES[seccion]);

        cantidadProductos++;
    }

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

    private static int seleccionarSeccion() {
        System.out.println("\nSecciones disponibles:");
        for (int i = 0; i < SECCIONES.length; i++) {
            System.out.printf("%d. %s\n", (i + 1), SECCIONES[i]);
        }

        int seccion = -1;
        boolean seleccionValida = false;
        while (!seleccionValida) {
            System.out.print("Seleccione una sección (1-" + SECCIONES.length + "): ");
            try {
                seccion = Integer.parseInt(scanner.nextLine()) - 1;
                if (seccion >= 0 && seccion < SECCIONES.length) {
                    seleccionValida = true;
                } else {
                    System.out.println("Selección inválida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
            }
        }
        return seccion;
    }

    private static void updateQuantity() {
        if (cantidadProductos == 0) {
            System.out.println("\nNo hay productos en el inventario.");
            return;
        }

        System.out.println("\n----- ACTUALIZAR CANTIDAD DE PRODUCTO -----");

        System.out.println("Productos disponibles:");
        for (int i = 0; i < cantidadProductos; i++) {
            System.out.printf("%d. %s (Cantidad actual: %d) - Categoría: %s, Sección: %s\n",
                    (i + 1), nombres[i], cantidades[i],
                    CATEGORIAS[categoriaProducto[i]], SECCIONES[seccionProducto[i]]);
        }

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

        cantidades[indice] = nuevaCantidad;
        valoresTotal[indice] = cantidades[indice] * precios[indice];

        System.out.println("\nCantidad actualizada exitosamente:");
        System.out.printf("Producto: %s - Nueva cantidad: %d - Valor total actualizado: %.2f\n",
                nombres[indice], cantidades[indice], valoresTotal[indice]);
    }

    private static void generateReport() {
        if (cantidadProductos == 0) {
            System.out.println("\nNo hay productos en el inventario.");
            return;
        }

        System.out.println("\n========== REPORTE DE INVENTARIO GENERAL ==========");

        double valorTotalInventario = 0;

        System.out.println("\nLista de productos:");
        System.out.println("----------------------------------------------------------------------------------------------");
        System.out.printf("%-20s %-15s %-15s %-15s %-15s %-15s\n",
                "PRODUCTO", "CANTIDAD", "PRECIO UNIT.", "VALOR TOTAL", "CATEGORÍA", "SECCIÓN");
        System.out.println("----------------------------------------------------------------------------------------------");

        for (int i = 0; i < cantidadProductos; i++) {
            System.out.printf("%-20s %-15d $%-14.2f $%-14.2f %-15s %-15s\n",
                    nombres[i], cantidades[i], precios[i], valoresTotal[i],
                    CATEGORIAS[categoriaProducto[i]], SECCIONES[seccionProducto[i]]);
            valorTotalInventario += valoresTotal[i];
        }

        System.out.println("----------------------------------------------------------------------------------------------");
        System.out.printf("VALOR TOTAL DEL INVENTARIO: $%.2f\n", valorTotalInventario);
        System.out.println("----------------------------------------------------------------------------------------------");
    }

    private static void generateReportByCategory() {
        if (cantidadProductos == 0) {
            System.out.println("\nNo hay productos en el inventario.");
            return;
        }

        System.out.println("\n========== REPORTE DE INVENTARIO POR CATEGORÍA Y SECCIÓN ==========");

        double valorTotalInventario = 0;

        for (int cat = 0; cat < MAX_CATEGORIAS; cat++) {
            boolean tieneCategoriaProductos = false;

            for (int sec = 0; sec < MAX_SECCIONES; sec++) {
                if (contadorProductosPorCS[cat][sec] > 0) {
                    tieneCategoriaProductos = true;
                    break;
                }
            }

            if (!tieneCategoriaProductos) {
                continue;  
            }

            System.out.printf("\n== CATEGORÍA: %s ==\n", CATEGORIAS[cat]);

            for (int sec = 0; sec < MAX_SECCIONES; sec++) {
                int numProductos = contadorProductosPorCS[cat][sec];

                if (numProductos > 0) {
                    System.out.printf("\n-- SECCIÓN: %s --\n", SECCIONES[sec]);
                    System.out.println("-------------------------------------------------------------");
                    System.out.printf("%-20s %-15s %-15s %-15s\n",
                            "PRODUCTO", "CANTIDAD", "PRECIO UNIT.", "VALOR TOTAL");
                    System.out.println("-------------------------------------------------------------");

                    double valorTotalSeccion = 0;

                    for (int i = 0; i < numProductos; i++) {
                        int indiceProducto = productosPorCategoriaSeccion[cat][sec][i];
                        System.out.printf("%-20s %-15d $%-14.2f $%-14.2f\n",
                                nombres[indiceProducto],
                                cantidades[indiceProducto],
                                precios[indiceProducto],
                                valoresTotal[indiceProducto]);

                        valorTotalSeccion += valoresTotal[indiceProducto];
                    }

                    System.out.println("-------------------------------------------------------------");
                    System.out.printf("VALOR TOTAL DE LA SECCIÓN: $%.2f\n", valorTotalSeccion);
                    System.out.println("-------------------------------------------------------------");

                    valorTotalInventario += valorTotalSeccion;
                }
            }
        }

        System.out.println("\n-------------------------------------------------------------");
        System.out.printf("VALOR TOTAL DEL INVENTARIO: $%.2f\n", valorTotalInventario);
        System.out.println("-------------------------------------------------------------");
    }
}