package kr.co.itsm.profileMnt.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link kr.co.itsm.profileMnt.domain.TbProjectMmt}
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbProjectMmtDto implements Serializable {
    @Size(max = 6)
    private String masterId;

    @Size(max = 100)
    private String projectNm;

    @Size(max = 8)
    private String projectStartDate;

    @Size(max = 8)
    private String projectEndDate;

    @Size(max = 20)
    private String projectClient;

    private Character useYn;

    private Instant createdDate;

    private Instant modifiedDate;

    @Size(max = 10)
    private String creator;

    @Size(max = 10)
    private String modifier;
}