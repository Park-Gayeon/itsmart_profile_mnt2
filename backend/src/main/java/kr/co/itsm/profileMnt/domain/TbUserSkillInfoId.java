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
public class TbUserSkillInfoId implements Serializable {
    private static final long serialVersionUID = -8177197702376332758L;
    @Size(max = 10)
    @NotNull
    @Column(name = "user_id", nullable = false, length = 10)
    private String userId;

    @NotNull
    @Column(name = "project_seq", nullable = false)
    private Integer projectSeq;

    @NotNull
    @Column(name = "skill_id", nullable = false)
    private Integer skillId;

}