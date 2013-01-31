package com.codeapes.checklist.domain.user;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "cl_user_group")
@PrimaryKeyJoinColumn(name = "owner_executor_key")
public class UserGroup extends OwnerExecutorImpl {

    private static final long serialVersionUID = 8632057779355106276L;

    private List<User> users;
    private String name;
    private String description;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "groups")
    public List<User> getUsers() {
        return users;
    }

    @Column(name = "description", length = 500, nullable = true)
    public String getDescription() {
        return description;
    }

    @Column(name = "name", length = 50, nullable = false, unique = true)
    public String getName() {
        return name;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}
