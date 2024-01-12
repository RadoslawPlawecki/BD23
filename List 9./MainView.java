package entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.util.List;

public class MainView {
    public static void main(String[] args) {
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("default")) {
            EntityManager em = emf.createEntityManager();
            var queryString = "SELECT mve.category, ROUND(AVG(mve.vitA), 2), " +
                    "ROUND(AVG(mve.vitC), 2), ROUND(AVG(mve.iron), 2), " +
                    "ROUND(AVG(mve.calcium), 2) FROM MyViewEntity mve " +
                    "GROUP BY mve.category";
            Query query = em.createQuery(queryString);
            List<Object[]> results = query.getResultList();
            for (Object[] result : results) {
                CategoriesEntity categoriesEntity = (CategoriesEntity) result[0];
                String category = categoriesEntity.getCatName();
                double averageVitA = (double) result[1];
                double averageVitC = (double) result[2];
                double averageIron = (double) result[3];
                double averageCalcium = (double) result[4];
                System.out.printf("Category: %s. \n" +
                        "Average value of vitamin A: %s%% daily requirement. \n" +
                        "Average value of vitamin C: %s%% daily requirement. \n" +
                        "Average value of iron: %s%% daily requirement. \n" +
                        "Average value of calcium: %s%% daily requirement. \n" +
                        "-------------------------------------------------- \n",
                        category, averageVitA, averageVitC, averageIron, averageCalcium);
            }
        }
    }
}
