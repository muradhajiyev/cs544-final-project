package edu.miu.cs544.medappointment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class OnStartup 
{
	/*
	@Autowired
	private ManagementService managementService;*/

	/*@Autowired
	private RoleRepository roleRepo;*/

	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event)
	{
		fillRoleTable();
		createAdminUser();
	}


	private void createAdminUser()
	{/*

		UserForm uForm = new StudentForm();
		UserForm.setName("admin");
		UserForm.setUsername("admin");
		UserForm.setEmail("admin@test.com");
		UserForm.setPassword("admin123");
		managementService.addAdminUser(uForm);*/
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
