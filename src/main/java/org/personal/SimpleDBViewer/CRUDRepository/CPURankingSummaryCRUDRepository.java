package org.personal.SimpleDBViewer.CRUDRepository;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.personal.SimpleDBViewer.Domain.CPUListEntity;
import org.personal.SimpleDBViewer.Domain.CPURankingSummaryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CPURankingSummaryCRUDRepository {
    @Autowired
    private AbstractCRUDRepository repo;

    @Autowired
    private CPUListEntityCRUDRepository cpuRepo;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    /**
     * Creates a new <code>CPURankingSummaryEntity</code> based on the provided inputs
     * @param summary The new <code>CPURankingSummaryEntity</code> to create
     * @return The newly created <code>CPURankingSummaryEntity</code> object
     * @throws NullPointerException Occurs if <code>summary</code> is null
     * @throws IllegalArgumentException Occurs if <code>summary.getCPU()</code> returns null
     */
    public CPURankingSummaryEntity createSummary(CPURankingSummaryEntity summary) throws NullPointerException, IllegalArgumentException {
        // ensure input is not null
        if(summary == null) {
            throw new NullPointerException("Summary input cannot be null");
        }

        // ensure that a cpu was passed into the summary
        if(summary.getCPU() == null) {
            throw new IllegalArgumentException("Cannot create a summary with a null CPU");
        }

        return (CPURankingSummaryEntity) this.repo.createEntity(summary);
    }

    /**
     * Queries the database to get a <code>CPURankingSummaryEntity</code>
     * @param summaryId The id of the <code>CPURankingSummaryEntity</code> to query
     * @return The <code>CPURankingSummaryEntity</code> corresponding to the inputs provided if it exists. If not, method returns null
     * @throws NullPointerException If <code>summaryId</code> is null
     */
    public CPURankingSummaryEntity getSummary(Long summaryId) throws NullPointerException {
        if(summaryId == null) {
            throw new NullPointerException("Summary object cannot be null");
        }

        return this.repo.getEntityById(summaryId, CPURankingSummaryEntity.class);
    }

    /**
     * Queries the database to get a <code>CPURankingSummaryEntity</code>
     * @param cpuName The CPU name of the <code>CPUListEntity</code> contained in the <code>CPURankingSummaryEntity</code> to query
     * @return The <code>CPURankingSummaryEntity</code> corresponding to the inputs provided if it exists. If not, method returns null
     * @throws NullPointerException If <code>cpuName</code> is null
     */
    public CPURankingSummaryEntity getSummary(String cpuName) throws NullPointerException {
        if(cpuName == null) {
            throw new NullPointerException("Input cpu name is null");
        }

        // get the correct cpu from the database
        CPUListEntity cpu = cpuRepo.getCPU(cpuName);
        if(cpu == null) {
            return null;
        } else {
            return this.repo.getEntityById(cpu.getId(), CPURankingSummaryEntity.class);
        }
    }

    /**
     * Queries the database to get a <code>CPURankingSummaryEntity</code>
     * @param cpu The <code>CPUListEntity</code> contained in the <code>CPURankingSummaryEntity</code> to query
     * @return The <code>CPURankingSummaryEntity</code> corresponding to the inputs provided if it exists. If not, method returns null
     * @throws NullPointerException If <code>cpu</code> is null
     * @throws IllegalArgumentException If <code>cpu</code> is not a persistent object
     */
    public CPURankingSummaryEntity getSummary(CPUListEntity cpu) throws NullPointerException, IllegalArgumentException {
        if(cpu == null) {
            throw new NullPointerException("Summary object cannot be null");
        }
        if(cpu.getId() == null && cpu.getName() == null) {
            throw new IllegalArgumentException("Input CPU only has null fields");
        }

        if(cpu.getId() != null) {
            return this.repo.getEntityById(cpu.getId(), CPURankingSummaryEntity.class);
        } else {
            return getSummary(cpu.getName());
        }
    }

    /**
     * Queries the database to get a <code>CPURankingSummaryEntity</code>
     * @param summary The <code>CPURankingSummaryEntity</code> to query
     * @return The <code>CPURankingSummaryEntity</code> corresponding to the inputs provided if it exists. If not, method returns null
     * @throws NullPointerException If <code>summary</code> is null
     * @throws IllegalArgumentException If <code>summary</code> does not contain an identifier
     */
    public CPURankingSummaryEntity getSummary(CPURankingSummaryEntity summary) throws NullPointerException, IllegalArgumentException {
        if(summary == null) {
            throw new NullPointerException("Summary object cannot be null");
        }

        if(summary.getId() != null) {
            return getSummary(summary.getId());
        } else if(summary.getCPU() != null) {
            return getSummary(summary.getCPU());
        } else {
            throw new IllegalArgumentException("Input summary does not contain a unique identifier. Therefore, we cannot query a summary from the database");
        }
    }

    /**
     * Queries all <code>CPURankingSummaryEntities</code> in the database
     * @return Returns all <code>CPURankingSummaryEntities</code> in the database
     */
    public List<CPURankingSummaryEntity> getAllSummaries() {
        EntityManager em = this.entityManagerFactory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<CPURankingSummaryEntity> summaryList = null;
        try {
            tx.begin();
            summaryList = em.createQuery("SELECT s FROM CPURankingSummaryEntity s", CPURankingSummaryEntity.class)
                    .getResultList();
            tx.commit();
        } catch(Exception e) {
            if(tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
        return summaryList;
    }

    /**
     * Updates the input <code>CPURankingSummaryEntity</code> in the database
     * <br />
     * Due to the schema used for the database, the CPUListEntity is immutable. Hence, if the summary needs to update the <code>CPUListEntity</code>, it is necessary to delete the old summary and create an entirely new one to update the database
     * @param summary <code>CPURankingSummaryEntity</code> to update
     * @return The updated <code>CPURankingSummaryEntity</code>
     * @throws NullPointerException Throws this exception if the input is null
     * @throws IllegalArgumentException Throws this exception if the input is not in the persistence context
     */
    public CPURankingSummaryEntity updateSummary(CPURankingSummaryEntity summary) throws NullPointerException, IllegalArgumentException {
        if(summary == null) {
            throw new NullPointerException("Input summary is null");
        }
        if(summary.getId() == null) {
            throw new IllegalArgumentException("Input summary is not in the persistence context");
        }

        // update method differs based on the input
        if(summary.getId().equals(summary.getCPU().getId())) {
            return (CPURankingSummaryEntity) this.repo.updateEntity(summary);
        } else {
            // remove old summary
            CPURankingSummaryEntity oldSummary = getSummary(summary.getId()); // this will some error that I don't know of (when id is invalid)
            deleteSummary(oldSummary);
            // create the new summary
            summary.setId(null);
            return createSummary(summary);
        }
    }

    /**
     * Deletes <code>summary</code> from the database
     * @param summary <code>CPURankingSummaryEntity</code> to delete from the database
     * @throws NullPointerException Occurs if <code>summary</code> if null
     * @throws IllegalArgumentException Occurs if <code>summary</code> is not a persistent object
     */
    public void deleteSummary(CPURankingSummaryEntity summary) throws NullPointerException, IllegalArgumentException {
        if(summary == null) {
            throw new NullPointerException("Summary object cannot be null");
        }
        if(summary.getId() == null || summary.getCPU() == null || summary.getCPU().getId() == null) {
            throw new IllegalArgumentException("Summary object needs to be persistent to be deleted");
        }

        this.repo.deleteEntity(summary.getId(), CPURankingSummaryEntity.class);
    }

    /**
     * Deletes all <code>CPURankingSummaryEntity</code> in the database
     */
    public void deleteAllSummaries() {
        List<CPURankingSummaryEntity> allSummaries = getAllSummaries();
        for(CPURankingSummaryEntity s : allSummaries) deleteSummary(s);
    }
}
