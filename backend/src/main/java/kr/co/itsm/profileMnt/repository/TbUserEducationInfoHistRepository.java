package kr.co.itsm.profileMnt.repository;

import kr.co.itsm.profileMnt.domain.TbUserEducationInfoHist;
import kr.co.itsm.profileMnt.domain.TbUserEducationInfoHistId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TbUserEducationInfoHistRepository extends JpaRepository<TbUserEducationInfoHist, TbUserEducationInfoHistId> {
}
