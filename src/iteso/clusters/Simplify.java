package iteso.clusters;

import java.util.List;

/**
 * Clase principal que simplifica la ejecución de algoritmos de clustering.
 */
public class Simplify {
    /**
     * Ruta al archivo CSV que contiene los datos.
     */
    private String csvPath;

    /**
     * Constructor que inicializa la ruta del archivo CSV.
     * @param csvPath La ruta al archivo CSV.
     */
    public Simplify(String csvPath) {
        this.csvPath = csvPath;
    }

    /**
     * Ejecuta el clustering utilizando el método y parámetros especificados.
     * @param method El método de clustering ("kmeans" o "hierarchical").
     * @param numClusters El número de clusters deseados.
     * @param metric La métrica de distancia a utilizar.
     * @param linkageType El tipo de enlace (solo para clustering jerárquico).
     */
    public void performClustering(String method, int numClusters, DistanceMetric metric, LinkageType linkageType) {
        // Cargar los datos desde el CSV
        DataLoader dataLoader = new DataLoader();
        List<DataPoint> dataPoints = dataLoader.loadFromCSV(csvPath);

        // Validar que se hayan cargado datos
        if (dataPoints.isEmpty()) {
            System.err.println("No se pudieron cargar datos desde el archivo: " + csvPath);
            return;
        }

        // Mostrar los datos cargados
        System.out.println("Datos cargados:");
        for (DataPoint point : dataPoints) {
            System.out.println("Características: " + java.util.Arrays.toString(point.getFeatures()) + ", Etiqueta: " + point.getLabel());
        }

        // Determinar el método de clustering a utilizar
        if (method.equalsIgnoreCase("kmeans")) {
            System.out.println("\nAplicando K-Means Clustering...");
            KMeans kMeans = new KMeans(numClusters, 100, metric);
            List<Cluster> clusters = kMeans.fit(dataPoints);

            // Imprimir los clusters formados
            printClusters(clusters, "K-Means");

        } else if (method.equalsIgnoreCase("hierarchical")) {
            System.out.println("\nAplicando Hierarchical Clustering...");
            HierarchicalClustering hierarchicalClustering = new HierarchicalClustering(metric, linkageType, numClusters);
            List<Cluster> clusters = hierarchicalClustering.fit(dataPoints);

            // Imprimir los clusters formados
            printClusters(clusters, "Hierarchical Clustering");
        } else {
            System.err.println("Método desconocido: " + method);
        }
    }

    /**
     * Imprime los clusters resultantes en la consola.
     * @param clusters Lista de clusters a imprimir.
     * @param methodName El nombre del método de clustering utilizado.
     */
    private void printClusters(List<Cluster> clusters, String methodName) {
        System.out.println("\nClusters formados por " + methodName + ":");
        for (int i = 0; i < clusters.size(); i++) {
            Cluster cluster = clusters.get(i);
            System.out.println("Cluster " + (i + 1) + ":");
            for (DataPoint point : cluster.getPoints()) {
                System.out.println("  Características: " + java.util.Arrays.toString(point.getFeatures()) + ", Etiqueta: " + point.getLabel());
            }
        }
    }
}