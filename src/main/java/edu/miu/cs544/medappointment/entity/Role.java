package edu.miu.cs544.medappointment.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Role {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@ManyToMany
	@JoinTable(name = "User_Role", 
    joinColumns = { @JoinColumn(name = "UserId") }, 
    inverseJoinColumns = { @JoinColumn(name = "RoleId") })
	private List<User> user;

	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Role(String name, List<User> user) {
		super();
		this.name = name;
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getUser() {
		return user;
	}

	public void setUser(List<User> user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}
	
	
	

	
}
