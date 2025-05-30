package kr.co.itsm.profileMnt.service.impl;

import kr.co.itsm.profileMnt.dao.WorkExperienceDAO;
import kr.co.itsm.profileMnt.service.WorkExperienceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class WorkExperienceServiceImpl implements WorkExperienceService {
    private final WorkExperienceDAO workExperienceDAO;

    @Override
    public Integer calcTotalMonth(String userId) {
        return workExperienceDAO.calcTotalMonth(userId);
    }
}
