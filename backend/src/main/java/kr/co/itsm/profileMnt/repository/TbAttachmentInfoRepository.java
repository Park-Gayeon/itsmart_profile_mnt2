package kr.co.itsm.profileMnt.repository;

import kr.co.itsm.profileMnt.domain.TbAttachmentInfo;
import kr.co.itsm.profileMnt.domain.TbAttachmentInfoId;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TbAttachmentInfoRepository extends JpaRepository<TbAttachmentInfo, TbAttachmentInfoId> {

    List<TbAttachmentInfo> findByIdUserIdAndIdFileSeOrderByIdFileSeqDesc(String userId, String fileSe);

    @Query(value = "SELECT * FROM TB_ATTACHMENT_INFO WHERE USER_ID = :userId AND FILE_SE = :fileSe ORDER BY FILE_SEQ DESC LIMIT 1", nativeQuery = true)
    Optional<TbAttachmentInfo> findTopByUserIdAndFileSe(String userId, String fileSe);

    @Query(value = "SELECT COALESCE(MAX(FILE_SEQ + 1), 1) FROM TB_ATTACHMENT_INFO WHERE USER_ID = :userId", nativeQuery = true)
    int getNextSeq(@Param("userId") String userId);

}
