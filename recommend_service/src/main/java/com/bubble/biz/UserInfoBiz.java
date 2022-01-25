package com.bubble.biz;

import com.bubble.dal.UserInfoService;
import com.bubble.model.UserInfo;
import com.bubble.thrift.BaseResp;
import com.bubble.thrift.recommend_service.*;
import com.bubble.thrift.recommend_service.GetUserInfoResponse;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author: sunpengyu.sonia
 * @Date: 2022/1/17 12:41 PM
 * @Desc:
 */
public class UserInfoBiz {
    @Autowired
    private UserInfoService userInfoService;

    public void CheckUserInfoRequest(GetUserInfoRequest request) throws TException {
        // 检查参数合法性
        if (request.UserId == null || request.getUserId().isEmpty()) {
            throw new TException("Empty UserId Error!");
        }
    }

    public GetUserInfoResponse GetUserInfo(GetUserInfoRequest getUserInfoRequest) {
        // 通过ID获取用户信息
        try {
            // 检查参数
            CheckUserInfoRequest(getUserInfoRequest);
            if (getUserInfoRequest.getUserId().size() > 1) {
                throw new TException("UserId not one Error!");
            }
            GetUserInfoResponse getUserInfoResponse = new GetUserInfoResponse();
            UserInfoEntity userInfoEntity = new UserInfoEntity();
            // 查询数据
            List<UserInfo> userInfoList = userInfoService.getUserInfoById(getUserInfoRequest.getUserId().get(0));
            UserInfo userInfo = userInfoList.get(0);
            // 检查SQL返回
            if (userInfo.getId() != getUserInfoRequest.getUserId().get(0)) {
                throw new TException("SQL ERROR!!!");
            }
            // 为返回的结构体赋值
            UserInfoToEntity(userInfo, userInfoEntity);
            getUserInfoResponse.setUserInfoEntity(new ArrayList<UserInfoEntity>((Collection<? extends UserInfoEntity>) userInfoEntity));
            getUserInfoResponse.setBaseResp(new BaseResp().setStatusCode(0).setStatusMsg("OK"));
            return getUserInfoResponse;
        } catch (TException e) {
            GetUserInfoResponse response = new GetUserInfoResponse();
            response.setBaseResp(new BaseResp().setStatusCode(1).setStatusMsg(e.getMessage()));
            return response;
        }
    }

    public GetUserInfoResponse BatchGetUserInfo(GetUserInfoRequest request) {
        // 批量通过ID获取用户信息
        try {
            CheckUserInfoRequest(request);
            List<UserInfoEntity> resultList = new ArrayList<>();
            for (int userId : request.getUserId()) {
                List<UserInfo> userInfoList = userInfoService.getUserInfoById(userId);
                UserInfo userInfo = userInfoList.get(0);
                // 检查SQL返回
                if (userInfo.getId() != request.getUserId().get(0)) {
                    throw new TException("SQL ERROR!!!");
                }
                UserInfoEntity userInfoEntity = new UserInfoEntity();
                // 为返回的结构体赋值
                UserInfoToEntity(userInfo, userInfoEntity);
                resultList.add(userInfoEntity);
            }
            GetUserInfoResponse getUserInfoResponse = new GetUserInfoResponse();
            getUserInfoResponse.setUserInfoEntity(resultList);
            getUserInfoResponse.setBaseResp(new BaseResp().setStatusCode(0).setStatusMsg("OK"));
            return getUserInfoResponse;
        } catch (TException e) {
            GetUserInfoResponse response = new GetUserInfoResponse();
            response.setBaseResp(new BaseResp().setStatusCode(1).setStatusMsg(e.getMessage()));
            return response;
        }
    }

    private void UserInfoToEntity(UserInfo userInfo, UserInfoEntity userInfoEntity) {
        // 赋值
        userInfoEntity.setAge(userInfo.getAge().shortValue());
        userInfoEntity.setContinent(userInfo.getContinent().shortValue());
        userInfoEntity.setConsumptionCapacity(userInfo.getConsumptionCapacity());
        userInfoEntity.setDegree(userInfo.getDegree().shortValue());
        userInfoEntity.setGender(userInfo.getGender().shortValue());
        userInfoEntity.setId(userInfo.getId());
        userInfoEntity.setUserStatus(userInfo.getUserStatus().shortValue());
        userInfoEntity.setCreateTime(userInfo.getCreateTime().toString());
        userInfoEntity.setModifyTime(userInfo.getModifyTime().toString());
    }
}
