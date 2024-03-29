package com.user.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.service.entities.Hotel;
import com.user.service.services.HotelService;

@RestController
@RequestMapping("/hotels")
public class HotelController {
	
	@Autowired
	HotelService service;
	
	
	@PreAuthorize("hasAuthority('SCOPE_internal') || hasRole('ROLE_ADMIN')")
	@GetMapping
	public List<Hotel> getall(){
		return service.getAll();
	}
	
	@PreAuthorize("hasAuthority('SCOPE_internal') || hasRole('ROLE_ADMIN')")
	@GetMapping("/id/{id}")
	public Hotel get(@PathVariable("id") int id) {
		return service.gethotel(id);
	}
	
	
	@PreAuthorize("hasAuthority('SCOPE_internal') || hasRole('ROLE_ADMIN')")
	@PostMapping
	public Hotel saveHotel(@RequestBody Hotel h) {
		return service.savehotel(h);
	}
	
	
	@PreAuthorize("hasAuthority('SCOPE_internal') || hasRole('ROLE_ADMIN')")
	@PutMapping("/userId/{id}/hotelId/{hid}")
	public int updatehotel(@PathVariable int id, @PathVariable int hid) {
		service.gethotel(id).setHotelId(hid);
		return hid;
	}

}
