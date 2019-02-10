package ru.geekbrains.pocket.backend.service;

import ru.geekbrains.pocket.backend.domain.db.Group;
import ru.geekbrains.pocket.backend.domain.db.GroupMember;
import ru.geekbrains.pocket.backend.domain.db.User;
import ru.geekbrains.pocket.backend.enumeration.RoleGroupMember;

import java.util.List;

public interface GroupMemberService {

    GroupMember createGroupMember(GroupMember groupMember);

    GroupMember createGroupMember(Group group, User member, RoleGroupMember role);

    void deleteAllGroupMembers();

    void deleteGroupMember(GroupMember groupMember);

    GroupMember getGroupMember(Group group, User member);

    List<GroupMember> getGroupMembers(Group group);

    List<GroupMember> getGroupMembers(User member);

    GroupMember updateGroupMember(GroupMember groupMember);

}
