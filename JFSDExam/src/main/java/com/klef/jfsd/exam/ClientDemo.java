package com.klef.jfsd.exam;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;


public class ClientDemo {
    private static SessionFactory factory;

    public static void main(String[] args) {
        factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        insertProjects();
        performAggregateFunctions();
    }

    public static void insertProjects() {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();

            Project p1 = new Project("AI System", 12, 50000, "Alice");
            Project p2 = new Project("Web App", 6, 20000, "Bob");
            Project p3 = new Project("Mobile App", 8, 30000, "Charlie");
            Project p4 = new Project("Data Migration", 10, 45000, "David");

            session.persist(p1);
            session.persist(p2);
            session.persist(p3);
            session.persist(p4);

            tx.commit();
            System.out.println("Projects inserted successfully.");
        }
    }

    public static void performAggregateFunctions() {
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();

            
            CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
            Root<Project> root = countQuery.from(Project.class);
            countQuery.select(builder.count(root));
            long count = session.createQuery(countQuery).getSingleResult();
            System.out.println("Total Projects: " + count);

            
            CriteriaQuery<Double> maxQuery = builder.createQuery(Double.class);
            maxQuery.select(builder.max(root.get("budget")));
            double maxBudget = session.createQuery(maxQuery).getSingleResult();
            System.out.println("Maximum Budget: " + maxBudget);

            
            CriteriaQuery<Double> minQuery = builder.createQuery(Double.class);
            minQuery.select(builder.min(root.get("budget")));
            double minBudget = session.createQuery(minQuery).getSingleResult();
            System.out.println("Minimum Budget: " + minBudget);

         
            CriteriaQuery<Double> sumQuery = builder.createQuery(Double.class);
            sumQuery.select(builder.sum(root.get("budget")));
            double totalBudget = session.createQuery(sumQuery).getSingleResult();
            System.out.println("Total Budget: " + totalBudget);

           
            CriteriaQuery<Double> avgQuery = builder.createQuery(Double.class);
            avgQuery.select(builder.avg(root.get("budget")));
            double avgBudget = session.createQuery(avgQuery).getSingleResult();
            System.out.println("Average Budget: "+avgBudget);

    }
}
}
