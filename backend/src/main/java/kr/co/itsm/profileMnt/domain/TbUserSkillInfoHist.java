package kr.co.itsm.profileMnt.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Instant;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_USER_SKILL_INFO_HIST")
public class TbUserSkillInfoHist {
    @EmbeddedId
    private TbUserSkillInfoHistId id;

    @Size(max = 20)
    @Column(name = "skill_nm", length = 20)
    private String skillNm;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @Size(max = 10)
    @NotNull
    @Column(name = "creator", nullable = false, length = 10)
    private String creator;

}