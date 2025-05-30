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
public class TbUserProfileInfoHistId implements Serializable {
    private static final long serialVersionUID = 7075918035014650071L;

    @Column(name = "user_id", nullable = false, length = 10)
    private String userId;

    @Column(name = "hist_seq", nullable = false)
    private Integer histSeq;

}