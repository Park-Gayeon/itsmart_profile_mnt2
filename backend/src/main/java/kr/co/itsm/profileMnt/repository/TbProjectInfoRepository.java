package kr.co.itsm.profileMnt.repository;

import kr.co.itsm.profileMnt.domain.TbProjectInfo;
import kr.co.itsm.profileMnt.domain.TbProjectInfoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TbProjectInfoRepository extends JpaRepository<TbProjectInfo, TbProjectInfoId> {
    List<TbProjectInfo> findByIdUserId(String userId);
}
