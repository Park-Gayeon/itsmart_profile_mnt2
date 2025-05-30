package kr.co.itsm.profileMnt.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link kr.co.itsm.profileMnt.domain.TbUserProfileInfoHist}
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbUserProfileInfoHistDto implements Serializable {
    private String userId;

    private Integer histSeq;

    @Size(max = 6)
    private String userNm;

    @Size(max = 3)
    private String userPosition;

    @Size(max = 8)
    private String userBirth;

    @Size(max = 3)
    private String userDepartment;

    @Size(max = 8)
    private String hireDate;

    @Size(max = 11)
    private String userPhone;

    @Size(max = 50)
    private String userAddress;

    private Integer fileSeq;

    @NotNull
    private Instant createdDate;

    @NotNull
    @Size(max = 10)
    private String creator;
}