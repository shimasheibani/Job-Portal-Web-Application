package com.dreamjob.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name= "users_job_portal")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR2(255)", unique = true)
    private String email;

    private boolean is_active;
    @Column(columnDefinition = "VARCHAR2(255)")
    @NotEmpty
    private String password;
    @Column(name = "registration_date")
    @DateTimeFormat(pattern="dd-MM-yyyy")
    private Date registrationDate;
    @ManyToOne(cascade =CascadeType.ALL)
    @JoinColumn(name="userTypeId", referencedColumnName = "userTypeId")
    private UserType userTypeId;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", is_active=" + is_active +
                ", password='" + password + '\'' +
                ", registrationDate=" + registrationDate +
                ", userType=" + userTypeId +
                '}';
    }
}
