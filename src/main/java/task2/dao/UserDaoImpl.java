package task2.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task2.model.User;
import task2.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private static final Logger log = LoggerFactory.getLogger(UserDaoImpl.class);

    @Override
    public void save(User user) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            log.info("Пользователь сохранен: " + user.getEmail());
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error("Ошибка при сохранении пользователя", e);
        }
    }

    @Override
    public User findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(User.class, id);
        } catch (HibernateException e) {
            log.error("Ошибка при поиске пользователя по id: " + id, e);
            return null;
        }
    }

    @Override
    public List<User> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from User", User.class).list();
        } catch (HibernateException e) {
            log.error("Ошибка при получении списка пользователей", e);
            return new ArrayList<>();
        }
    }

    @Override
    public void update(User user) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();
            log.info("Пользователь обновлен: " + user.getId());
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error("Ошибка при обновлении пользователя", e);
        }
    }

    @Override
    public void delete(Long id) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);

            if (user != null) {
                session.remove(user);
                log.info("Пользователь удален: " + id);
            } else {
                System.out.println("Пользователь не найден");
            }

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error("Ошибка при удалении пользователя", e);
        }
    }
}
