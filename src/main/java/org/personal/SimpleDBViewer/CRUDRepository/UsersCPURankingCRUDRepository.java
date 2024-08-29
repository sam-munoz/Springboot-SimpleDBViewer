package org.personal.SimpleDBViewer.CRUDRepository;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.personal.SimpleDBViewer.Domain.CPUListEntity;
import org.personal.SimpleDBViewer.Domain.UsersCPURankingEntity;
import org.personal.SimpleDBViewer.Domain.UsersCPURankingId;
import org.personal.SimpleDBViewer.Domain.UsersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UsersCPURankingCRUDRepository {
    @Autowired
    private AbstractCRUDRepository repo;

    @Autowired
    private CPUListEntityCRUDRepository cpuRepo;

    @Autowired
    private UsersEntityCRUDRepository userRepo;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public UsersCPURankingEntity createRanking(UsersCPURankingEntity ranking) throws NullPointerException, IllegalArgumentException {
       if(ranking == null) {
           throw new NullPointerException("Input ranking cannot be null");
       }
       if(ranking.getCpu() == null || ranking.getUser() == null || ranking.getRanking() == null) {
           throw new IllegalArgumentException("Input cannot have any null fields");
       }
       return (UsersCPURankingEntity) repo.createEntity(ranking);
    }

    public UsersCPURankingEntity getRanking(UsersCPURankingId id) throws NullPointerException {
        if(id == null) {
            throw new NullPointerException("Input ranking object is null");
        }

        return repo.getEntityById(id, UsersCPURankingEntity.class);
    }

    /**
     * THIS METHOD NEEDS SOME REFACTORING. IT SUCKS!!!!!
     * @param ranking
     * @return
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
	public UsersCPURankingEntity getRanking(UsersCPURankingEntity ranking) throws NullPointerException, IllegalArgumentException {
        if(ranking == null) {
            throw new NullPointerException("Input ranking object is null");
        }
        if(ranking.getRanking() == null) {
            throw new IllegalArgumentException("Input ranking object has a null id object");
        }

        if(!ranking.getId().hasNullId()) {
            return getRanking(ranking.getId());
        } else {
            if(ranking.getCpu() == null || ranking.getUser() == null) throw new IllegalArgumentException("Input ranking is missing data from its primary key");
            if(ranking.getCpu().getId() == null && ranking.getCpu().getName() == null) throw new IllegalArgumentException("Input ranking contains a CPU with no identifiable data");
            if(ranking.getUser().getId() == null && ranking.getUser().getName() == null) throw new IllegalArgumentException("Input ranking contains a User with no identifiable data");
            CPUListEntity queryCPU = cpuRepo.getCPU(ranking.getCpu());
            UsersEntity queryUser = userRepo.getUser(ranking.getUser());
            if(queryCPU.getId() == null) {
                throw new IllegalArgumentException("Input ranking passed CPU that does not exist in the database");
            } else {
                ranking.getId().setCpuId(queryCPU.getId());
            }
            if(queryUser.getId() == null) {
                throw new IllegalArgumentException("Input ranking passed User that does not exist in the database");
            } else {
                ranking.getId().setUserId(queryUser.getId());
            }
            return getRanking(ranking.getId());
        }
	}

	public List<UsersCPURankingEntity> getAllRankings() {
        EntityManager em = this.entityManagerFactory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<UsersCPURankingEntity> allRankings = null;
        try {
            tx.begin();
            allRankings = em.createQuery("SELECT r FROM UsersCPURankingEntity r", UsersCPURankingEntity.class)
                            .getResultList();
            tx.commit();
        } catch(Exception e) {

        } finally {
            em.close();
        }
        return allRankings;
	}

	public void deleteRanking(UsersCPURankingEntity ranking) throws NullPointerException, IllegalArgumentException {
        if(ranking == null) {
            throw new NullPointerException("Input ranking is null");
        }
        if(ranking.getId() == null) {
            throw new IllegalArgumentException("Input ranking has a null id object");
        }

        // either id is valid primary key or not. catch error during testing
        repo.deleteEntity(ranking.getId(), UsersCPURankingEntity.class);
	}

    public void deleteAllRankings() {
        List<UsersCPURankingEntity> allRankings = getAllRankings();
        for(UsersCPURankingEntity r : allRankings) deleteRanking(r);
    }
}
