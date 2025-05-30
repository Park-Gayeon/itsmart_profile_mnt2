package kr.co.itsm.profileMnt.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Entity
@Table(name = "TB_WORK_EXPERIENCE_INFO_HIST")
public class TbWorkExperienceInfoHist {
    @EmbeddedId
    private TbWorkExperienceInfoHistId id;

    @Size(max = 20)
    @Column(name = "work_place", length = 20)
    private String workPlace;

    @Size(max = 20)
    @Column(name = "work_assigned_task", length = 20)
    private String workAssignedTask;

    @Size(max = 10)
    @Column(name = "work_position", length = 10)
    private String workPosition;

    @Size(max = 8)
    @Column(name = "work_start_date", length = 8)
    private String workStartDate;

    @Size(max = 8)
    @Column(name = "work_end_date", length = 8)
    private String workEndDate;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @Size(max = 10)
    @NotNull
    @Column(name = "creator", nullable = false, length = 10)
    private String creator;

}