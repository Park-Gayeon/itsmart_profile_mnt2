package kr.co.itsm.profileMnt.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kr.co.itsm.profileMnt.domain.TbUserEducationInfo;
import kr.co.itsm.profileMnt.domain.TbUserQualificationInfo;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link kr.co.itsm.profileMnt.domain.TbUserQualificationInfo}
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbUserQualificationInfoDto implements Serializable {
    private String userId;

    private Integer qualificationSeq;

    @Size(max = 20)
    private String qualificationNm;

    @Size(max = 20)
    private String issuer;

    @Size(max = 8)
    private String acquisitionDate;

    @Size(max = 8)
    private String expiryDate;

    private Character isExpired;

    private Character useYn;

    private Instant createdDate;

    private Instant modifiedDate;

    @Size(max = 10)
    private String creator;

    @Size(max = 10)
    private String modifier;

    public static TbUserQualificationInfoDto fromEntity(TbUserQualificationInfo entity) {
        if (entity == null) return null;

        return TbUserQualificationInfoDto.builder()
                .userId(entity.getId().getUserId())
                .qualificationSeq(entity.getId().getQualificationSeq())
                .qualificationNm(entity.getQualificationNm())
                .issuer(entity.getIssuer())
                .acquisitionDate(entity.getAcquisitionDate())
                .expiryDate(entity.getExpiryDate())
                .isExpired(entity.getIsExpired())
                .build();
    }
}