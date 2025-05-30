package kr.co.itsm.profileMnt.service;

import kr.co.itsm.profileMnt.dto.TbUserProfileInfoDto;

import java.util.List;

public interface ProfileMntService {
    List<TbUserProfileInfoDto> getUsrProfileInfoList();

    void insertUsrProfile(TbUserProfileInfoDto profile);

    List<Object> getUsrProfileDetail(String userId);

    void updateUsrProfile(TbUserProfileInfoDto tbUserProfileInfo);
}
