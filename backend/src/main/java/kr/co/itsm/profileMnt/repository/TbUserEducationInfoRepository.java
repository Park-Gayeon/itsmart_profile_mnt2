package kr.co.itsm.profileMnt.repository;

import kr.co.itsm.profileMnt.domain.TbUserEducationInfo;
import kr.co.itsm.profileMnt.domain.TbUserEducationInfoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TbUserEducationInfoRepository extends JpaRepository<TbUserEducationInfo, TbUserEducationInfoId> {
    void deleteByIdUserId(String userId);
    List<TbUserEducationInfo> findByIdUserId(String userId);
}
