package kr.co.itsm.profileMnt.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link kr.co.itsm.profileMnt.domain.TbUserSkillInfo}
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbUserSkillInfoDto implements Serializable {
    private String userId;

    private Integer projectSeq;

    private Integer skillId;

    @Size(max = 20)
    private String skillNm;

    private Character useYn;

    private Instant createdDate;

    private Instant modifiedDate;

    @Size(max = 10)
    private String creator;

    @Size(max = 10)
    private String modifier;
}