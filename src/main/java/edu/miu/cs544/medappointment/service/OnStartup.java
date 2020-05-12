package edu.miu.cs544.medappointment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

public class OnStartup 
{
	
	@Autowired
	private StudentManagementService studentManagementService;

	@Autowired
	private RoleRepository roleRepo;

	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event)
	{
		fillRoleTable();
		createAdminUser();
	}


	private void createAdminUser()
	{/*

		StudentForm studentForm = new StudentForm();
		studentForm.setName("admin");
		studentForm.setUsername("admin");
		studentForm.setEmail("admin@gmail.com");
		studentForm.setPassword("admin123");
		studentManagementService.addAdminUser(studentForm);*/
	}

	private void fillRoleTable()
	{/*
		List<Role> roles = roleRepo.findAll();

		if (roles == null || roles.isEmpty()) 
		{
			roleRepo.save(new Role("ROLE_USER"));
			roleRepo.save(new Role("ROLE_ADMIN"));
			roleRepo.save(new Role("ROLE_CHECKER"));
		}*/
	}
}
