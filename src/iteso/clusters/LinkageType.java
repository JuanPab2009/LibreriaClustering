package iteso.clusters;

/**
 * Enumeración de los tipos de enlace disponibles para clustering jerárquico.
 */
public enum LinkageType {
    /**
     * Enlace simple: usa la distancia mínima entre puntos de diferentes clusters.
     */
    SINGLE,
    /**
     * Enlace completo: usa la distancia máxima entre puntos de diferentes clusters.
     */
    COMPLETE
}