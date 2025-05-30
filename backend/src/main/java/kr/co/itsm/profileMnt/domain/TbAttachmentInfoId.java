package kr.co.itsm.profileMnt.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class TbAttachmentInfoId implements Serializable {
    private static final long serialVersionUID = 1424386906183413531L;

    @Column(name = "file_seq", nullable = false)
    private Integer fileSeq;

    @Column(name = "file_se", nullable = false, length = 30)
    private String fileSe;

    @Column(name = "user_id", nullable = false, length = 10)
    private String userId;
}