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
@Table(name = "TB_COMMON_CODE")
public class TbCommonCode {
    @EmbeddedId
    private TbCommonCodeId id;

    @Column(name = "code_group_nm", nullable = false, length = 10)
    private String codeGroupNm;

    @Column(name = "code_value", nullable = false, length = 50)
    private String codeValue;

    @Size(max = 3)
    @Column(name = "parent_id", length = 3)
    private String parentId;

    @Column(name = "level", nullable = false)
    private Integer level;

    @ColumnDefault("'Y'")
    @Column(name = "use_yn", nullable = false)
    private Character useYn;

    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @Column(name = "creator", nullable = false, length = 10)
    private String creator;

}