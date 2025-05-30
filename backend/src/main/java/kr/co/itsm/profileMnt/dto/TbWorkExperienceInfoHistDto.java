package kr.co.itsm.profileMnt.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link kr.co.itsm.profileMnt.domain.TbWorkExperienceInfoHist}
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbWorkExperienceInfoHistDto implements Serializable {
    private String userId;

    private Integer workSeq;

    private Integer histSeq;

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

    private Instant createdDate;

    @Size(max = 10)
    private String creator;
}