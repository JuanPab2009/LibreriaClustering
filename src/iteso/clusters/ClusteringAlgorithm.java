package iteso.clusters;

import java.util.List;

/**
 * Interfaz para algoritmos de clustering.
 */
public interface ClusteringAlgorithm {
    /**
     * Ejecuta el algoritmo de clustering sobre los datos proporcionados.
     * @param data Lista de puntos de datos a clusterizar.
     * @return Lista de clusters resultantes.
     */
    List<Cluster> fit(List<DataPoint> data);
}