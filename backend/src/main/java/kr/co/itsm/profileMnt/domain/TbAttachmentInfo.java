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
@Table(name = "TB_ATTACHMENT_INFO")
public class TbAttachmentInfo {
    @EmbeddedId
    private TbAttachmentInfoId id;

    @Column(name = "file_ori_nm", nullable = false, length = 20)
    private String fileOriNm;

    @Column(name = "file_sver_nm", nullable = false, length = 50)
    private String fileSverNm;

    @Column(name = "file_sver_path", nullable = false, length = 100)
    private String fileSverPath;

    @Column(name = "file_extension", nullable = false, length = 8)
    private String fileExtension;

    @ColumnDefault("'Y'")
    @Column(name = "use_yn", nullable = false)
    private Character useYn;

    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @Column(name = "creator", nullable = false, length = 10)
    private String creator;

}