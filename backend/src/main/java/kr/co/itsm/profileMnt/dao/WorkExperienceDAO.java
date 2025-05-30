package kr.co.itsm.profileMnt.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WorkExperienceDAO {
    /* 근무경력 totalMonth 조회 */
    Integer calcTotalMonth(String userId);
}
