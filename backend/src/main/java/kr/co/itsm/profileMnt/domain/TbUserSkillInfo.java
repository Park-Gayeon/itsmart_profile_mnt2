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
@Table(name = "TB_USER_SKILL_INFO")
public class TbUserSkillInfo {
    @EmbeddedId
    private TbUserSkillInfoId id;

    @Size(max = 20)
    @NotNull
    @Column(name = "skill_nm", nullable = false, length = 20)
    private String skillNm;

    @NotNull
    @ColumnDefault("'Y'")
    @Column(name = "use_yn", nullable = false)
    private Character useYn;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @NotNull
    @Column(name = "modified_date", nullable = false)
    private Instant modifiedDate;

    @Size(max = 10)
    @NotNull
    @Column(name = "creator", nullable = false, length = 10)
    private String creator;

    @Size(max = 10)
    @NotNull
    @Column(name = "modifier", nullable = false, length = 10)
    private String modifier;

}