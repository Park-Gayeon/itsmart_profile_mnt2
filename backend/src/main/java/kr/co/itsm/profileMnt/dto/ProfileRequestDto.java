package kr.co.itsm.profileMnt.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProfileRequestDto {
    private TbUserProfileInfoDto profileInfo;
    private List<TbUserEducationInfoDto> educationInfo;
    private List<TbUserQualificationInfoDto> qualificationInfo;
    private List<TbWorkExperienceInfoDto> experienceInfo;
    private List<TbProjectInfoDto> projectInfo;
}
