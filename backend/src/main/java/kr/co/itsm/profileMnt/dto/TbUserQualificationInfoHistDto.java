package kr.co.itsm.profileMnt.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link kr.co.itsm.profileMnt.domain.TbUserQualificationInfoHist}
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbUserQualificationInfoHistDto implements Serializable {
    private String userId;

    private Integer qualificationSeq;

    private Integer histSeq;

    @Size(max = 20)
    private String qualificationNm;

    @Size(max = 20)
    private String issuer;

    @Size(max = 8)
    private String acquisitionDate;

    @Size(max = 8)
    private String expiryDate;

    private Character isExpired;

    private Instant createdDate;

    @Size(max = 10)
    private String creator;
}