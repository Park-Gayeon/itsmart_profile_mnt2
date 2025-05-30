package kr.co.itsm.profileMnt.dao;

import kr.co.itsm.profileMnt.dto.LoginDto;
import kr.co.itsm.profileMnt.dto.TbCommonCodeDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface CommonDAO {
    /* 파일 테이블 file_seq 생성 */
    @Select("SELECT COALESCE(MAX(FILE_SEQ + 1), 1) AS FILE_SEQ FROM TB_ATTACHMENT_INFO WHERE USER_ID = #{userId}")
    int getNextSeq(String userId);

    /* code_group_id로 list 조회 */
    List<TbCommonCodeDto> getCodeList(Map<String, String> params);

    /* 단일 value 조회 */
    @Select("SELECT CODE_VALUE FROM TB_COMMON_CODE WHERE CODE_GROUP_ID = #{codeGroupId} AND CODE_ID = #{codeId}")
    String codeValue(String codeGroupId, String codeId);

    /* 로그인 유저 조회 */
    @Select("SELECT USER_ID, USER_PW, USER_ROLE FROM TB_USER_PROFILE_INFO WHERE USER_ID = #{userId} AND USE_YN = 'Y'")
    Optional<LoginDto> getUsrInfo(String userId);
}
