package iteso.clusters;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase responsable de cargar datos desde un archivo CSV.
 */
public class DataLoader {
    /**
     * Carga puntos de datos desde un archivo CSV especificado.
     * @param filePath La ruta del archivo CSV.
     * @return Una lista de objetos DataPoint cargados desde el archivo.
     */
    public List<DataPoint> loadFromCSV(String filePath) {
        List<DataPoint> dataPoints = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Saltar la línea de encabezados
            while ((line = br.readLine()) != null) {
                // Separar los valores por coma
                String[] parts = line.split(",");
                // Crear un array para las características numéricas
                double[] features = new double[parts.length - 1];
                for (int i = 0; i < parts.length - 1; i++) {
                    // Convertir cada característica a un número
                    features[i] = Double.parseDouble(parts[i]);
                }
                // La última parte es la etiqueta
                String label = parts[parts.length - 1];
                // Crear un nuevo punto de datos y agregarlo a la lista
                dataPoints.add(new DataPoint(features, label));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataPoints;
    }
}