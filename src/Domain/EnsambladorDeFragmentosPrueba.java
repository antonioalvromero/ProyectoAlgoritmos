package Domain;

import java.util.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class EnsambladorDeFragmentosPrueba {

    private NodoFragmento inicio; // Inicio de la lista enlazada de fragmentos cargados
    private NodoFragmento ensambladoInicio; // Inicio de la lista enlazada de fragmentos ensamblados
    private int[][] matrizTraslapes; // Matriz de traslapes
    private boolean[] fragmentosUsados; // Marcador de fragmentos utilizados en el ensamblaje

    public EnsambladorDeFragmentosPrueba() {
        inicio = null;
        ensambladoInicio = null;
    }

    public void agregarFragmento(String fragmento) {
        NodoFragmento nuevoFragmento = new NodoFragmento(fragmento);
        if (inicio == null) {
            inicio = nuevoFragmento;
        } else {
            NodoFragmento actual = inicio;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevoFragmento);
        }
    }

    // Carga los fragmentos desde un archivo
    public void cargarFragmentosDeArchivo(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            StringBuilder fragmentBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                fragmentBuilder.append(line);
                fragmentBuilder.append(System.lineSeparator()); // Agregar separador de línea
            }
            String[] arrayFragmentos = fragmentBuilder.toString().split("\\R"); // Dividir fragmentos utilizando separadores de línea
            for (String fragmento : arrayFragmentos) {
                agregarFragmento(fragmento);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Guarda los fragmentos ensamblados en un archivo
    public void guardaEnsambladoEnArchivo(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            NodoFragmento actual = ensambladoInicio;
            while (actual != null) {
                writer.write(actual.getFragmento());
                writer.newLine();
                actual = actual.getSiguiente();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Genera fragmentos aleatorios a partir de un archivo de texto arbitrario
    public void generaFragmentosAleatoriosDeArchivo(String filename, int fragmentCount, int averageLength) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            StringBuilder textBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                textBuilder.append(line);
            }
            String text = textBuilder.toString();
            generarFragmentosAleatorios(text, fragmentCount, averageLength);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Genera fragmentos aleatorios a partir de un texto dado
    public void generarFragmentosAleatorios(String text, int contadorFragmentos, int averageLength) {
        Random random = new Random();
        int textLength = text.length();
        for (int i = 0; i < contadorFragmentos; i++) {
            int fragmentLength = (int) (averageLength * (random.nextDouble() + 0.5)); // Longitud aleatoria cercana a la longitud promedio
            int startIndex = random.nextInt(textLength - fragmentLength + 1); // Índice de inicio aleatorio dentro del texto
            String fragment = text.substring(startIndex, startIndex + fragmentLength);
            agregarFragmento(fragment);
        }
    }

    // Calcula la matriz de traslapes entre los fragmentos cargados
    public void crearMatrizTraslapes() {
        int contadorFragmentos = contarFragmentos();
        matrizTraslapes = new int[contadorFragmentos][contadorFragmentos];
        fragmentosUsados = new boolean[contadorFragmentos];

        NodoFragmento actual1 = inicio;
        NodoFragmento actual2;
        for (int i = 0; i < contadorFragmentos; i++) {
            actual2 = inicio;
            for (int j = 0; j < contadorFragmentos; j++) {
                matrizTraslapes[i][j] = calcularTraslape(actual1.getFragmento(), actual2.getFragmento());
                actual2 = actual2.getSiguiente();
            }
            actual1 = actual1.getSiguiente();
        }
    }

    // Calcula el traslape entre dos fragmentos dados
    public int calcularTraslape(String fragmento1, String fragmento2) {
        int maxTraslape = Math.min(fragmento1.length(), fragmento2.length());
        for (int i = maxTraslape; i > 0; i--) {
            if (fragmento1.endsWith(fragmento2.substring(0, i))) {
                return i;
            }
        }
        return 0;
    }

    public void imprimirMatrizTraslapes() {
        int contadorFragmentos = contarFragmentos();

        // Crear una matriz de fragmentos para almacenar los nodos
        NodoFragmento[][] matrizFragmentos = new NodoFragmento[contadorFragmentos][contadorFragmentos];

        // Llenar la matriz de fragmentos
        NodoFragmento actual1 = inicio;
        for (int i = 0; i < contadorFragmentos; i++) {
            NodoFragmento actual2 = inicio;
            for (int j = 0; j < contadorFragmentos; j++) {
                matrizFragmentos[i][j] = actual2;
                actual2 = actual2.getSiguiente();
            }
            actual1 = actual1.getSiguiente();
        }

        // Imprimir encabezado de columnas
        System.out.print("     ");
        for (int i = 0; i < contadorFragmentos; i++) {
            System.out.printf("%-4d ", i + 1);
        }
        System.out.println();

        actual1 = inicio;
        for (int i = 0; i < contadorFragmentos; i++) {
            // Imprimir número de fila
            System.out.printf("%-5d", i + 1);

            NodoFragmento actual2 = inicio;
            for (int j = 0; j < contadorFragmentos; j++) {
                // Comparar los fragmentos
                int traslape = calcularTraslape(actual1.getFragmento(), actual2.getFragmento());

                // Imprimir el valor de traslape
                System.out.printf("%-4d ", traslape);

                // Mostrar fragmentos comparados
                if (i != j && traslape > 0) {
                    System.out.printf("(%d-%d) ", i + 1, j + 1);
                }

                actual2 = actual2.getSiguiente();
            }

            System.out.println();
            actual1 = actual1.getSiguiente();
        }
    }

    // Encuentra el siguiente fragmento para ensamblar
    public int encuentraSiguienteFragmento(int fragmentoActual) {
        int maxTraslape = 0;
        int siguienteFragmento = -1;

        for (int i = 0; i < contarFragmentos(); i++) {
            if (fragmentosUsados[i]) {
                continue;
            }

            if (matrizTraslapes[fragmentoActual][i] > maxTraslape) {
                maxTraslape = matrizTraslapes[fragmentoActual][i];
                siguienteFragmento = i;
            }
        }

        if (siguienteFragmento >= 0) {
            fragmentosUsados[siguienteFragmento] = true;
        }

        return siguienteFragmento;
    }

    // Ensambla los fragmentos cargados en orden
    public void ensambla(int tamanioMinimoTraslape) {
        int fragmentoInicial = 0;
        int siguienteFragmento;
        boolean hayIslas = false;
        List<String> islas = new ArrayList<>(); // Lista para almacenar los fragmentos que forman islas

        ensambladoInicio = new NodoFragmento(inicio.getFragmento());

        do {
            siguienteFragmento = encuentraSiguienteFragmento(fragmentoInicial);

            if (siguienteFragmento != -1) {
                int traslape = matrizTraslapes[fragmentoInicial][siguienteFragmento];

                if (traslape >= tamanioMinimoTraslape && traslape < inicio.getFragmento().length()) {
                    ensambladoInicio.setFragmento(ensambladoInicio.getFragmento() + inicio.getFragmento().substring(traslape));
                } else {
                    hayIslas = true;
                    islas.add(inicio.getFragmento()); // Agregar el fragmento a la lista de islas
                }

                fragmentoInicial = siguienteFragmento;
            }
        } while (siguienteFragmento != -1);

        if (hayIslas) {
            System.out.println("Se encontraron islas en el ensamblaje.");
            System.out.println("Fragmentos que forman islas:");

            for (String isla : islas) {
                System.out.println(isla);
            }
        } else {
            System.out.println("No se encontraron islas en el ensamblaje.");
        }
    }

    // Función para contar el número de fragmentos
    public int contarFragmentos() {
        int contador = 0;
        NodoFragmento actual = inicio;
        while (actual != null) {
            contador++;
            actual = actual.getSiguiente();
        }
        return contador;
    }

    // Carga la hilera original desde un archivo
    public String hileraOriginalDesdeArchivo(String filename) {
        StringBuilder stringOriginal = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringOriginal.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringOriginal.toString();
    }

    /* Calcula la similitud entre la hilera original y la hilera ensamblada
   si la hilera original es más larga que la hilera ensamblada,
   solo se tienen en cuenta los primeros caracteres hasta el 
   tamaño de la hilera ensamblada y viceversa 
     */
    public double calculaSimilitud(String hileraOriginal) {
        String cadenaEnsamblada = cadenaFragmentos(ensambladoInicio);
        int longitudOriginal = hileraOriginal.length();
        int longitudEnsamblado = cadenaEnsamblada.length();
        int longitudComun = 0;

        int longitudMinima = Math.min(longitudOriginal, longitudEnsamblado); // Obtener la longitud mínima

        for (int i = 0; i < longitudMinima; i++) {
            if (hileraOriginal.charAt(i) == cadenaEnsamblada.charAt(i)) {
                longitudComun++;
            }
        }

        return (double) longitudComun / longitudEnsamblado * 100; // Calcular el porcentaje de similitud
    }

// Concatena los fragmentos en una cadena
    private String cadenaFragmentos(NodoFragmento nodo) {
        StringBuilder cadena = new StringBuilder();
        NodoFragmento actual = nodo;
        while (actual != null) {
            cadena.append(actual.getFragmento());
            actual = actual.getSiguiente();
        }
        return cadena.toString();
    }

// Agrega un fragmento al final de la lista de fragmentos ensamblados
    private void agregarFragmentoEnsamblado(String fragmento) {
        NodoFragmento nuevoNodo = new NodoFragmento(fragmento);
        if (ensambladoInicio == null) {
            ensambladoInicio = nuevoNodo;
        } else {
            NodoFragmento actual = ensambladoInicio;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevoNodo);
        }
    }

// Listar los fragmentos cargados
    public void listarFragmentos() {
        NodoFragmento actual = inicio;
        while (actual != null) {
            System.out.println(actual.getFragmento());
            actual = actual.getSiguiente();
        }
    }

// Ordenar los fragmentos alfabéticamente usando Quicksort
    public void ordenarAlfabetico() {
        inicio = quicksort(inicio);
    }

    private NodoFragmento quicksort(NodoFragmento inicio) {
        if (inicio == null || inicio.getSiguiente() == null) {
            return inicio;
        }
        NodoFragmento pivote = inicio;
        NodoFragmento menorCabecera = null;
        NodoFragmento menor = null;
        NodoFragmento mayorCabecera = null;
        NodoFragmento mayor = null;

        NodoFragmento actual = inicio.getSiguiente();

        while (actual != null) {
            NodoFragmento siguiente = actual.getSiguiente();
            if (actual.getFragmento().compareTo(pivote.getFragmento()) < 0) {
                if (menor == null) {
                    menorCabecera = actual;
                    menor = actual;
                } else {
                    menor.setSiguiente(actual);
                    menor = actual;
                }
            } else {
                if (mayor == null) {
                    mayorCabecera = actual;
                    mayor = actual;
                } else {
                    mayor.setSiguiente(actual);
                    mayor = actual;
                }
            }
            actual.setSiguiente(null);
            actual = siguiente;
        }

        menorCabecera = quicksort(menorCabecera);
        mayorCabecera = quicksort(mayorCabecera);

        if (menorCabecera != null) {
            menor.setSiguiente(pivote);
            inicio = menorCabecera;
        } else {
            inicio = pivote;
        }

        pivote.setSiguiente(mayorCabecera);

        return inicio;
    }

// Filtrar fragmentos por longitud mayor que un valor dado
    public void filtrarPorLongitud(int length) {
        NodoFragmento fragmentosFiltrados = null;
        NodoFragmento actual = inicio;
        while (actual != null) {
            if (actual.getFragmento().length() > length) {
                agregarFragmento(actual.getFragmento());
            }
            actual = actual.getSiguiente();
        }
    }

// Buscar fragmentos por palabras clave
    public void buscarPorPalabraClave(String keyword) {
        boolean seEncontraronCoincidencias = false;
        NodoFragmento actual = inicio;
        while (actual != null) {
            if (actual.getFragmento().contains(keyword)) {
                System.out.println(actual.getFragmento());
                seEncontraronCoincidencias = true;
            }
            actual = actual.getSiguiente();
        }

        if (!seEncontraronCoincidencias) {
            System.out.println("No se encontraron coincidencias.");
        }
    }

    public void generarGrafoConexoMinimo() {
        int contadorFragmentos = contarFragmentos();
        int[][] matrizAdyacencia = new int[contadorFragmentos][contadorFragmentos];

        // Construir la matriz de adyacencia basada en la superposición de fragmentos
        NodoFragmento actualI = inicio;
        int i = 0;
        while (actualI != null) {
            String fragmentoI = actualI.getFragmento();
            NodoFragmento actualJ = inicio;
            int j = 0;
            while (actualJ != null) {
                if (i != j) {
                    String fragmentoJ = actualJ.getFragmento();
                    int superposicion = calcularSuperposicion(fragmentoI, fragmentoJ);
                    matrizAdyacencia[i][j] = superposicion;
                }
                actualJ = actualJ.getSiguiente();
                j++;
            }
            actualI = actualI.getSiguiente();
            i++;
        }

        // Algoritmo de Prim para generar el árbol de expansión mínima
        int[] distancia = new int[contadorFragmentos];
        boolean[] visitado = new boolean[contadorFragmentos];
        int[] padre = new int[contadorFragmentos];
        for (int k = 0; k < contadorFragmentos; k++) {
            padre[k] = -1; // Establecer todos los valores iniciales a -1
        }

        for (int k = 0; k < contadorFragmentos; k++) {
            distancia[k] = Integer.MAX_VALUE;
            visitado[k] = false;
        }

        distancia[0] = 0;
        padre[0] = -1;

        for (int k = 0; k < contadorFragmentos - 1; k++) {
            int u = minimaDistancia(distancia, visitado, contadorFragmentos);
            if (u >= 0 && u < contadorFragmentos) {
                visitado[u] = true;
            }
            for (int v = 0; v < contadorFragmentos; v++) {
                if (!visitado[v] && matrizAdyacencia[u][v] != 0 && matrizAdyacencia[u][v] < distancia[v]) {
                    padre[v] = u;
                    distancia[v] = matrizAdyacencia[u][v];
                }
            }
        }

        // Generar los fragmentos ensamblados a partir del árbol de expansión mínima
        ensambladoInicio = null;
        for (int k = 1; k < contadorFragmentos; k++) {
            if (padre[k] != -1) {
                agregarFragmentoEnsamblado(matrizAdyacencia[padre[k]][k] + "");
            }
        }
    }

    private int calcularSuperposicion(String fragmento1, String fragmento2) {
        int longitud1 = fragmento1.length();
        int longitud2 = fragmento2.length();
        int minSuperposicion = Math.min(longitud1, longitud2);

        for (int k = minSuperposicion; k >= 0; k--) {
            if (fragmento1.endsWith(fragmento2.substring(0, k))) {
                return k;
            }
        }
        return 0;
    }

    // Función para encontrar el vértice con la distancia mínima no visitada
    public int minimaDistancia(int[] distancia, boolean[] visitado, int contadorFragmentos) {
        int minimaDist = Integer.MAX_VALUE;
        int minimaDistIndice = -1;
        for (int i = 0; i < contadorFragmentos; i++) {
            if (!visitado[i] && distancia[i] < minimaDist) {
                minimaDist = distancia[i];
                minimaDistIndice = i;
            }
        }
        return minimaDistIndice;
    }

    public static void main(String[] args) {
        EnsambladorDeFragmentosPrueba ensamblador = new EnsambladorDeFragmentosPrueba();
        ensamblador.cargarFragmentosDeArchivo("D:/Codigo/fragmentos.txt");
        ensamblador.crearMatrizTraslapes();
        ensamblador.imprimirMatrizTraslapes();
        ensamblador.ensambla(10);//Se le indica min de Traslapes
        ensamblador.generarGrafoConexoMinimo();
        ensamblador.guardaEnsambladoEnArchivo("D:/Codigo/ensamblaje.txt");
        System.out.println("Ensamblado completado.");
    }
}
