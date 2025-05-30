package kr.co.itsm.profileMnt.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "TB_PROJECT_MMT")
public class TbProjectMmt {
    @Id
    @Size(max = 6)
    @Column(name = "master_id", nullable = false, length = 6)
    private String masterId;

    @Size(max = 100)
    @NotNull
    @Column(name = "project_nm", nullable = false, length = 100)
    private String projectNm;

    @Size(max = 8)
    @NotNull
    @Column(name = "project_start_date", nullable = false, length = 8)
    private String projectStartDate;

    @Size(max = 8)
    @NotNull
    @Column(name = "project_end_date", nullable = false, length = 8)
    private String projectEndDate;

    @Size(max = 20)
    @NotNull
    @Column(name = "project_client", nullable = false, length = 20)
    private String projectClient;

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