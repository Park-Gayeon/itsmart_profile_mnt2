package kr.co.itsm.profileMnt.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityExistsException;
import kr.co.itsm.profileMnt.dao.ProfileDAO;
import kr.co.itsm.profileMnt.domain.*;
import kr.co.itsm.profileMnt.domain.TbUserQualificationInfo;
import kr.co.itsm.profileMnt.dto.*;
import kr.co.itsm.profileMnt.repository.*;
import kr.co.itsm.profileMnt.service.FileService;
import kr.co.itsm.profileMnt.service.ProfileMntService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;
import java.util.stream.IntStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProfileMntServiceImpl implements ProfileMntService {
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;
    private final ProfileDAO profileDAO;
    private final TbUserProfileInfoRepository tbUserProfileInfoRepository;
    private final TbUserProfileInfoHistRepository tbUserProfileInfoHistRepository;
    private final TbUserEducationInfoRepository tbUserEducationInfoRepository;
    private final TbUserEducationInfoHistRepository tbUserEducationInfoHistRepository;
    private final TbUserQualificationInfoRepository tbUserQualificationInfoRepository;
    private final TbWorkExperienceInfoRepository tbWorkExperienceInfoRepository;
    private final TbProjectInfoRepository tbProjectInfoRepository;
    private final FileService fileService;

    @Value("${user.default-password}")
    private String defaultPassword;

    @Override
    public List<TbUserProfileInfoDto> getUsrProfileInfoList() {
        log.info("getUsrProfileInfoList : 현재는 검색조건, 페이징 없이 전체 list 조회");
        return profileDAO.getUsrProfileInfoList();
    }

    @Override
    public ProfileRequestDto getUsrProfileDetail(String userId) {
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

        return ProfileRequestDto.builder()
                .profileInfo(TbUserProfileInfoDto.fromEntity(profileInfo))
                .educationInfo(eduInfo)
                .qualificationInfo(qualiInfo)
                .experienceInfo(workInfo)
                .projectInfo(projectInfo)
                .build();
    }

    @Override
    @Transactional
    public void insertUsrProfile(TbUserProfileInfoDto profile, MultipartFile file) {
        // 1. 존재 여부 체크
        if (tbUserProfileInfoRepository.existsById(profile.getUserId())) {
            throw new EntityExistsException("User profile with ID " + profile.getUserId() + " already exists.");
        }

        // 2. 히스토리 순번 조회
        profile.setHistSeq(getMaxHistSeq(profile.getUserId()));

        // 3. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(defaultPassword);

        // 4. file 저장
        int fileSeq = 0;
        if (file != null) {
            // file upload to server
            TbAttachmentInfoDto tbAttachmentInfo = fileService.saveImageFile(file);

            // 파일 정보 DB 저장
            tbAttachmentInfo.setUserId(profile.getUserId());
            fileSeq = fileService.insertUsrFileInfo(tbAttachmentInfo);
        }
        profile.setFileSeq(fileSeq);

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
    public void setUsrProfileDetail(ProfileRequestDto payload, MultipartFile file) {
        String userId = payload.getProfileInfo().getUserId();
        int fileSeq = 0;
        if (file != null) {
            log.info("파일을 저장하는 로직을 실행한다. ");
            // file upload to server
            TbAttachmentInfoDto tbAttachmentInfo = fileService.saveImageFile(file);

            // 파일 정보 DB 저장
            tbAttachmentInfo.setUserId(userId);
            fileSeq = fileService.insertUsrFileInfo(tbAttachmentInfo);
        }

        // 여기서 해야할 것
        // 1. 프로필 사항 저장하고 hist 테이블 적재할 것
        // 2. edu 사항 저장하고, hist 테이블 적재할 것
        // 3. work 사항 저장하고, hist 테이블 적재할 것
        // 4. qualification 사항 저장하고 hist 테이블 적재할 것
        // 5. project 사항 저장하고 hist 테이블 적재할 것



        // 2. 기존 Entity 조회
        TbUserProfileInfo setEntity = tbUserProfileInfoRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid set ID: " + userId));
        List<TbUserEducationInfo> eduEntity = tbUserEducationInfoRepository.findByIdUserId(userId);
        if(!eduEntity.isEmpty()){
            tbUserEducationInfoRepository.deleteByIdUserId(userId);
            tbUserEducationInfoRepository.flush();
        }
        List<TbUserQualificationInfo> qualiEntity = tbUserQualificationInfoRepository.findByIdUserId(userId);
        if(!qualiEntity.isEmpty()){
            tbUserQualificationInfoRepository.deleteByIdUserId(userId);
            tbUserQualificationInfoRepository.flush();
        }

        // 3. 값 업데이트
        // profile
        setEntity.setUserPhone(payload.getProfileInfo().getUserPhone());
        setEntity.setUserBirth(payload.getProfileInfo().getUserBirth());
        setEntity.setUserDepartment(payload.getProfileInfo().getUserDepartment());
        setEntity.setUserPosition(payload.getProfileInfo().getUserPosition());
        setEntity.setUserAddress(payload.getProfileInfo().getUserAddress());
        setEntity.setUserAddress(payload.getProfileInfo().getUserAddress());
        setEntity.setFileSeq(fileSeq);
        setEntity.setModifier(userId);
        setEntity.setModifiedDate(Instant.now());

        // education
        IntStream.range(0, payload.getEducationInfo().size()).forEach(i -> {
            TbUserEducationInfoDto item = payload.getEducationInfo().get(i);
            tbUserEducationInfoRepository.save(
                    TbUserEducationInfo.builder()
                            .id(TbUserEducationInfoId.builder()
                                    .userId(userId)
                                    .schoolSeq(i + 1)
                                    .build())
                            .schoolGubun(item.getSchoolGubun())
                            .schoolNm(item.getSchoolNm())
                            .schoolStartDate(item.getSchoolStartDate())
                            .schoolEndDate(item.getSchoolEndDate())
                            .major(item.getMajor())
                            .totalGrade(item.getTotalGrade())
                            .gradStatus(item.getGradStatus())
                            .createdDate(Instant.now())
                            .creator(userId)
                            .modifiedDate(Instant.now())
                            .modifier(userId)
                            .build());
        });

        // qualification
        IntStream.range(0, payload.getQualificationInfo().size()).forEach(i -> {
            TbUserQualificationInfoDto item = payload.getQualificationInfo().get(i);
            tbUserQualificationInfoRepository.save(
                    TbUserQualificationInfo.builder()
                            .id(TbUserQualificationInfoId.builder()
                                    .userId(userId)
                                    .qualificationSeq(i + 1)
                                    .build())
                            .qualificationNm(item.getQualificationNm())
                            .issuer(item.getIssuer())
                            .acquisitionDate(item.getAcquisitionDate())
                            .expiryDate(item.getExpiryDate())
                            .useYn('Y')
                            .createdDate(Instant.now())
                            .creator(userId)
                            .modifiedDate(Instant.now())
                            .modifier(userId)
                            .build());
        });


    }

    public int getMaxHistSeq(String userId) {
        return profileDAO.getMaxHistSeq(userId);
    }
}
