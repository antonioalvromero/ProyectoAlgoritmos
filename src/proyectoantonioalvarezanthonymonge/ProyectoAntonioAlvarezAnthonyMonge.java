package proyectoantonioalvarezanthonymonge;

import Domain.EnsambladorDeFragmentos;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.nio.charset.Charset;
public class ProyectoAntonioAlvarezAnthonyMonge {

    public static void main(String[] args) {
        EnsambladorDeFragmentos ensamblador = new EnsambladorDeFragmentos();
        Scanner scanner = new Scanner(System.in);

        int opcion;
        do {
            System.out.println("========== Menú ==========");
            System.out.println("1. Agregar fragmento");
            System.out.println("2. Cargar fragmentos desde archivo");
            System.out.println("3. Guardar ensamblado en archivo");
            System.out.println("4. Generar fragmentos aleatorios");
            System.out.println("5. Ensamblar fragmento");
            System.out.println("6. Calcular similitud");
            System.out.println("7. Listar fragmentos");
            System.out.println("8. Ordenar fragmentos alfabéticamente");
            System.out.println("9. Filtrar fragmentos por longitud");
            System.out.println("10. Buscar fragmentos por palabra clave");
            System.out.println("11. Generar grafo conexo mínimo");
            System.out.println("12. Reconstruir hilera original");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el fragmento a agregar: ");
                    String fragmento = scanner.nextLine();
                    ensamblador.agregarFragmento(fragmento);
                    System.out.println("Fragmento agregado correctamente.");
                    break;
                case 2:
                    System.out.print("Ingrese el nombre del archivo: ");
                    String filename = scanner.nextLine();
                    ensamblador.cargarFragmentosDeArchivo(filename);
                    System.out.println("Fragmentos cargados correctamente.");
                    break;
                case 3:
                    System.out.print("Ingrese el nombre del archivo: ");
                    String saveFilename = scanner.nextLine();
                    ensamblador.guardaEnsambladoEnArchivo(saveFilename);
                    System.out.println("Ensamblado guardado en archivo correctamente.");
                    break;
                case 4:
                    System.out.print("Ingrese el nombre del archivo de texto: ");
                    String textFilename = scanner.nextLine();
                    System.out.print("Ingrese la cantidad de fragmentos: ");
                    int fragmentCount = scanner.nextInt();
                    System.out.print("Ingrese la longitud promedio de los fragmentos: ");
                    int averageLength = scanner.nextInt();
                    ensamblador.generaFragmentosAleatoriosDeArchivo(textFilename, fragmentCount, averageLength);
                    System.out.println("Fragmentos generados aleatoriamente correctamente.");
                    break;
                case 5:
                    System.out.print("Ingrese el tamaño mínimo de traslape: ");
                    int minTraslape = scanner.nextInt();
                    ensamblador.ensamblaFragmento(minTraslape);
                    System.out.println("Fragmentos ensamblados correctamente.");
                    break;
                case 6:
                    System.out.print("Ingrese el nombre del archivo de la hilera original: ");
                    String originalFilename = scanner.nextLine();
                    String hileraOriginal = ensamblador.hileraOriginalDesdeArchivo(originalFilename);
                    double similitud = ensamblador.calculaSimilitud(hileraOriginal);
                    System.out.println("La similitud entre la hilera original y la ensamblada es: " + similitud);
                    break;
                case 7:
                    System.out.println("Lista de fragmentos:");
                    ensamblador.listarFragmentos();
                    break;
                case 8:
                    ensamblador.ordenarAlfabetico();
                    System.out.println("Fragmentos ordenados alfabéticamente.");
                    ensamblador.listarFragmentos();
                    break;
                case 9:
                    System.out.print("Ingrese la longitud máxima: ");
                    int longitudMinima = scanner.nextInt();
                    scanner.nextLine(); // Consumir el carácter de nueva línea

                    List<String> fragmentosFiltrados = ensamblador.filtrarPorLongitud(longitudMinima);
                    if (fragmentosFiltrados.isEmpty()) {
                        System.out.println("No se encontraron fragmentos que cumplan la condición.");
                    } else {
                        System.out.println("Fragmentos filtrados por longitud:");
                        for (Iterator<String> it = fragmentosFiltrados.iterator(); it.hasNext();) {
                            fragmento = it.next();
                            System.out.println(fragmento);
                        }
                    }
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
                    ensamblador.imprimirGrafoConexoMinimo();
                    break;
                case 12:
                    String hileraReconstruida = ensamblador.reconstruirHileraOriginal();

                    System.out.println("Hilera original reconstruida: " + hileraReconstruida);
                    break;
                case 0:
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
                    break;
            }

            System.out.println(); // Salto de línea
        } while (opcion != 0);

        scanner.close();
    }
}
