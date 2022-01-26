package com.peaksoft;

import com.peaksoft.entity.Worker;
import com.peaksoft.util.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;

public class App {
    public static void main( String[] args ) {

        Worker worker1 = new Worker("Aza", 26);
        Worker worker2 = new Worker("Aiba", 18);
        Worker worker3 = new Worker("Aza", 17);
        Worker worker4 = new Worker("Aza", 30);
        Worker worker5 = new Worker("Mara", 22);
        create(worker1);
        create(worker2);
        create(worker3);
        create(worker4);
        create(worker5);

//        for (Worker w:getRequiredWorkers()) {
//            System.out.println(w);
//        }

//        update("Aza",18);

//        delete("Aza");
        HibernateUtil.shutDown();

    }

    public static int create(Worker w) {
        Session session = HibernateUtil.getSession().openSession();
        session.beginTransaction();
        session.save(w);
        session.getTransaction().commit();
        session.close();
        System.out.println("Added successfully " + w);
        return w.getId();
    }
    public static List<Worker> getRequiredWorkers() {
        Session session = HibernateUtil.getSession().openSession();
        session.beginTransaction();
        List<Worker> workers = session.createQuery("FROM Worker WHERE name LIKE 'Aza' AND " +
                "age > 20").getResultList();
        session.getTransaction().commit();
        session.close();
        System.out.println("Found " + workers.size() + " workers");
        return workers;
    }
    public static void update(String name, int age) {
        Session session = HibernateUtil.getSession().openSession();
        session.beginTransaction();
        String hql = "UPDATE Worker set age = :age "  +
                "WHERE name = :name";
        Query query = session.createQuery(hql);
        query.setParameter("age", age);
        query.setParameter("name", name);
        int result = query.executeUpdate();
        System.out.println("Rows affected: " + result);
        session.getTransaction().commit();
        session.close();
        System.out.println("Successfully updated!");
    }
    public static void delete(String name) {
        Session session = HibernateUtil.getSession().openSession();
        session.beginTransaction();
        String hql = "DELETE FROM Worker WHERE name = :worker_name";
        Query query = session.createQuery(hql);
        query.setParameter("worker_name", name);
        int result = query.executeUpdate();
        System.out.println("Rows affected: " + result);
        session.getTransaction().commit();
        session.close();
        System.out.println("Successfully deleted ");
    }
}
