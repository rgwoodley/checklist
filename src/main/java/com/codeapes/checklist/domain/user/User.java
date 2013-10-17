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

import org.apache.commons.lang.StringUtils;

@Entity
@Table(name = "cl_user")  // NOSONAR
@PrimaryKeyJoinColumn(name = "owner_executor_key")  // NOSONAR
public class User extends OwnerExecutorImpl {

    private static final long serialVersionUID = 3262325786490797832L;

    private List<UserGroup> groups;
    private List<Role> roles;
    private String username;
    private String firstName;
    private String lastName;
    private boolean active;
    private String password;

    public synchronized void addUserRole(Role role) {
        if (roles == null) {
            roles = new ArrayList<Role>();
        }
        roles.add(role);
    }

    private String formatName() {
        String formattedName = null;
        if (!StringUtils.isBlank(firstName) && !StringUtils.isBlank(lastName)) {
            final StringBuilder theName = new StringBuilder(firstName);
            theName.append(" ");
            theName.append(lastName);
            formattedName = theName.toString();
        } else {
            formattedName = username;
        }
        return formattedName;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "cl_user_group_rel", joinColumns = { @JoinColumn(name = "user_key") },
            inverseJoinColumns = { @JoinColumn(name = "group_key") })  // NOSONAR
    public List<UserGroup> getGroups() {
        return groups;
    }

    @ElementCollection
    @JoinTable(name = "cl_user_role", joinColumns = { @JoinColumn(name = "user_key") })  // NOSONAR
    @Column(name = "role", length = 25)  // NOSONAR
    @Enumerated(EnumType.STRING)
    public List<Role> getRoles() {
        return roles;
    }

    @Column(name = "password", length = 64, nullable = false)  // NOSONAR
    public String getPassword() {
        return password;
    }

    @Column(name = "username", length = 20, nullable = false, unique = true)  // NOSONAR
    public String getUsername() {
        return username;
    }

    @Column(name = "first_name", length = 50, nullable = true)  // NOSONAR
    public String getFirstName() {
        return firstName;
    }

    @Column(name = "last_name", length = 50, nullable = true)  // NOSONAR
    public String getLastName() {
        return lastName;
    }

    @Column(name = "active", nullable = false)  // NOSONAR
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
        setName(formatName());
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        setName(formatName());
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        setName(formatName());
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    public void addGroup(UserGroup group) {
        if (groups == null) {
            groups = new ArrayList<UserGroup>();
        }
        groups.add(group);
    }

}
