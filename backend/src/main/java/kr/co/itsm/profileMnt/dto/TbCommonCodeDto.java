package kr.co.itsm.profileMnt.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link kr.co.itsm.profileMnt.domain.TbCommonCode}
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbCommonCodeDto implements Serializable {
    private String codeGroupId;

    private String codeId;

    @Size(max = 10)
    private String codeGroupNm;

    @Size(max = 50)
    private String codeValue;

    @Size(max = 3)
    private String parentId;

    private Integer level;

    private Character useYn;

    private Instant createdDate;

    @Size(max = 10)
    private String creator;
}