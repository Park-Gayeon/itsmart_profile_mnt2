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
@Embeddable
public class TbUserEducationInfoHistId implements Serializable {
    private static final long serialVersionUID = -4827489511314844376L;

    @Column(name = "user_id", nullable = false, length = 10)
    private String userId;

    @Column(name = "school_seq", nullable = false)
    private Integer schoolSeq;

    @Column(name = "hist_seq", nullable = false)
    private Integer histSeq;

}