package kr.co.itsm.profileMnt.service.impl;

import jakarta.persistence.EntityExistsException;
import kr.co.itsm.profileMnt.dao.ProfileDAO;
import kr.co.itsm.profileMnt.dao.ProjectDAO;
import kr.co.itsm.profileMnt.dao.WorkExperienceDAO;
import kr.co.itsm.profileMnt.domain.*;
import kr.co.itsm.profileMnt.domain.TbUserQualificationInfo;
import kr.co.itsm.profileMnt.dto.*;
import kr.co.itsm.profileMnt.repository.*;
import kr.co.itsm.profileMnt.service.ProfileMntService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProfileMntServiceImpl implements ProfileMntService {
    private final PasswordEncoder passwordEncoder;
    private final ProfileDAO profileDAO;
    private final TbUserProfileInfoRepository tbUserProfileInfoRepository;
    private final TbUserProfileInfoHistRepository tbUserProfileInfoHistRepository;
    private final TbUserEducationInfoRepository tbUserEducationInfoRepository;
    private final TbUserEducationInfoHistRepository tbUserEducationInfoHistRepository;
    private final TbUserQualificationInfoRepository tbUserQualificationInfoRepository;
    private final TbWorkExperienceInfoRepository tbWorkExperienceInfoRepository;
    private final TbProjectInfoRepository tbProjectInfoRepository;

    @Value("${user.default-password}")
    private String defaultPassword;

    @Override
    public List<TbUserProfileInfoDto> getUsrProfileInfoList() {
        log.info("getUsrProfileInfoList : 현재는 검색조건, 페이징 없이 전체 list 조회");
        return profileDAO.getUsrProfileInfoList();
    }

    @Override
    public List<Object> getUsrProfileDetail(String userId) {
        TbUserProfileInfo profileInfo = tbUserProfileInfoRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid storage ID: " + userId));
        List<TbUserEducationInfo> eduEntity = tbUserEducationInfoRepository.findByIdUserId(userId);
        List<TbUserQualificationInfo> qualificationEntity = tbUserQualificationInfoRepository.findByIdUserId(userId);
        List<TbWorkExperienceInfo> workExperienceEntity = tbWorkExperienceInfoRepository.findByIdUserId(userId);
        List<TbProjectInfo> projectEntity =tbProjectInfoRepository.findByIdUserId(userId);

        /* dto 변환 */
        List<TbUserEducationInfoDto> eduInfo = eduEntity.stream()
                .map(TbUserEducationInfoDto::fromEntity)
                .toList();

        List<TbUserQualificationInfoDto> qualiInfo = qualificationEntity.stream()
                .map(TbUserQualificationInfoDto::fromEntity)
                .toList();

        List<TbWorkExperienceInfoDto> workInfo = workExperienceEntity.stream()
                .map(TbWorkExperienceInfoDto::fromEntity)
                .toList();

        List<TbProjectInfoDto> projectInfo = projectEntity.stream()
                .map(TbProjectInfoDto::fromEntity)
                .toList();

        return List.of(
                TbUserProfileInfoDto.fromEntity(profileInfo),
                eduInfo,
                qualiInfo,
                workInfo,
                projectInfo
        );
    }

    @Override
    @Transactional
    public void insertUsrProfile(TbUserProfileInfoDto profile) {
        // 1. 존재 여부 체크
        if (tbUserProfileInfoRepository.existsById(profile.getUserId())) {
            throw new EntityExistsException("User profile with ID " + profile.getUserId() + " already exists.");
        }

        // 2. 히스토리 순번 조회
        profile.setHistSeq(getMaxHistSeq(profile.getUserId()));

        // 3. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(defaultPassword);

        // 4. 프로필 엔티티 생성 및 저장
        TbUserProfileInfo profileEntity = TbUserProfileInfo.builder()
                .userId(profile.getUserId())
                .userPw(encodedPassword)
                .userNm(profile.getUserNm())
                .userPosition(profile.getUserPosition())
                .userBirth(profile.getUserBirth())
                .userDepartment(profile.getUserDepartment())
                .hireDate(profile.getHireDate())
                .userPhone(profile.getUserPhone())
                .userAddress(profile.getUserAddress())
                .fileSeq(profile.getFileSeq())
                .useYn('Y')
                .createdDate(Instant.now())
                .creator(profile.getCreator())
                .build();
        tbUserProfileInfoRepository.save(profileEntity);

        // 5. 이력 엔티티 생성 및 저장
        TbUserProfileInfoHist profileHistEntity = TbUserProfileInfoHist.builder()
                .id(TbUserProfileInfoHistId.builder()
                        .userId(profile.getUserId())
                        .histSeq(profile.getHistSeq())
                        .build())
                .userNm(profile.getUserNm())
                .userPosition(profile.getUserPosition())
                .userDepartment(profile.getUserDepartment())
                .userBirth(profile.getUserBirth())
                .hireDate(profile.getHireDate())
                .userPhone(profile.getUserPhone())
                .userAddress(profile.getUserAddress())
                .fileSeq(profile.getFileSeq())
                .createdDate(Instant.now())
                .creator(profile.getCreator())
                .build();
        tbUserProfileInfoHistRepository.save(profileHistEntity);
    }

    @Override
    @Transactional
    public void updateUsrProfile(TbUserProfileInfoDto profile) {
        profile.setHistSeq(getMaxHistSeq(profile.getUserId()));

        TbUserProfileInfo entity = tbUserProfileInfoRepository.findById(profile.getUserId())
                .orElseThrow(() -> new NoSuchElementException("User profile not found: " + profile.getUserId()));

        TbUserProfileInfo updatedEntity = TbUserProfileInfoDto.toEntity(profile, entity);
        tbUserProfileInfoRepository.save(updatedEntity);

        TbUserProfileInfoHist profileHistEntity = TbUserProfileInfoHist.builder()
                .id(TbUserProfileInfoHistId.builder()
                        .userId(profile.getUserId())
                        .histSeq(profile.getHistSeq())
                        .build())
                .userNm(profile.getUserNm())
                .userPosition(profile.getUserPosition())
                .userDepartment(profile.getUserDepartment())
                .userBirth(profile.getUserBirth())
                .hireDate(profile.getHireDate())
                .userPhone(profile.getUserPhone())
                .userAddress(profile.getUserAddress())
                .fileSeq(profile.getFileSeq())
                .createdDate(Instant.now())
                .creator(profile.getCreator())
                .build();
        tbUserProfileInfoHistRepository.save(profileHistEntity);

        if (profile.getEducationList() == null || profile.getEducationList().isEmpty()){
            log.info("학력 정보가 비어있습니다. 처리할 데이터가 없습니다: user_id={}", profile.getUserId());
        } else {
            // DELETE TB
            log.info("사용자 학력 정보를 삭제합니다: user_id={}", profile.getUserId());
            tbUserEducationInfoRepository.deleteByIdUserId(profile.getUserId());

            for (TbUserEducationInfoDto edu : profile.getEducationList()){
                edu.setUserId(profile.getUserId());
                edu.setHistSeq(profile.getHistSeq());

                log.info("사용자 학력 정보를 입력합니다: user_id={}, school_nm={}", edu.getUserId(), edu.getSchoolNm());
                TbUserEducationInfo tbUserEducationInfo = TbUserEducationInfo.builder()
                                .id(TbUserEducationInfoId.builder()
                                        .userId(edu.getUserId())
                                        .schoolSeq(edu.getSchoolSeq())
                                        .build()
                                )
                                .schoolGubun(edu.getSchoolGubun())
                                .schoolNm(edu.getSchoolNm())
                                .schoolStartDate(edu.getSchoolStartDate())
                                .schoolEndDate(edu.getSchoolEndDate())
                                .major(edu.getMajor())
                                .doubleMajor(edu.getDoubleMajor())
                                .totalGrade(edu.getTotalGrade())
                                .standardGrade(edu.getStandardGrade())
                                .gradStatus(edu.getGradStatus())
                                .creator("SYSTEM")
                                .modifier("SYSTEM")
                                .createdDate(Instant.now())
                                .modifiedDate(Instant.now())
                        .build();
                tbUserEducationInfoRepository.save(tbUserEducationInfo);


                TbUserEducationInfoHist tbUserEducationInfoHist = TbUserEducationInfoHist.builder()
                                .id(TbUserEducationInfoHistId.builder()
                                        .userId(edu.getUserId())
                                        .schoolSeq(edu.getSchoolSeq())
                                        .histSeq(edu.getHistSeq())
                                        .build()
                                )
                                .schoolGubun(edu.getSchoolGubun())
                                .schoolStartDate(edu.getSchoolStartDate())
                                .schoolEndDate(edu.getSchoolEndDate())
                                .major(edu.getMajor())
                                .doubleMajor(edu.getDoubleMajor())
                                .totalGrade(edu.getTotalGrade())
                                .standardGrade(edu.getStandardGrade())
                                .gradStatus(edu.getGradStatus())
                                .gradStatus(edu.getGradStatus())
                                .creator("SYSTEM")
                                .createdDate(Instant.now())
                                .build();
                tbUserEducationInfoHistRepository.save(tbUserEducationInfoHist);
                log.info("사용자 학력 정보 이력을 생성했습니다: user_id={}, hist_seq={}", edu.getUserId(), edu.getHistSeq());
            }
        }
    }

    public int getMaxHistSeq(String userId) {
        return profileDAO.getMaxHistSeq(userId);
    }
}
