package kr.co.itsm.profileMnt.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kr.co.itsm.profileMnt.domain.TbUserEducationInfo;
import kr.co.itsm.profileMnt.domain.TbUserProfileInfo;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * DTO for {@link kr.co.itsm.profileMnt.domain.TbUserEducationInfo}
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbUserEducationInfoDto implements Serializable {
    private String userId;

    private Integer schoolSeq;

    @Size(max = 3)
    private String schoolGubun;

    @Size(max = 20)
    private String schoolNm;

    @Size(max = 8)
    private String schoolStartDate;

    @Size(max = 8)
    private String schoolEndDate;

    @Size(max = 20)
    private String major;

    @Size(max = 20)
    private String doubleMajor;

    private BigDecimal totalGrade;

    private BigDecimal standardGrade;

    @Size(max = 3)
    private String gradStatus;

    private Instant createdDate;

    private  Instant modifiedDate;

    @Size(max = 10)
    private String creator;

    @Size(max = 10)
    private String modifier;

    /* etc */
    private int histSeq; // 이력 순번

    public static TbUserEducationInfoDto fromEntity(TbUserEducationInfo entity) {
        if (entity == null) return null;

        return TbUserEducationInfoDto.builder()
                .userId(entity.getId().getUserId())
                .schoolSeq(entity.getId().getSchoolSeq())
                .schoolGubun(entity.getSchoolGubun())
                .schoolNm(entity.getSchoolNm())
                .schoolStartDate(entity.getSchoolStartDate())
                .schoolEndDate(entity.getSchoolEndDate())
                .major(entity.getMajor())
                .totalGrade(entity.getTotalGrade())
                .standardGrade(entity.getStandardGrade())
                .gradStatus(entity.getGradStatus())
                .build();
    }
}