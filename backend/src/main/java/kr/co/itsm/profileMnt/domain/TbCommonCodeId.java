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
public class TbCommonCodeId implements Serializable {
    private static final long serialVersionUID = 832121237585364189L;
    @Column(name = "code_group_id", nullable = false, length = 5)
    private String codeGroupId;

    @Column(name = "code_id", nullable = false, length = 3)
    private String codeId;
}