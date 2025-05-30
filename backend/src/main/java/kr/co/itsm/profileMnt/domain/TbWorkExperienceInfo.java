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
@Table(name = "TB_WORK_EXPERIENCE_INFO")
public class TbWorkExperienceInfo {
    @EmbeddedId
    private TbWorkExperienceInfoId id;

    @Column(name = "work_place", nullable = false, length = 20)
    private String workPlace;

    @Column(name = "work_assigned_task", nullable = false, length = 20)
    private String workAssignedTask;

    @Column(name = "work_position", nullable = false, length = 10)
    private String workPosition;

    @Column(name = "work_start_date", nullable = false, length = 8)
    private String workStartDate;

    @Column(name = "work_end_date", nullable = false, length = 8)
    private String workEndDate;

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