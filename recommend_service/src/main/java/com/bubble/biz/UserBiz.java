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

import static sun.misc.Signal.raise;

/**
 * @Author: sunpengyu.sonia
 * @Date: 2022/1/17 12:41 PM
 * @Desc:
 */
public class UserBiz {
    @Autowired
    private UserInfoService userInfoService;

    public void CheckUserInfoRequest(GetUserInfoRequest request) throws TException {
        if (request.UserId == null || request.getUserId().isEmpty()) {
            throw new TException("Empty UserId Error!");
        }
        if (request.getUserId().size() > 1) {
            throw new TException("UserId not one Error!");
        }
    }

    public GetUserInfoResponse GetUserInfo(GetUserInfoRequest getUserInfoRequest) {
        try {
            // 检查参数
            CheckUserInfoRequest(getUserInfoRequest);
            GetUserInfoResponse getUserInfoResponse = new GetUserInfoResponse();
            UserInfoEntity userInfoEntity = new UserInfoEntity();
            // 查询数据
            List<UserInfo> userInfoList = userInfoService.getUserInfoById(getUserInfoRequest.getUserId().get(0));
            UserInfo userInfo = userInfoList.get(0);
            // 检查SQL返回
            if(userInfo.getId() != getUserInfoRequest.getUserId().get(0)){
                throw new TException("SQL ERROR!!!");
            }
            // 为返回的结构体赋值
            userInfoEntity.setAge(userInfo.getAge().shortValue());
            userInfoEntity.setContinent(userInfo.getContinent().shortValue());
            userInfoEntity.setConsumptionCapacity(userInfo.getConsumptionCapacity());
            userInfoEntity.setDegree(userInfo.getDegree().shortValue());
            userInfoEntity.setGender(userInfo.getGender().shortValue());
            userInfoEntity.setId(userInfo.getId());
            userInfoEntity.setUserStatus(userInfo.getUserStatus().shortValue());
            userInfoEntity.setCreateTime(userInfo.getCreateTime().toString());
            userInfoEntity.setModifyTime(userInfo.getModifyTime().toString());
            getUserInfoResponse.setUserInfoEntity(new ArrayList<UserInfoEntity>((Collection<? extends UserInfoEntity>) userInfoEntity));
            getUserInfoResponse.setBaseResp(new BaseResp().setStatusCode(0).setStatusMsg("OK"));
            return getUserInfoResponse;
        } catch (TException e) {
            GetUserInfoResponse response = new GetUserInfoResponse();
            response.setBaseResp(new BaseResp().setStatusCode(1).setStatusMsg(e.getMessage()));
            return response;
        }
    }

    public List<UserInfo> BatchGetUserInfo(List<Integer> userIdList) {
        List<UserInfo> userInfoList = new ArrayList<>();
        for (Integer userId : userIdList) {
            for (UserInfo userInfo : userInfoService.getUserInfoById(userId)) {
                userInfoList.add(userInfo);
            }
        }
        return userInfoList;
    }
}
