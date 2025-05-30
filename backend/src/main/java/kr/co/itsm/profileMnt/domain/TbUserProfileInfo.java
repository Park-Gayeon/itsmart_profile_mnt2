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
@Table(name = "TB_USER_PROFILE_INFO")
public class TbUserProfileInfo {
    @Id
    @Column(name = "user_id", nullable = false, length = 10)
    private String userId;

    @Column(name = "user_pw", nullable = false, length = 120)
    private String userPw;

    @Column(name = "user_nm", nullable = false, length = 6)
    private String userNm;

    @Column(name = "user_position", nullable = false, length = 3)
    private String userPosition;

    @Column(name = "user_birth", nullable = false, length = 8)
    private String userBirth;

    @Column(name = "user_department", nullable = false, length = 3)
    private String userDepartment;

    @Column(name = "hire_date", nullable = false, length = 8)
    private String hireDate;

    @Column(name = "user_phone", nullable = false, length = 11)
    private String userPhone;

    @Column(name = "user_address", nullable = false, length = 50)
    private String userAddress;

    @ColumnDefault("'1'")
    @Column(name = "user_role", nullable = false)
    private Character userRole;

    @ColumnDefault("'Y'")
    @Column(name = "use_yn", nullable = false)
    private Character useYn;

    @Column(name = "file_seq")
    private Integer fileSeq;

    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @Column(name = "modified_date", nullable = false)
    private Instant modifiedDate;

    @Column(name = "creator", nullable = false, length = 10)
    private String creator;

    @Column(name = "modifier", nullable = false, length = 10)
    private String modifier;

}