package iteso.clusters;

/**
 * Clase que representa un punto de datos con características y una etiqueta.
 */
public class DataPoint {
    /**
     * Array de características numéricas del punto de datos.
     */
    private double[] features;

    /**
     * Etiqueta asociada al punto de datos.
     */
    private String label;

    /**
     * Constructor que inicializa las características y la etiqueta del punto de datos.
     * @param features Array de características.
     * @param label Etiqueta del punto de datos.
     */
    public DataPoint(double[] features, String label) {
        this.features = features;
        this.label = label;
    }

    /**
     * Obtiene las características del punto de datos.
     * @return Array de características.
     */
    public double[] getFeatures() {
        return features;
    }

    /**
     * Establece nuevas características para el punto de datos.
     * @param features Array de nuevas características.
     */
    public void setFeatures(double[] features) {
        this.features = features;
    }

    /**
     * Calcula la distancia a otro punto de datos usando una métrica específica.
     * @param other El otro punto de datos.
     * @param metric La métrica de distancia a utilizar.
     * @return La distancia calculada.
     */
    public double distanceTo(DataPoint other, DistanceMetric metric) {
        return Utils.calculateDistance(this.features, other.getFeatures(), metric);
    }

    /**
     * Obtiene la etiqueta del punto de datos.
     * @return La etiqueta como una cadena.
     */
    public String getLabel() {
        return label;
    }
}