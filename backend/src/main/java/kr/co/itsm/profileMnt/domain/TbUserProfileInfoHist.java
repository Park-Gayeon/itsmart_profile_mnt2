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
@Table(name = "TB_USER_PROFILE_INFO_HIST")
public class TbUserProfileInfoHist {
    @EmbeddedId
    private TbUserProfileInfoHistId id;

    @Column(name = "user_nm", length = 6)
    private String userNm;

    @Column(name = "user_position", length = 3)
    private String userPosition;

    @Column(name = "user_birth", length = 8)
    private String userBirth;

    @Column(name = "user_department", length = 3)
    private String userDepartment;

    @Column(name = "hire_date", length = 8)
    private String hireDate;

    @Column(name = "user_phone", length = 11)
    private String userPhone;

    @Column(name = "user_address", length = 50)
    private String userAddress;

    @Column(name = "file_seq")
    private Integer fileSeq;

    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @Column(name = "creator", nullable = false, length = 10)
    private String creator;

}