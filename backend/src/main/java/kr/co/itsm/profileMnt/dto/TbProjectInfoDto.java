package kr.co.itsm.profileMnt.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kr.co.itsm.profileMnt.domain.TbAttachmentInfo;
import kr.co.itsm.profileMnt.domain.TbProjectInfo;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link kr.co.itsm.profileMnt.domain.TbProjectInfo}
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbProjectInfoDto implements Serializable {
    private String userId;

    private Integer projectSeq;

    @Size(max = 100)
    private String projectNm;

    @Size(max = 8)
    private String projectStartDate;

    @Size(max = 8)
    private String projectEndDate;

    @Size(max = 3)
    private String projectRole;

    @Size(max = 20)
    private String projectClient;

    @Size(max = 3)
    private String assignedTaskLar;

    @Size(max = 3)
    private String assignedTaskMid;

    @Size(max = 6)
    private String masterId;

    private Character useYn;

    private Instant createdDate;

    private Instant modifiedDate;

    @Size(max = 10)
    private String creator;

    @Size(max = 10)
    private String modifier;

    public static TbProjectInfoDto fromEntity(TbProjectInfo entity) {
        if (entity == null) return null;

        return TbProjectInfoDto.builder()
                .userId(entity.getId().getUserId())
                .projectSeq(entity.getId().getProjectSeq())
                .projectNm(entity.getProjectNm())
                .projectStartDate(entity.getProjectStartDate())
                .projectEndDate(entity.getProjectEndDate())
                .projectRole(entity.getProjectRole())
                .projectClient(entity.getProjectClient())
                .assignedTaskLar(entity.getAssignedTaskLar())
                .assignedTaskMid(entity.getAssignedTaskMid())
                .masterId(entity.getMasterId())
                .createdDate(entity.getCreatedDate())
                .build();
    }
}