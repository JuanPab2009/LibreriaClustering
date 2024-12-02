package iteso.clusters;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que implementa el algoritmo de clustering jerárquico.
 */
public class HierarchicalClustering implements ClusteringAlgorithm{
    /**
     * Métrica de distancia a utilizar (Euclidiana o Manhattan).
     */
    private DistanceMetric metric;

    /**
     * Tipo de enlace (simple o completo).
     */
    private LinkageType linkageType;

    /**
     * Número deseado de clusters al finalizar.
     */
    private int desiredClusterCount;

    /**
     * Constructor que inicializa los parámetros del algoritmo.
     * @param metric Métrica de distancia.
     * @param linkageType Tipo de enlace.
     * @param desiredClusterCount Número deseado de clusters.
     */
    public HierarchicalClustering(DistanceMetric metric, LinkageType linkageType, int desiredClusterCount) {
        this.metric = metric;
        this.linkageType = linkageType;
        this.desiredClusterCount = desiredClusterCount;
    }

    /**
     * Ejecuta el algoritmo de clustering jerárquico sobre los datos proporcionados.
     * @param data Lista de puntos de datos a clusterizar.
     * @return Lista de clusters resultantes.
     */
    @Override
    public List<Cluster> fit(List<DataPoint> data) {
        // Inicializar cada punto como su propio cluster
        List<Cluster> clusters = initializeClusters(data);

        // Fusionar clusters hasta llegar al número deseado
        while (clusters.size() > desiredClusterCount) {
            mergeClosestClusters(clusters);
        }

        return clusters;
    }

    /**
     * Crea un cluster para cada punto de datos.
     * @param data Lista de puntos de datos.
     * @return Lista inicial de clusters.
     */
    private List<Cluster> initializeClusters(List<DataPoint> data) {
        List<Cluster> clusters = new ArrayList<>();
        for (DataPoint point : data) {
            Cluster cluster = new Cluster();
            cluster.setCentroid(point); // El centroide inicial es el punto mismo
            cluster.addPoint(point);
            clusters.add(cluster);
        }
        return clusters;
    }

    /**
     * Fusiona los dos clusters más cercanos según la métrica y el tipo de enlace.
     * @param clusters Lista actual de clusters.
     */
    private void mergeClosestClusters(List<Cluster> clusters) {
        double minDistance = Double.MAX_VALUE;
        Cluster clusterA = null;
        Cluster clusterB = null;

        // Buscar los dos clusters más cercanos
        for (int i = 0; i < clusters.size(); i++) {
            for (int j = i + 1; j < clusters.size(); j++) {
                double distance = calculateClusterDistance(clusters.get(i), clusters.get(j));
                if (distance < minDistance) {
                    minDistance = distance;
                    clusterA = clusters.get(i);
                    clusterB = clusters.get(j);
                }
            }
        }

        // Fusionar los clusters encontrados
        if (clusterA != null && clusterB != null) {
            // Combinar los puntos de ambos clusters
            clusterA.getPoints().addAll(clusterB.getPoints());
            // Recalcular el centroide del cluster fusionado
            clusterA.setCentroid(recomputeCentroid(clusterA));
            // Remover el cluster que ha sido fusionado
            clusters.remove(clusterB);
        }
    }

    /**
     * Calcula la distancia entre dos clusters basándose en el tipo de enlace.
     * @param cluster1 El primer cluster.
     * @param cluster2 El segundo cluster.
     * @return La distancia calculada entre los clusters.
     */
    private double calculateClusterDistance(Cluster cluster1, Cluster cluster2) {
        double distance = linkageType == LinkageType.SINGLE ? Double.MAX_VALUE : Double.MIN_VALUE;

        for (DataPoint point1 : cluster1.getPoints()) {
            for (DataPoint point2 : cluster2.getPoints()) {
                double currentDistance = point1.distanceTo(point2, metric);

                if (linkageType == LinkageType.SINGLE) {
                    // Enlace simple: buscar la distancia mínima
                    distance = Math.min(distance, currentDistance);
                } else if (linkageType == LinkageType.COMPLETE) {
                    // Enlace completo: buscar la distancia máxima
                    distance = Math.max(distance, currentDistance);
                }
            }
        }

        return distance;
    }

    /**
     * Recalcula el centroide de un cluster.
     * @param cluster El cluster para el cual se recalculará el centroide.
     * @return Un nuevo DataPoint que representa el centroide.
     */
    private DataPoint recomputeCentroid(Cluster cluster) {
        int numFeatures = cluster.getPoints().get(0).getFeatures().length;
        double[] newCentroid = new double[numFeatures];

        // Sumar todas las características de los puntos en el cluster
        for (DataPoint point : cluster.getPoints()) {
            double[] features = point.getFeatures();
            for (int i = 0; i < numFeatures; i++) {
                newCentroid[i] += features[i];
            }
        }

        // Dividir entre el número de puntos para obtener el promedio
        for (int i = 0; i < newCentroid.length; i++) {
            newCentroid[i] /= cluster.getPoints().size();
        }

        // Crear un nuevo DataPoint sin etiqueta para el centroide
        return new DataPoint(newCentroid, null);
    }
}