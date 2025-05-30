package kr.co.itsm.profileMnt.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class TbWorkExperienceInfoHistId implements Serializable {
    private static final long serialVersionUID = -4981876603035569378L;
    @Size(max = 10)
    @NotNull
    @Column(name = "user_id", nullable = false, length = 10)
    private String userId;

    @NotNull
    @Column(name = "work_seq", nullable = false)
    private Integer workSeq;

    @NotNull
    @Column(name = "hist_seq", nullable = false)
    private Integer histSeq;

}