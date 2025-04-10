package web.dao;

import org.springframework.context.annotation.ComponentScan;
import web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Repository
@ComponentScan("web")
public class UserDaoImp implements UserDao {

    //@Autowired
    //private SessionFactory sessionFactory;

    @Autowired
    private EntityManagerFactory emf;

    @Override
    public void add(User user) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }

    @Override
    public void delete(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User user = em.find(User.class,id);

        if (user != null) {
            em.remove(user);
        }
        em.getTransaction().commit();
    }

    @Override
    public List<User> listUsers() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<User> users = em.createQuery("from User").getResultList();
        em.getTransaction().commit();
        return users;
    }

}
