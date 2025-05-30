package kr.co.itsm.profileMnt.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kr.co.itsm.profileMnt.domain.TbAttachmentInfo;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link kr.co.itsm.profileMnt.domain.TbAttachmentInfo}
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbAttachmentInfoDto implements Serializable {
    private Integer fileSeq;

    private String fileSe;

    private String userId;

    @Size(max = 20)
    private String fileOriNm;

    @Size(max = 50)
    private String fileSverNm;

    @Size(max = 100)
    private String fileSverPath;

    @Size(max = 8)
    private String fileExtension;

    private Character useYn;

    private Instant createdDate;

    @Size(max = 10)
    private String creator;

    public static TbAttachmentInfoDto fromEntity(TbAttachmentInfo entity) {
        if (entity == null) return null;

        return TbAttachmentInfoDto.builder()
                .userId(entity.getId().getUserId())
                .fileSeq(entity.getId().getFileSeq())
                .fileSe(entity.getId().getFileSe())
                .fileOriNm(entity.getFileOriNm())
                .fileSverNm(entity.getFileSverNm())
                .fileSverPath(entity.getFileSverPath())
                .fileExtension(entity.getFileExtension())
                .build();
    }
}