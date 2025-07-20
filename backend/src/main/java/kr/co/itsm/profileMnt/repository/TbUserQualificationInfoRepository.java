package kr.co.itsm.profileMnt.repository;

import kr.co.itsm.profileMnt.domain.TbUserQualificationInfo;
import kr.co.itsm.profileMnt.domain.TbUserQualificationInfoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TbUserQualificationInfoRepository extends JpaRepository<TbUserQualificationInfo, TbUserQualificationInfoId> {
    void deleteByIdUserId(String userId);
    List<TbUserQualificationInfo> findByIdUserId(String userId);
}
