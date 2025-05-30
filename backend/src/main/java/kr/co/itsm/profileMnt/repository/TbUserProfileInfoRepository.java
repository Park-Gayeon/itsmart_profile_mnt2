package kr.co.itsm.profileMnt.repository;

import kr.co.itsm.profileMnt.domain.TbUserProfileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TbUserProfileInfoRepository extends JpaRepository<TbUserProfileInfo, String> {
}
