package kr.co.itsm.profileMnt.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * DTO for {@link kr.co.itsm.profileMnt.domain.TbUserEducationInfoHist}
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbUserEducationInfoHistDto implements Serializable {
    private String userId;

    private Integer schoolSeq;

    private Integer histSeq;

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

    @Size(max = 10)
    private String creator;
}