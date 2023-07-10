/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

import java.util.*;
import java.io.*;
import java.nio.charset.Charset;
public class EnsambladorDeFragmentos {

    private List<String> fragmentos; // Lista de fragmentos cargados
    private List<String> fragmentosEnsamblados; // Lista de fragmentos ensamblados
    private int[][] matrizTraslapes; // Matriz de traslapes
    private boolean[] fragmentosUsados; // Marcador de fragmentos utilizados en el ensamblaje

    public EnsambladorDeFragmentos() {
        fragmentos = new ArrayList<>();
        fragmentosEnsamblados = new ArrayList<>();
    }

    public void agregarFragmento(String fragmento) {
        fragmentos.add(fragmento);
    }

    // Carga los fragmentos desde un archivo
    public void cargarFragmentosDeArchivo(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            StringBuilder fragmentBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                fragmentBuilder.append(line);
                fragmentBuilder.append(System.lineSeparator()); // Add line separator
            }
            String[] arrayFragmentos = fragmentBuilder.toString().split("\\R"); // Split fragments using line separators
            fragmentos.addAll(Arrays.asList(arrayFragmentos));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Guarda los fragmentos ensamblados en un archivo
    public void guardaEnsambladoEnArchivo(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String fragment : fragmentosEnsamblados) {
                writer.write(fragment);
                writer.newLine();
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
            int startIndex = random.nextInt(textLength - fragmentLength + 1); // Índice de inicio aleatorio
            int endIndex = startIndex + fragmentLength;
            String fragment = text.substring(startIndex, endIndex);
            fragmentos.add(fragment);
        }
    }

    // Calcula la matriz de traslapes entre fragmentos
    private void calculaMatrizTraslape() {
        int contadorFragmentos = fragmentos.size();
        matrizTraslapes = new int[contadorFragmentos][contadorFragmentos];

        for (int i = 0; i < contadorFragmentos; i++) {
            String fragmentoA = fragmentos.get(i);
            for (int j = 0; j < contadorFragmentos; j++) {
                if (i != j) {
                    String fragmentoB = fragmentos.get(j);
                    matrizTraslapes[i][j] = calculaTraslape(fragmentoA, fragmentoB);
                }
            }
        }
    }

    // Calcula el traslape entre dos fragmentos
    private int calculaTraslape(String fragmentA, String fragmentB) {
        int maxTraslape = Math.min(fragmentA.length(), fragmentB.length()) - 1;
        for (int i = maxTraslape; i >= 0; i--) {
            String sufijo = fragmentA.substring(fragmentA.length() - i);
            String prefijo = fragmentB.substring(0, i);
            if (sufijo.equals(prefijo)) {
                return i;
            }
        }
        return 0;
    }

    // Encuentra el siguiente fragmento a ensamblar basado en el traslape máximo
    private int encuentraSiguienteFragmento(int fragmentoActual) {
        int contadorFragmentos = fragmentos.size();
        int siguenteFragmento = -1;
        int maxTraslape = 0;

        for (int i = 0; i < contadorFragmentos; i++) {
            if (!fragmentosUsados[i] && matrizTraslapes[fragmentoActual][i] > maxTraslape) {
                maxTraslape = matrizTraslapes[fragmentoActual][i];
                siguenteFragmento = i;
            }
        }

        return siguenteFragmento;
    }

    // Ensambla los fragmentos
    public void ensamblaFragmento(int minTraslape) {
        calculaMatrizTraslape();
        int contadorFragmentos = fragmentos.size();
        fragmentosUsados = new boolean[contadorFragmentos];
        fragmentosEnsamblados.clear();

        int fragmentoInicial = 0; // Fragmento inicial
        int siguienteFragmento;

        do {
            fragmentosUsados[fragmentoInicial] = true;
            fragmentosEnsamblados.add(fragmentos.get(fragmentoInicial));
            siguienteFragmento = encuentraSiguienteFragmento(fragmentoInicial);

            if (siguienteFragmento != -1) {
                String fragmentoEnsamblado = fragmentos.get(fragmentoInicial);
                int traslape = matrizTraslapes[fragmentoInicial][siguienteFragmento];
                String textoTraslapado = fragmentos.get(siguienteFragmento).substring(traslape);
                fragmentoEnsamblado += textoTraslapado;
                fragmentosEnsamblados.set(fragmentosEnsamblados.size() - 1, fragmentoEnsamblado);
                fragmentoInicial = siguienteFragmento;
            }
        } while (siguienteFragmento != -1);

        // Verificar si hay "islas" de fragmentos
        boolean tieneIslas = false;
        List<String> fragmentosIsla = new ArrayList<>();

        for (int i = 0; i < fragmentosUsados.length; i++) {
            if (!fragmentosUsados[i]) {
                tieneIslas = true;
                fragmentosIsla.add(fragmentos.get(i));
            }
        }

        // Imprimir el ensamblaje
        System.out.println("Ensamblaje:");
        for (String fragmento : fragmentosEnsamblados) {
            System.out.println(fragmento);
        }

        if (tieneIslas) {
            System.out.println("El ensamblaje produjo islas de fragmentos.");
            System.out.println("Fragmentos de isla:");

            for (String fragmento : fragmentosIsla) {
                System.out.println(fragmento);
            }
        } else {
            System.out.println("El ensamblaje no produjo islas de fragmentos.");
        }
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
        solo se teniene en cuenta los primeros caracteres hasta el 
        tamaño de la hilera ensamblada y viceversa 
     */
    public double calculaSimilitud(String hileraOriginal) {
        String cadenaEnsamblada = String.join("", fragmentosEnsamblados);
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

    // Listar los fragmentos cargados
    public void listarFragmentos() {
        for (String fragment : fragmentos) {
            System.out.println(fragment);
        }
    }

    //Ordenar los fragmentos alfabéticamente usando Quicksort
    public void ordenarAlfabetico() {
        quicksort(0, fragmentos.size() - 1);
    }

    private void quicksort(int inicio, int fin) {
        if (inicio < fin) {
            int indiceParticion = particionar(inicio, fin);
            quicksort(inicio, indiceParticion - 1);
            quicksort(indiceParticion + 1, fin);
        }
    }

    private int particionar(int inicio, int fin) {
        String pivote = fragmentos.get(fin);
        int i = inicio - 1;

        for (int j = inicio; j < fin; j++) {
            if (fragmentos.get(j).compareTo(pivote) <= 0) {
                i++;
                swapFragments(i, j);
            }
        }

        swapFragments(i + 1, fin);
        return i + 1;
    }

    private void swapFragments(int i, int j) {
        String temp = fragmentos.get(i);
        fragmentos.set(i, fragmentos.get(j));
        fragmentos.set(j, temp);
    }

// Filtrar fragmentos por longitud mayor que un valor dado
    public List<String> filtrarPorLongitud(int length) {
        List<String> filteredFragments = new ArrayList<>();
        for (String fragment : fragmentos) {
            if (fragment.length() > length) {
                filteredFragments.add(fragment);
            }
        }
        return filteredFragments;
    }

// Buscar fragmentos por palabras clave
    public void buscarPorPalabraClave(String keyword) {
        List<String> matchingFragments = new ArrayList<>();
        for (String fragment : fragmentos) {
            if (fragment.contains(keyword)) {
                matchingFragments.add(fragment);
            }
        }

        if (matchingFragments.isEmpty()) {
            System.out.println("No se encontraron coincidencias.");
        } else {
            System.out.println("Coincidencias encontradas:");
            for (String fragment : matchingFragments) {
                System.out.println(fragment);
            }
        }
    }

    public void generarGrafoConexoMinimo() {
        int contadorFragmentos = fragmentos.size();
        int[][] matrizAdyacencia = new int[contadorFragmentos][contadorFragmentos];

        // Construir la matriz de adyacencia basada en la superposición de fragmentos
        for (int i = 0; i < contadorFragmentos; i++) {
            String fragmentA = fragmentos.get(i);
            for (int j = 0; j < contadorFragmentos; j++) {
                if (i != j) {
                    String fragmentB = fragmentos.get(j);
                    int traslape = calculaTraslape(fragmentA, fragmentB);
                    matrizAdyacencia[i][j] = traslape;
                    matrizAdyacencia[j][i] = traslape; // Asegurar que la matriz sea simétrica
                }
            }
        }

        // Algoritmo de Prim para encontrar el grafo conexo mínimo
        boolean[] visitado = new boolean[contadorFragmentos];
        int[] padre = new int[contadorFragmentos];
        int[] pesoMin = new int[contadorFragmentos];

        Arrays.fill(pesoMin, Integer.MAX_VALUE);

        if (contadorFragmentos > 1) {
            pesoMin[0] = 0;
            padre[0] = -1;
        }

        for (int i = 0; i < contadorFragmentos - 1; i++) {
            int indexMinimo = -1;
            int valorPesoMinimo = Integer.MAX_VALUE;
            for (int j = 0; j < contadorFragmentos; j++) {
                if (!visitado[j] && pesoMin[j] < valorPesoMinimo) {
                    valorPesoMinimo = pesoMin[j];
                    indexMinimo = j;
                }
            }

            if (indexMinimo != -1) {
                visitado[indexMinimo] = true;
            }

            for (int j = 0; j < contadorFragmentos; j++) {
                if (!visitado[j] && matrizAdyacencia[indexMinimo][j] > 0 && matrizAdyacencia[indexMinimo][j] < pesoMin[j]) {
                    padre[j] = indexMinimo;
                    pesoMin[j] = matrizAdyacencia[indexMinimo][j];
                }
            }
        }

        // Construir el grafo conexo mínimo
        List<String> fragmentosConexoMinimo = new ArrayList<>();
        for (int i = 1; i < contadorFragmentos; i++) {
            String fragmentoA = fragmentos.get(padre[i]);
            String fragmentoB = fragmentos.get(i);
            int overlap = matrizAdyacencia[padre[i]][i];
            String textoTraslapado = fragmentoB.substring(overlap);
            String fragmentoEnsamblado = fragmentoA + textoTraslapado;
            fragmentosConexoMinimo.add(fragmentoEnsamblado);
        }

        // Reemplazar los fragmentos originales con el grafo conexo mínimo
        fragmentos = fragmentosConexoMinimo;
    }

    public void imprimirGrafoConexoMinimo() {
        int contadorFragmentos = fragmentos.size();

        // Construir el grafo conexo mínimo
        List<String> fragmentosConexoMinimo = new ArrayList<>();
        for (int i = 1; i < contadorFragmentos; i++) {
            String fragmentoA = fragmentos.get(i - 1);
            String fragmentoB = fragmentos.get(i);
            int overlap = calculaTraslape(fragmentoA, fragmentoB);
            String textoTraslapado = fragmentoB.substring(overlap);
            String fragmentoEnsamblado = fragmentoA + textoTraslapado;
            fragmentosConexoMinimo.add(fragmentoEnsamblado);
        }

        // Imprimir el grafo conexo mínimo
        for (String fragmento : fragmentosConexoMinimo) {
            System.out.println(fragmento);
        }
    }

    // Reconstruir la hilera original a partir de los fragmentos ensamblados
    public String reconstruirHileraOriginal() {
        StringBuilder hileraReconstruida = new StringBuilder();
        for (String fragmento : fragmentosEnsamblados) {
            hileraReconstruida.append(fragmento);
        }
        return hileraReconstruida.toString();
    }

}