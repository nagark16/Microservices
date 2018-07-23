/*
 * HATEOAS -- Hypermedia As The Engine Of Application State
 * Internationlization -- i18n
 * */


package org.nagark.restfulwebservices.user;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserResource {
	
	@Autowired
	private UserDAO userDao;
	
	@GetMapping(path="/users")
	public List<User> retrieveAllUsers(){
		return userDao.findAll(); 
	}
	
	@GetMapping(path="/users/{id}")
	public Resource<User> find(@PathVariable int id) {
		User user = userDao.findOne(id);
		if(user == null)
			throw new UserNotFoundException("id-"+id);
		
		Resource<User> resource = new Resource<User>(user);
		/*
		 * Instead of calling ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn) 
		 * we are calling linkTo(methodOn(this.getClass()).retrieveAllUsers());
		 * becoz of import static we did above 
		 * */
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		resource.add(linkTo.withRel("all-users"));
		return resource;
	}
	
	@DeleteMapping(path="/users/{id}")
	public void delete(@PathVariable int id) {
		User user = userDao.deleteById(id);
		if(user == null)
			throw new UserNotFoundException("id-"+id);

	}
	
	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) { //valid is to do validation when we get request and return immediately if it is not proper
		User savedUser = userDao.save(user);
		
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(savedUser.getId())
			.toUri();
		
		return ResponseEntity.created(location).build();
	}
}
