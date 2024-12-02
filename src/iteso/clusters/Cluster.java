package iteso.clusters;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un cluster de puntos de datos.
 */
public class Cluster {
    /**
     * Lista de puntos que pertenecen al cluster.
     */
    private List<DataPoint> points = new ArrayList<>();

    /**
     * Centroide del cluster.
     */
    private DataPoint centroid;

    /**
     * Agrega un punto de datos al cluster.
     * @param point El punto de datos a agregar.
     */
    public void addPoint(DataPoint point) {
        points.add(point);
    }

    /**
     * Elimina todos los puntos de datos del cluster.
     */
    public void clearPoints() {
        points.clear();
    }

    /**
     * Obtiene la lista de puntos de datos en el cluster.
     * @return Lista de puntos de datos.
     */
    public List<DataPoint> getPoints() {
        return points;
    }

    /**
     * Obtiene el centroide del cluster.
     * @return El centroide como un objeto DataPoint.
     */
    public DataPoint getCentroid() {
        return centroid;
    }

    /**
     * Establece el centroide del cluster.
     * @param centroid El nuevo centroide.
     */
    public void setCentroid(DataPoint centroid) {
        this.centroid = centroid;
    }
}