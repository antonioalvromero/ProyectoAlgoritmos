package Domain;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.nio.charset.Charset;

public class EnsambladorDeFragmentosMenu {

    public static void main(String[] args) throws UnsupportedEncodingException {
        try (Scanner scanner = new Scanner(System.in)) {
            EnsambladorDeFragmentosPrueba ensamblador = new EnsambladorDeFragmentosPrueba();
            
            System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out), true, "UTF-8"));
            
            boolean salir = false;
            while (!salir) {
                System.out.println("---- MENÚ ----");
                System.out.println("1. Cargar fragmentos desde archivo");
                System.out.println("2. Generar fragmentos aleatorios");
                System.out.println("3. Crear matriz de traslapes");
                System.out.println("4. Ensamblar fragmentos");
                System.out.println("5. Guardar ensamblado en archivo");
                System.out.println("6. Calcular similitud con hilera original");
                System.out.println("7. Listar fragmentos");
                System.out.println("8. Ordenar fragmentos alfabéticamente");
                System.out.println("9. Filtrar fragmentos por longitud");
                System.out.println("10. Buscar fragmentos por palabra clave");
                System.out.println("11. Generar grafo conexo mínimo");
                System.out.println("12. Salir");
                System.out.print("Ingrese la opción deseada: ");
                int opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea
                
                switch (opcion) {
                    case 1:
                        System.out.print("Ingrese el nombre del archivo: ");
                        String archivoCarga = scanner.nextLine();
                        ensamblador.cargarFragmentosDeArchivo(archivoCarga);
                        System.out.println("Fragmentos cargados exitosamente.");
                        break;
                    case 2:
                        System.out.print("Ingrese el nombre del archivo: ");
                        String archivoTexto = scanner.nextLine();
                        System.out.print("Ingrese el número de fragmentos: ");
                        int numFragmentos = scanner.nextInt();
                        System.out.print("Ingrese la longitud promedio de los fragmentos: ");
                        int longitudPromedio = scanner.nextInt();
                        ensamblador.generaFragmentosAleatoriosDeArchivo(archivoTexto, numFragmentos, longitudPromedio);
                        System.out.println("Fragmentos generados exitosamente.");
                        break;
                    case 3:
                        ensamblador.crearMatrizTraslapes();
                        System.out.println("Matriz de traslapes creada exitosamente.");
                        break;
                    case 4:
                        ensamblador.ensambla(5);
                        System.out.println("Fragmentos ensamblados exitosamente.");
                        break;
                    case 5:
                        System.out.print("Ingrese el nombre del archivo: ");
                        String archivoGuarda = scanner.nextLine();
                        ensamblador.guardaEnsambladoEnArchivo(archivoGuarda);
                        System.out.println("Fragmentos ensamblados guardados exitosamente.");
                        break;
                    case 6:
                        System.out.print("Ingrese el nombre del archivo de la hilera original: ");
                        String archivoHilera = scanner.nextLine();
                        String hileraOriginal = ensamblador.hileraOriginalDesdeArchivo(archivoHilera);
                        double similitud = ensamblador.calculaSimilitud(hileraOriginal);
                        System.out.println("La similitud entre la hilera original y la hilera ensamblada es: " + similitud + "%");
                        break;
                    case 7:
                        ensamblador.listarFragmentos();
                        break;
                    case 8:
                        ensamblador.ordenarAlfabetico();
                        System.out.println("Fragmentos ordenados alfabéticamente.");
                        break;
                    case 9:
                        System.out.print("Ingrese la longitud mínima: ");
                        int longitudMinima = scanner.nextInt();
                        ensamblador.filtrarPorLongitud(longitudMinima);
                        System.out.println("Fragmentos filtrados por longitud.");
                        break;
                    case 10:
                        System.out.print("Ingrese la palabra clave: ");
                        String palabraClave = scanner.nextLine();
                        ensamblador.buscarPorPalabraClave(palabraClave);
                        break;
                    case 11:
                        ensamblador.generarGrafoConexoMinimo();
                        System.out.println("Grafo conexo mínimo generado.");
                        break;
                    case 12:
                        salir = true;
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Opción inválida. Por favor ingrese un número del 1 al 12.");
                        break;
                }
                System.out.println();
            }
        }
    }
}
