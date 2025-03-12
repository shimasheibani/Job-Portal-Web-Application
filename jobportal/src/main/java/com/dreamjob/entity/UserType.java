package com.dreamjob.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_type")
public class UserType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userTypeId;
    @Column(columnDefinition = "VARCHAR2(255)" , name = "user_type_name")
    private String userTypeName;
    @OneToMany(targetEntity = User.class, mappedBy = "userTypeId", cascade = CascadeType.ALL)
    private List<User> users;

    @Override
    public String toString() {
        return "UserType{" +
                "id=" + userTypeId +
                ", users=" + users +
                '}';
    }
}
