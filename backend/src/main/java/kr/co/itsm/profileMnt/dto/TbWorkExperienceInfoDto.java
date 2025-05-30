package kr.co.itsm.profileMnt.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kr.co.itsm.profileMnt.domain.TbUserEducationInfo;
import kr.co.itsm.profileMnt.domain.TbWorkExperienceInfo;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link kr.co.itsm.profileMnt.domain.TbWorkExperienceInfo}
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbWorkExperienceInfoDto implements Serializable {
    private String userId;

    private Integer workSeq;

    @Size(max = 20)
    private String workPlace;

    @Size(max = 20)
    private String workAssignedTask;

    @Size(max = 10)
    private String workPosition;

    @Size(max = 8)
    private String workStartDate;

    @Size(max = 8)
    private String workEndDate;

    private Character useYn;

    private Instant createdDate;

    private Instant modifiedDate;

    @Size(max = 10)
    private String creator;

    @Size(max = 10)
    private String modifier;

    public static TbWorkExperienceInfoDto fromEntity(TbWorkExperienceInfo entity) {
        if (entity == null) return null;

        return TbWorkExperienceInfoDto.builder()
                .userId(entity.getId().getUserId())
                .workSeq(entity.getId().getWorkSeq())
                .workPlace(entity.getWorkPlace())
                .workAssignedTask(entity.getWorkAssignedTask())
                .workPosition(entity.getWorkPosition())
                .workStartDate(entity.getWorkStartDate())
                .workEndDate(entity.getWorkEndDate())
                .build();
    }
}