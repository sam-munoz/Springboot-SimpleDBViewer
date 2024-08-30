package org.personal.SimpleDBViewer.CRUDRepository;

import java.util.List;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.validation.constraints.Null;
import org.apache.catalina.User;
import org.personal.SimpleDBViewer.Domain.UsersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

@Repository
public class UsersEntityCRUDRepository {
    @Autowired
    private AbstractCRUDRepository repo;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    /**
     * Creates a new <code>UsersEntity</code> object in the database
     * @param user <code>UsersEntity</code> object to create in the database
     * @return The <code>UsersEntity</code> created in the database
     * @throws NullPointerException Occurs if the <code>user</code> is null
     * @throws IllegalArgumentException Occurs if the <code>user</code> the id of the object is not null, i.e. the object is not transient
     */
    public UsersEntity createUser(UsersEntity user) throws NullPointerException, IllegalArgumentException {
        if(user == null) {
            throw new NullPointerException("Input User object cannot be null");
        }
        if(user.getId() != null) {
            throw new IllegalArgumentException("Attempted to create a new User object that is not transient object");
        }

        return (UsersEntity) this.repo.createEntity(user);
    }

    /**
     * Queries an <code>UsersEntity</code> from the database based on a id
     * @param userId The id used in the query
     * @return The queried <code>UsersEntity</code> object if it exists in the database. Otherwise, null
     * @throws NullPointerException Occurs if <code>userId</code> is null
     */
    public UsersEntity getUser(Long userId) throws NullPointerException {
        if(userId == null) {
            throw new NullPointerException("userId cannot be null");
        }

        return (UsersEntity) this.repo.getEntityById(userId, UsersEntity.class);
    }

    /**
     * Queries an <code>UsersEntity</code> from the database based on an user's name
     * @param userName The user's name used in the query
     * @return The queried <code>UsersEntity</code> object if it exists in the database. Otherwise, null
     * @throws NullPointerException Occurs if <code>userName</code> is null
     * @throws IllegalArgumentException Occurs if <code>userName</code> is an empty string
     */
    public UsersEntity getUser(String userName) throws NullPointerException, IllegalArgumentException {
        if(userName == null) {
            throw new NullPointerException("Query name is null");
        }
        if(userName.isEmpty()) {
            throw new IllegalArgumentException("Query name is empty");
        }

        EntityManager em = this.entityManagerFactory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        UsersEntity queryUser = null;
        try {
            tx.begin();
            queryUser = em.createQuery("SELECT u FROM UsersEntity u WHERE u.name = ?1", UsersEntity.class)
                            .setParameter(1, userName)
                            .getSingleResult();
            tx.commit();
        } catch(EmptyResultDataAccessException | NoResultException e) {
            queryUser = null;
        }
        catch(Exception e) {
            if(tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
        return queryUser;
    }

    /**
     * Queries an <code>UsersEntity</code> from the database based on an <code>UsersEntity</code> object
     * @param user The <code>UsersEntity</code> used in the query
     * @return The queried <code>UsersEntity</code> object if it exists in the database. Otherwise, null
     * @throws NullPointerException Occurs if <code>user</code> is null
     * @throws IllegalArgumentException Occurs if <code>user</code> does not have non-null identifier fields
     */
    public UsersEntity getUser(UsersEntity user) throws NullPointerException, IllegalArgumentException {
        // validate user input. userId cannot be empty
        if(user == null) {
            throw new NullPointerException("Input user is null");
        }

        if(user.getId() != null) {
            return getUser(user.getId());
        } else if(user.getName() != null) {
            return getUser(user.getName());
        } else {
            throw new IllegalArgumentException("Input user has no non-null, identifiable fields.");
        }

    }

    /**
     * Returns all the <code>UsersEntity</code> in the database
     * @return A list of all <code>UsersEntity</code> in the database
     */
    public List<UsersEntity> getAllUsers() {
        List<UsersEntity> rtnList = null;
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            rtnList = entityManager.createQuery("SELECT u from UsersEntity u", UsersEntity.class)
                    .getResultList();
            tx.commit();
        } catch(Exception e) {
            if(tx.isActive()) tx.rollback();
            throw e;
        } finally {
            entityManager.close();
        }
        return rtnList;
    }

    /**
     * Updates a persistent <code>UsersEntity</code> in the database
     * @param user The <code>UsersEntity</code> to update in the database
     * @return The updated <code>UsersEntity</code>
     * @throws NullPointerException Occurs if <code>user</code> is null
     * @throws IllegalArgumentException Occurs if <code>user</code> is not persistent
     */
	public UsersEntity updateUser(UsersEntity user) throws NullPointerException, IllegalArgumentException {
		if(user == null) {
			throw new NullPointerException("Input User object cannot be null");
		}
        if(user.getId() == null && user.getName() == null) {
            throw new IllegalArgumentException("Input User object has no non-null, identifiable fields");
        }

        // primary key if the object can be identified by the name field
        if(user.getId() == null && user.getName() != null) {
            user = getUser(user);
            if(user == null) {
                throw new IllegalArgumentException("Input User object does not exist in the database");
            }
        }

        return (UsersEntity) this.repo.updateEntity(user);
	}

    /**
     * Deletes a persistent <code>UsersEntity</code> in the database
     * @param user The <code>UsersEntity</code> to delete in the database
     * @throws NullPointerException Occurs if <code>user</code> is null
     * @throws IllegalArgumentException Occurs if <code>user</code> does not have a non-null, identifiable fields or the <code>user</code> is not in the database
     */
    public void deleteUser(UsersEntity user) throws NullPointerException, IllegalArgumentException {
        if(user == null) {
            throw new NullPointerException("Input User object cannot be null");
        }
        if(user.getId() == null && user.getName() == null) {
            throw new IllegalArgumentException("Input User object has no non-null, identifiable fields");
        }

        // primary key if the object can be identified by the name field
        if(user.getId() == null && user.getName() != null) {
            user = getUser(user);
            if(user == null) {
                throw new IllegalArgumentException("Input User object does not exist in the database");
            }
        }

        this.repo.deleteEntity(user.getId(), UsersEntity.class);
    }

    /**
     * Deletes all <code>UsersEntity</code> in the database
     */
    public void deleteAllUsers() {
        List<UsersEntity> allUsers = getAllUsers();
        for(UsersEntity u : allUsers) deleteUser(u);
    }
}