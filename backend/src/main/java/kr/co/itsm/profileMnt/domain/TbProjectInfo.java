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
@Table(name = "TB_PROJECT_INFO")
public class TbProjectInfo {
    @EmbeddedId
    private TbProjectInfoId id;

    @Column(name = "project_nm", nullable = false, length = 100)
    private String projectNm;

    @Column(name = "project_start_date", nullable = false, length = 8)
    private String projectStartDate;

    @Column(name = "project_end_date", nullable = false, length = 8)
    private String projectEndDate;

    @Column(name = "project_role", length = 3)
    private String projectRole;

    @Column(name = "project_client", nullable = false, length = 20)
    private String projectClient;

    @Column(name = "assigned_task_lar", nullable = false, length = 3)
    private String assignedTaskLar;

    @Column(name = "assigned_task_mid", nullable = false, length = 3)
    private String assignedTaskMid;

    @Column(name = "master_id", length = 6)
    private String masterId;

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