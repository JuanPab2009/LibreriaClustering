package iteso.clusters;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Clase que implementa el algoritmo K-Means para clustering.
 */
public class KMeans implements ClusteringAlgorithm {
    /**
     * Número de clusters a formar.
     */
    private int k;

    /**
     * Número máximo de iteraciones permitidas.
     */
    private int maxIterations;

    /**
     * Métrica de distancia a utilizar (Euclidiana o Manhattan).
     */
    private DistanceMetric metric;

    /**
     * Constructor que inicializa los parámetros del algoritmo K-Means.
     * @param k Número de clusters.
     * @param maxIterations Número máximo de iteraciones.
     * @param metric Métrica de distancia.
     */
    public KMeans(int k, int maxIterations, DistanceMetric metric) {
        this.k = k;
        this.maxIterations = maxIterations;
        this.metric = metric;
    }

    /**
     * Ejecuta el algoritmo K-Means sobre los datos proporcionados.
     * @param data Lista de puntos de datos a clusterizar.
     * @return Lista de clusters resultantes.
     */
    @Override
    public List<Cluster> fit(List<DataPoint> data) {
        // Inicializar clusters con centroides aleatorios
        List<Cluster> clusters = initializeClusters(data);
        for (int i = 0; i < maxIterations; i++) {
            // Asignar puntos a los clusters más cercanos
            assignPointsToClusters(data, clusters);
            // Recalcular los centroides de los clusters
            recomputeCentroids(clusters);
        }
        return clusters;
    }

    /**
     * Inicializa los clusters seleccionando centroides aleatorios.
     * @param data Lista de puntos de datos.
     * @return Lista inicial de clusters.
     */
    private List<Cluster> initializeClusters(List<DataPoint> data) {
        List<Cluster> clusters = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < k; i++) {
            Cluster cluster = new Cluster();
            // Seleccionar un punto aleatorio como centroide inicial
            cluster.setCentroid(data.get(random.nextInt(data.size())));
            clusters.add(cluster);
        }
        return clusters;
    }

    /**
     * Asigna cada punto de datos al cluster con el centroide más cercano.
     * @param data Lista de puntos de datos.
     * @param clusters Lista de clusters.
     */
    private void assignPointsToClusters(List<DataPoint> data, List<Cluster> clusters) {
        // Limpiar los puntos de cada cluster
        for (Cluster cluster : clusters) {
            cluster.clearPoints();
        }
        // Asignar cada punto al cluster más cercano
        for (DataPoint point : data) {
            Cluster bestCluster = null;
            double minDistance = Double.MAX_VALUE;
            for (Cluster cluster : clusters) {
                double distance = point.distanceTo(cluster.getCentroid(), metric);
                if (distance < minDistance) {
                    minDistance = distance;
                    bestCluster = cluster;
                }
            }
            if (bestCluster != null) {
                bestCluster.addPoint(point);
            }
        }
    }

    /**
     * Recalcula los centroides de los clusters basándose en los puntos asignados.
     * @param clusters Lista de clusters.
     */
    private void recomputeCentroids(List<Cluster> clusters) {
        for (Cluster cluster : clusters) {
            if (cluster.getPoints().isEmpty()) continue; // Saltar si el cluster no tiene puntos
            double[] newCentroid = new double[cluster.getCentroid().getFeatures().length];
            // Sumar las características de todos los puntos en el cluster
            for (DataPoint point : cluster.getPoints()) {
                double[] features = point.getFeatures();
                for (int i = 0; i < features.length; i++) {
                    newCentroid[i] += features[i];
                }
            }
            // Calcular el promedio de las características
            for (int i = 0; i < newCentroid.length; i++) {
                newCentroid[i] /= cluster.getPoints().size();
            }
            // Establecer el nuevo centroide
            cluster.setCentroid(new DataPoint(newCentroid, null));
        }
    }
}