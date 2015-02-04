import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.porourke.springinaction.firstproject.ch11hibernate.Department;
import org.porourke.springinaction.firstproject.ch11hibernate.Employee;
import org.porourke.springinaction.firstproject.ch11hibernate.HibernateUtil;


public class HibernateTest {
	public static void main(String[] args) {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
  
        session.beginTransaction();
 
        Department department = new Department("java");
        session.save(department);
 
        session.save(new Employee("Jakab Gipsz",department));
        session.save(new Employee("Captain Nemo",department));
      
        session.getTransaction().commit();
 
        Query q = session.createQuery("From Employee ");
                 
        List<Employee> resultList = q.list();
        System.out.println("num of employess:" + resultList.size());
        for (Employee next : resultList) {
            System.out.println("next employee: " + next);
        }
 
    }
}
