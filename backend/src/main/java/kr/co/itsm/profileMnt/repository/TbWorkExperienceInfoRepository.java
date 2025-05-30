package kr.co.itsm.profileMnt.repository;

import kr.co.itsm.profileMnt.domain.TbWorkExperienceInfo;
import kr.co.itsm.profileMnt.domain.TbWorkExperienceInfoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TbWorkExperienceInfoRepository extends JpaRepository<TbWorkExperienceInfo, TbWorkExperienceInfoId> {
    List<TbWorkExperienceInfo> findByIdUserId(String userId);
}
