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
	private List<User> users;

	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Role(String name, List<User> user) {
		super();
		this.name = name;
		this.users = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getUser() {
		return users;
	}

	public void setUser(List<User> user) {
		this.users = user;
	}
	public boolean addUser(User user) {
		return users.add(user);
	}
	public boolean removeUser(User user) {
		return users.remove(user);
	}
	public Long getId() {
		return id;
	}
	
	
	

	
}
