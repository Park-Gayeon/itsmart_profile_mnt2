package kr.co.itsm.profileMnt.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_USER_QUALIFICATION_INFO")
public class TbUserQualificationInfo {
    @EmbeddedId
    private TbUserQualificationInfoId id;

    @Column(name = "qualification_nm", nullable = false, length = 20)
    private String qualificationNm;

    @Column(name = "issuer", nullable = false, length = 20)
    private String issuer;

    @Column(name = "acquisition_date", nullable = false, length = 8)
    private String acquisitionDate;

    @Column(name = "expiry_date", length = 8)
    private String expiryDate;

    @ColumnDefault("'N'")
    @Column(name = "is_expired")
    private Character isExpired;

    @ColumnDefault("'Y'")
    @Column(name = "use_yn", nullable = false)
    private Character useYn;

    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @Column(name = "modified_date", nullable = false)
    private Instant modifiedDate;

    @Column(name = "creator", nullable = false, length = 10)
    private String creator;

    @Column(name = "modifier", nullable = false, length = 10)
    private String modifier;

}