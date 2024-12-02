package iteso.clusters;

/**
 * Clase de utilidades con métodos estáticos.
 */
public class Utils {
    /**
     * Calcula la distancia entre dos arrays de características utilizando una métrica específica.
     * @param features1 Primer array de características.
     * @param features2 Segundo array de características.
     * @param metric La métrica de distancia a utilizar.
     * @return La distancia calculada.
     */
    public static double calculateDistance(double[] features1, double[] features2, DistanceMetric metric) {
        double distance = 0.0;
        switch (metric) {
            case EUCLIDEAN:
                // Calcular la suma de las diferencias al cuadrado
                for (int i = 0; i < features1.length; i++) {
                    distance += Math.pow(features1[i] - features2[i], 2);
                }
                // Obtener la raíz cuadrada de la suma
                distance = Math.sqrt(distance);
                break;
            case MANHATTAN:
                // Calcular la suma de las diferencias absolutas
                for (int i = 0; i < features1.length; i++) {
                    distance += Math.abs(features1[i] - features2[i]);
                }
                break;
            default:
                throw new IllegalArgumentException("Métrica no soportada");
        }
        return distance;
    }
}