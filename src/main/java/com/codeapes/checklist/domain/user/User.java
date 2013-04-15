package com.codeapes.checklist.domain.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.codeapes.checklist.util.StringUtil;

@Entity
@Table(name = "cl_user")
@PrimaryKeyJoinColumn(name = "owner_executor_key")
public class User extends OwnerExecutorImpl {

    private static final long serialVersionUID = 3262325786490797832L;

    private List<UserGroup> groups;
    private List<Role> roles;
    private String username;
    private String firstName;
    private String lastName;
    private boolean active;
    private String formattedName;
    private String password;

    public synchronized void addUserRole(Role role) {
        if (roles == null) {
            roles = new ArrayList<Role>();
        }
        roles.add(role);
    }

    @Transient
    public String getName() {
        if (formattedName == null) {
            formatName();
        }
        return formattedName;
    }

    private void formatName() {
        final StringBuilder name = new StringBuilder();
        boolean hasLastName = false;
        if (StringUtil.isNotEmpty(lastName)) {
            name.append(lastName);
            hasLastName = true;
        }
        if (StringUtil.isNotEmpty(firstName)) {
            if (hasLastName) {
                name.append(", ");
            }
            name.append(firstName);
        }
        if (StringUtil.isNotEmpty(name.toString())) {
            formattedName = name.toString();
        } else {
            formattedName = this.getUsername();
        }
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "cl_user_group_rel", joinColumns = { @JoinColumn(name = "user_key") },
            inverseJoinColumns = { @JoinColumn(name = "group_key") })
    public List<UserGroup> getGroups() {
        return groups;
    }

    @ElementCollection
    @JoinTable(name = "cl_user_role", joinColumns = { @JoinColumn(name = "user_key") })
    @Column(name = "role", length = 25)
    @Enumerated(EnumType.STRING)
    public List<Role> getRoles() {
        return roles;
    }

    @Column(name = "password", length = 64, nullable = false)
    public String getPassword() {
        return password;
    }

    @Column(name = "username", length = 20, nullable = false, unique = true)
    public String getUsername() {
        return username;
    }

    @Column(name = "first_name", length = 50, nullable = true)
    public String getFirstName() {
        return firstName;
    }

    @Column(name = "last_name", length = 50, nullable = true)
    public String getLastName() {
        return lastName;
    }

    @Column(name = "active", nullable = false)
    public boolean isActive() {
        return active;
    }

    public void setGroups(List<UserGroup> groups) {
        this.groups = groups;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
