package org.personal.SimpleDBViewer.CRUDRepository;

import java.util.List;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.personal.SimpleDBViewer.Domain.UsersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UsersEntityCRUDRepository {
    @Autowired
    private AbstractCRUDRepository repo;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public UsersEntity createUser(UsersEntity user) throws IllegalArgumentException {
        // valid input
        if(user == null) {
            throw new IllegalArgumentException("Input User object cannot be null");
        }

        return (UsersEntity) this.repo.createEntity(user);
    }

    public UsersEntity createUser(String userName, String userPasswd) throws IllegalArgumentException {
        // validate user input. userName cannot be empty
        if(userName.isEmpty()) {
            throw new IllegalArgumentException("userName cannot be a empty, i.e. length of userName must be >= 1");
        }

        UsersEntity user = new UsersEntity();
        user.setName(userName);
        user.setPasswd(userPasswd);
        return (UsersEntity) this.repo.createEntity(user);
    }

    public UsersEntity createUser(String userName) throws IllegalArgumentException {
        // validate user input. userName cannot be empty
        if(userName.isEmpty()) {
            throw new IllegalArgumentException("userName cannot be a empty, i.e. length of userName must be >= 1");
        }

        UsersEntity user = new UsersEntity();
        user.setName(userName);
        user.setPasswd(null);
        return (UsersEntity) this.repo.createEntity(user);
    }

    public UsersEntity getUser(Long userId) {
        // validate user input. userId cannot be empty
        if(userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }

        return (UsersEntity) this.repo.getEntityById(userId, UsersEntity.class);
    }

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
	public UsersEntity updateUser(UsersEntity user) throws IllegalArgumentException {
		if(user == null) {
			throw new IllegalArgumentException("Input User object cannot be null");
		}

        return (UsersEntity) this.repo.updateEntity(user);
	}

    public void deleteUser(UsersEntity user) throws IllegalArgumentException {
        if(user == null) {
            throw new IllegalArgumentException("Input User object cannot be null");
        }

        this.repo.deleteEntity(user.getId(), UsersEntity.class);
    }
}