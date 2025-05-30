package kr.co.itsm.profileMnt.dao;

import kr.co.itsm.profileMnt.dto.TbUserProfileInfoDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper
public interface ProfileDAO {
    /* 관리자 : 직원 목록 조회*/
    List<TbUserProfileInfoDto> getUsrProfileInfoList();

    /* 프로필 이력 테이블 hist_seq 생성 */
    @Select("SELECT COALESCE(MAX(HIST_SEQ) + 1, 1) AS HIST_SEQ FROM TB_USER_PROFILE_INFO_HIST WHERE USER_ID = #{userId}")
    int getMaxHistSeq(String userId);

    /* 프로필 상세화면 조회 */
    TbUserProfileInfoDto getUsrProfileDetail(String userId);
}
