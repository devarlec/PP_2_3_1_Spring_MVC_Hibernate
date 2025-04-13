package web.dao;

import web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    private final EntityManagerFactory emf;

    @Autowired
    public UserDaoImp(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void add(User user) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }


    @Override
    public void delete(Long id) {

        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            User user = em.find(User.class, id);
            if (user == null) {
                throw new EntityNotFoundException("User with id " + id + " not found");
            }
            em.remove(user);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }


    @Override
    public List<User> listUsers() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            List<User> users = em.createQuery("from User", User.class).getResultList();
            em.getTransaction().commit();
            return users;
        } finally {
            em.close();
        }
    }

}
