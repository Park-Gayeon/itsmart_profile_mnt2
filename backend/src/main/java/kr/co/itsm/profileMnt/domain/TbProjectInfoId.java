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
public class TbProjectInfoId implements Serializable {
    private static final long serialVersionUID = -57255313423982248L;

    @Column(name = "user_id", nullable = false, length = 10)
    private String userId;

    @Column(name = "project_seq", nullable = false)
    private Integer projectSeq;

}