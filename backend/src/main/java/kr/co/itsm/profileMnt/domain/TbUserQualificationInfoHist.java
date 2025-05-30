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
@Table(name = "TB_USER_QUALIFICATION_INFO_HIST")
public class TbUserQualificationInfoHist {
    @EmbeddedId
    private TbUserQualificationInfoHistId id;

    @Size(max = 20)
    @Column(name = "qualification_nm", length = 20)
    private String qualificationNm;

    @Size(max = 20)
    @Column(name = "issuer", length = 20)
    private String issuer;

    @Size(max = 8)
    @Column(name = "acquisition_date", length = 8)
    private String acquisitionDate;

    @Size(max = 8)
    @Column(name = "expiry_date", length = 8)
    private String expiryDate;

    @ColumnDefault("'N'")
    @Column(name = "is_expired")
    private Character isExpired;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @Size(max = 10)
    @NotNull
    @Column(name = "creator", nullable = false, length = 10)
    private String creator;

}