package edu.miu.cs544.medappointment.shared;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class RoleDto {
    private Long id;
    private String name;
    public RoleDto() {
        super();
        // TODO Auto-generated constructor stub
    }

    public RoleDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }
}
