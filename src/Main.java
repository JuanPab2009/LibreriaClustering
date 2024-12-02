import iteso.clusters.*;

public class Main {
    public static void main(String[] args) {

        String csvPath = "data/IRIS.csv";

        Simplify simplify = new Simplify(csvPath);

        simplify.performClustering("kmeans", 3, DistanceMetric.EUCLIDEAN, null);
        simplify.performClustering("hierarchical", 3, DistanceMetric.EUCLIDEAN, LinkageType.SINGLE);
    }
}