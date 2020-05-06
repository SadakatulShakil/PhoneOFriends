package com.example.phoneofriends.View.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GroupDetail implements Serializable {

    private String groupId;
    private String adminId;
    private String groupName;
    private String groupMemberCount;

    public GroupDetail() {
    }

    public GroupDetail(String groupId, String adminId, String groupName, String groupMemberCount) {
        this.groupId = groupId;
        this.adminId = adminId;
        this.groupName = groupName;
        this.groupMemberCount = groupMemberCount;
    }

    public String getGroupMemberCount() {
        return groupMemberCount;
    }

    public void setGroupMemberCount(String groupMemberCount) {
        this.groupMemberCount = groupMemberCount;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
