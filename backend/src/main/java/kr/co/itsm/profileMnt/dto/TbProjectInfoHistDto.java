package kr.co.itsm.profileMnt.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link kr.co.itsm.profileMnt.domain.TbProjectInfoHist}
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbProjectInfoHistDto implements Serializable {
    private String userId;

    private Integer projectSeq;

    private Integer histSeq;

    @Size(max = 100)
    private String projectNm;

    @Size(max = 8)
    private String projectStartDate;

    @Size(max = 8)
    private String projectEndDate;

    @Size(max = 3)
    private String projectRole;

    @Size(max = 20)
    private String projectClient;

    @Size(max = 3)
    private String assignedTaskLar;

    @Size(max = 3)
    private String assignedTaskMid;

    @Size(max = 6)
    private String masterId;

    @NotNull
    private Instant createdDate;

    @Size(max = 10)
    private String creator;
}