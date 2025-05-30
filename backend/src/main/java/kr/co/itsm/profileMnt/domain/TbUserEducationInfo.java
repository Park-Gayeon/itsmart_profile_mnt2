package kr.co.itsm.profileMnt.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_USER_EDUCATION_INFO")
public class TbUserEducationInfo {
    @EmbeddedId
    private TbUserEducationInfoId id;

    @Column(name = "school_gubun", length = 3)
    private String schoolGubun;

    @Column(name = "school_nm", length = 20)
    private String schoolNm;

    @Column(name = "school_start_date", length = 8)
    private String schoolStartDate;

    @Column(name = "school_end_date", length = 8)
    private String schoolEndDate;

    @Column(name = "major", length = 20)
    private String major;

    @Column(name = "double_major", length = 20)
    private String doubleMajor;

    @Column(name = "total_grade", precision = 2, scale = 1)
    private BigDecimal totalGrade;

    @Column(name = "standard_grade", precision = 2, scale = 1)
    private BigDecimal standardGrade;

    @Column(name = "grad_status", length = 3)
    private String gradStatus;

    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @Column(name = "modified_date", nullable = false)
    private Instant modifiedDate;

    @Column(name = "creator", nullable = false, length = 10)
    private String creator;

    @Column(name = "modifier", nullable = false, length = 10)
    private String modifier;

}