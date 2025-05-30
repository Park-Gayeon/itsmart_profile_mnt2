package kr.co.itsm.profileMnt.service.impl;

import kr.co.itsm.profileMnt.dao.ProjectDAO;
import kr.co.itsm.profileMnt.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectDAO projectDAO;

    @Override
    public Integer calcTotalMonth(String userId) {
        return projectDAO.calcTotalMonth(userId);
    }
}
