package kr.co.itsm.profileMnt.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TbUserQualificationInfoId implements Serializable {
    private static final long serialVersionUID = 3851725003491733554L;
    @Column(name = "user_id", nullable = false, length = 10)
    private String userId;

    @Column(name = "qualification_seq", nullable = false)
    private Integer qualificationSeq;

}