package com.pef.non_cog.modalclass;

public class GroupNameID {
    String groupId;
    String groupName;

    public GroupNameID(String groupName) {
        this.groupName = groupName;
    }

    public GroupNameID(String groupId, String groupName) {
        this.groupId = groupId;
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return groupName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
