package kr.co.itsm.profileMnt.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kr.co.itsm.profileMnt.domain.TbAttachmentInfo;
import kr.co.itsm.profileMnt.domain.TbUserProfileInfo;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

/**
 * DTO for {@link kr.co.itsm.profileMnt.domain.TbUserProfileInfo}
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "사용자 프로필 정보")
public class TbUserProfileInfoDto implements Serializable {
    @Size(max = 10)
    private String userId;

    @NotNull
    @Size(max = 120)
    private String userPw;

    @NotNull
    @Size(max = 6)
    private String userNm;

    @NotNull
    @Size(max = 3)
    private String userPosition;

    @Size(max = 8)
    private String userBirth;

    @Size(max = 3)
    private String userDepartment;

    @Size(max = 8)
    private String hireDate;

    @Size(max = 11)
    private String userPhone;

    @Size(max = 50)
    private String userAddress;

    private Character userRole;

    private Character useYn;

    private Integer fileSeq;

    private Instant createdDate;

    private Instant modifiedDate;

    @Size(max = 10)
    private String creator;

    @Size(max = 10)
    private String modifier;

    /* etc */
    private String userPositionNm;

    private String userDepartmentNm;

    private int histSeq;

    private int cnt;

    private String projectNm; // 프로젝트 명

    private String projectStartDate; // 프로젝트 시작일

    private String projectEndDate; // 프로젝트 종료일

    private int projectTotalMonth; // 수행경력
    
    private int workExperienceTotalMonth; // 근무경력

    private String projectClient; // 발주처

    private String qualificationYn; // 정보처리기사 자격증 여부

    /* paging */
    private int curPage;
    private int offset;
    private int limit;

    private TbAttachmentInfo fileInfo;
    private List<TbUserEducationInfoDto> educationList;
    private List<TbProjectInfoDto> projectList;
    private List<TbUserQualificationInfoDto> qualificationList;
    private List<TbWorkExperienceInfoDto> workExperienceList;

    public static TbUserProfileInfo toEntity(TbUserProfileInfoDto dto, TbUserProfileInfo origin) {
        return TbUserProfileInfo.builder()
                .userPosition(dto.getUserPosition() != null ? dto.getUserPosition() : origin.getUserPosition())
                .userBirth(dto.getUserBirth() != null ? dto.getUserBirth() : origin.getUserBirth())
                .userDepartment(dto.getUserDepartment() != null ? dto.getUserDepartment() : origin.getUserDepartment())
                .userPhone(dto.getUserPhone() != null ? dto.getUserPhone() : origin.getUserPhone())
                .userAddress(dto.getUserAddress() != null ? dto.getUserAddress() : origin.getUserAddress())
                .fileSeq(origin.getFileSeq())
                .modifiedDate(Instant.now())
                .modifier(dto.getUserId())
                .build();
    }
    public static TbUserProfileInfoDto fromEntity(TbUserProfileInfo entity) {
        if (entity == null) return null;

        return TbUserProfileInfoDto.builder()
                .userId(entity.getUserId())
                .userNm(entity.getUserNm())
                .userPosition(entity.getUserPosition())
                .userBirth(entity.getUserBirth())
                .userDepartment(entity.getUserDepartment())
                .hireDate(entity.getHireDate())
                .userPhone(entity.getUserPhone())
                .userAddress(entity.getUserAddress())
                .build();
    }


}