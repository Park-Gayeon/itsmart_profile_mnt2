package kr.co.itsm.profileMnt.repository;

import kr.co.itsm.profileMnt.domain.TbUserProfileInfoHist;
import kr.co.itsm.profileMnt.domain.TbUserProfileInfoHistId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TbUserProfileInfoHistRepository extends JpaRepository<TbUserProfileInfoHist, TbUserProfileInfoHistId> {
}