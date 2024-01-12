package firstEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("default")) {
            EntityManager em = emf.createEntityManager();
            // item A
            var queryStringCountAll = "((SELECT COUNT(p) FROM ProductsEntity p))";
            var queryStringWhereClause = "p.calcium + p.iron > 50";
            var queryStringA01 = String.format("SELECT ROUND(100.0 * count(p) / %s, 2) FROM ProductsEntity p WHERE %s",
                    queryStringCountAll, queryStringWhereClause);
            var queryStringA02 = "SELECT ROUND(100.0 * COUNT(p) / ((SELECT COUNT(p2) FROM ProductsEntity p2)), 2) " +
                    "FROM ProductsEntity p WHERE p.calcium + p.iron > 50";
            Query queryA01 = em.createQuery(queryStringA01);
            Query queryA02 = em.createQuery(queryStringA02);
            double resultA01 = (double) queryA01.getSingleResult();
            double resultA02 = (double) queryA02.getSingleResult();
            System.out.printf("Percentage of products that meet the condition in the item A: %s%% \n", resultA01);
            System.out.printf("Percentage of products that meet the condition in the item A: %s%% \n", resultA02);
            // item B
            var queryStringB = "SELECT AVG(calories) FROM ProductsEntity p WHERE p.itemName LIKE '%bacon%'";
            Query queryB = em.createQuery(queryStringB);
            double resultB = (double) queryB.getSingleResult();
            System.out.printf("Average calorific value of products with bacon in the name: %s kcal \n",
                    resultB);
            // item C
            var queryStringC = "SELECT p.category, p.itemName FROM ProductsEntity p WHERE (p.category, " +
                    "p.cholesterole) IN (SELECT p2.category, MAX(p2.cholesterole) FROM ProductsEntity p2 " +
                    "GROUP BY p2.category)";
            Query queryC = em.createQuery(queryStringC);
            List<Object[]> resultsC = queryC.getResultList();
            for (Object[] resultC : resultsC) {
                CategoriesEntity categoriesEntity = (CategoriesEntity) resultC[0];
                String category = categoriesEntity.getCatName();
                String product = (String) resultC[1];
                System.out.printf("Category: %s. Product with the highest cholesterol: %s \n", category, product);
            }
            // item D
            var queryStringD = "SELECT COUNT(p) FROM ProductsEntity p WHERE (p.itemName LIKE '%mocha%' OR " +
                    "p.itemName LIKE '%coffee%') AND p.fiber = 0";
            Query queryD = em.createQuery(queryStringD);
            Long resultD = (Long) queryD.getSingleResult();
            System.out.printf("Number of coffees that do not contain fiber: %s \n", resultD);
            // item E
            var queryStringE = "SELECT p.itemName, ROUND(p.calories * 4.184, 2) " +
                    "FROM ProductsEntity p WHERE p.itemName LIKE '%McMuffin%' " +
                    "ORDER BY p.calories ASC";
            Query queryE = em.createQuery(queryStringE);
            List<Object[]> resultsE = queryE.getResultList();
            for (Object[] resultE : resultsE) {
                String product = (String) resultE[0];
                double calories = (double) resultE[1];
                System.out.printf("McMuffin: %s. Calorific value: %s kJ \n", product, calories);
            }
            // item F
            var queryStringF = "SELECT COUNT(DISTINCT(p.carbs)) FROM ProductsEntity p";
            Query queryF = em.createQuery(queryStringF);
            long resultF = (long) queryF.getSingleResult();
            System.out.printf("Number of different carbohydrate values: %s \n", resultF);
        }
    }
}
