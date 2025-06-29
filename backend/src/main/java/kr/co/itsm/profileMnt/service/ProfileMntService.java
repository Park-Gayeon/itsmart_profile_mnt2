package kr.co.itsm.profileMnt.service;

import kr.co.itsm.profileMnt.dto.ProfileRequestDto;
import kr.co.itsm.profileMnt.dto.TbUserProfileInfoDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ProfileMntService {
    List<TbUserProfileInfoDto> getUsrProfileInfoList();

    void insertUsrProfile(TbUserProfileInfoDto profile, MultipartFile file);

    ProfileRequestDto getUsrProfileDetail(String userId);

    void setUsrProfileDetail(ProfileRequestDto payload, MultipartFile file);
}
