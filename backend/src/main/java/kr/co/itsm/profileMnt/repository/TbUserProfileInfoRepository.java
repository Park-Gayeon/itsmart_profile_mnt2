package kr.co.itsm.profileMnt.repository;

import kr.co.itsm.profileMnt.domain.TbUserProfileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TbUserProfileInfoRepository extends JpaRepository<TbUserProfileInfo, String> {
    @Modifying
    @Query("UPDATE TbUserProfileInfo u SET u.userPw = :userPw WHERE u.userId = :userId")
    void updatePassword(@Param("userId") String userId, @Param("userPw") String userPw);
}
