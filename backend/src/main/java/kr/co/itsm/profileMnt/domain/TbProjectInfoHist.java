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
@Table(name = "TB_PROJECT_INFO_HIST")
public class TbProjectInfoHist {
    @EmbeddedId
    private TbProjectInfoHistId id;

    @Size(max = 100)
    @Column(name = "project_nm", length = 100)
    private String projectNm;

    @Size(max = 8)
    @Column(name = "project_start_date", length = 8)
    private String projectStartDate;

    @Size(max = 8)
    @Column(name = "project_end_date", length = 8)
    private String projectEndDate;

    @Size(max = 3)
    @Column(name = "project_role", length = 3)
    private String projectRole;

    @Size(max = 20)
    @Column(name = "project_client", length = 20)
    private String projectClient;

    @Size(max = 3)
    @Column(name = "assigned_task_lar", length = 3)
    private String assignedTaskLar;

    @Size(max = 3)
    @Column(name = "assigned_task_mid", length = 3)
    private String assignedTaskMid;

    @Size(max = 6)
    @Column(name = "master_id", length = 6)
    private String masterId;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @Size(max = 10)
    @NotNull
    @Column(name = "creator", nullable = false, length = 10)
    private String creator;

}