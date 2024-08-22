package org.personal.SimpleDBViewer.EntityServices;

import org.personal.SimpleDBViewer.CRUDRepository.CPUListCRUDRepositoryInterface;
import org.personal.SimpleDBViewer.CRUDRepository.CPUListEntityCRUDRepository;
import org.personal.SimpleDBViewer.domain.CPUListEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;
import java.util.List;

@Service
public class CPUListEntityService {
    @Autowired
    private CPUListCRUDRepositoryInterface repo;

    public CPUListEntity createCPU(CPUListEntity cpu) {
        return this.repo.save(cpu);
    }

    public CPUListEntity getCPU(CPUListEntity cpu) throws NoSuchElementException {
        try {
            return this.repo.findById(cpu.getId()).orElseThrow();
        } catch(NoSuchElementException e) {
            throw e;
        }
    }

//    public CPUListEntity getCPU(String cpuName) throws NoSuchElementException {
//        try {
//            CPUListEntity c = new CPUListEntity();
//            c.setName(cpuName);
//            return this.repo.findOne(c).get();
//        } catch(NoSuchElementException e) {
//            throw e;
//        }
//    }

    public List<CPUListEntity> getAllCPUs() {
        return this.repo.findAll();
    }

    public CPUListEntity updateCPU(CPUListEntity cpu) {
        return this.repo.save(cpu);
    }

    public void deleteCPU(CPUListEntity cpu) {
        this.repo.delete(cpu);
    }

}
