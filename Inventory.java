import java.util.Scanner;

public class Inventory {

    public static void main(String[] args) {
        Scanner input  = new Scanner(System.in);

        //int longitudCategoria = categorias.length;
        final int TAMAÑO_COLUMNA = 3;
        
        String[] categorias = {"Alimentos", "Limpieza"};

        String[][] nombres = new String[categorias.length][TAMAÑO_COLUMNA];
        int[][] cantidades = new int[categorias.length][TAMAÑO_COLUMNA];
        double[][] precios = new double[categorias.length][TAMAÑO_COLUMNA];

        for (int i = 0; i < categorias.length; i++) {
            System.out.println("Categorias: " + categorias[i]);
            for (int j = 0; j < TAMAÑO_COLUMNA; j++) {
                System.out.println("ingrese el nombre del producto " + (j + 1) + ": ");
                nombres[i][j] = input.nextLine();
                
                System.out.println("ingrese la cantidad del producto: ");
                cantidades[i][j] = input.nextInt();
                input.nextLine();

                System.out.println("ingrese el precio por unidad ");
                precios[i][j] = input.nextDouble();
                input.nextLine();
            }
        }

        input.close();
    }
}