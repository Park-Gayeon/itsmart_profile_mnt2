package kr.co.itsm.profileMnt.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link kr.co.itsm.profileMnt.domain.TbUserSkillInfoHist}
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbUserSkillInfoHistDto implements Serializable {
    private String userId;

    private Integer projectSeq;

    private Integer skillId;

    private Integer histSeq;

    @Size(max = 20)
    private String skillNm;

    private Instant createdDate;

    @Size(max = 10)
    private String creator;
}