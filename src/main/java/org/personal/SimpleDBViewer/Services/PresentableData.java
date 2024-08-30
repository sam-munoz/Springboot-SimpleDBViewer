package org.personal.SimpleDBViewer.Services;

import java.util.ArrayList;
import java.util.List;

import org.personal.SimpleDBViewer.CRUDRepository.CPUListEntityCRUDRepository;
import org.personal.SimpleDBViewer.CRUDRepository.UsersEntityCRUDRepository;
import org.personal.SimpleDBViewer.Domain.CPURankingSummaryEntity;
import org.personal.SimpleDBViewer.Domain.UsersCPURankingEntity;
import org.personal.SimpleDBViewer.Services.PresentableEntity.PresentableRanking;
import org.personal.SimpleDBViewer.Services.PresentableEntity.PresentableSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PresentableData {
    @Autowired
    private CPUListEntityCRUDRepository cpuRepo;

    @Autowired
    private UsersEntityCRUDRepository userRepo;

    public List<PresentableSummary> makeSummaryPresentable(List<CPURankingSummaryEntity> summaries) {
        List<PresentableSummary> presentableList = new ArrayList<PresentableSummary>();
        for(CPURankingSummaryEntity s : summaries) {
            PresentableSummary ps = new PresentableSummary();
            ps.setCount(s.getCount());
            ps.setRankSum(s.getRankSum());
            ps.computeAverage();
            ps.setCpuName(cpuRepo.getCPU(s.getId()).getName());
            presentableList.add(ps);
        }
        return presentableList;
    }

    public List<PresentableRanking> makeRankingPresentable(List<UsersCPURankingEntity> rankings) {
        List<PresentableRanking> presentableList = new ArrayList<PresentableRanking>();
        for(UsersCPURankingEntity r : rankings) {
            PresentableRanking pr = new PresentableRanking();
            pr.setRanking(r.getRanking());
            pr.setUserName(userRepo.getUser(r.getId().getUserId()).getName());
            pr.setCpuName(cpuRepo.getCPU(r.getId().getCpuId()).getName());
            presentableList.add(pr);
        }
        return presentableList;
    }
}
