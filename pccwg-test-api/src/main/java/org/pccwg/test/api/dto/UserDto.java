package org.pccwg.test.api.dto;

import org.pccwg.test.api.entity.Uzer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class UserDto {

	private Long id;
	
	@NotEmpty(message = "name is required")
	@Pattern(regexp = "^(?!.*,.*$).*$", message = "Name should not contain commas")
    private String name;
	
	@NotEmpty(message = "email is required")
	@Email(message = "invalid email")
    private String email;
	
	@NotEmpty(message = "password is required")
    private String password;
    private Boolean deleted;

    public UserDto() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

    public Uzer toEntity() {
    	Uzer entity = new Uzer();
    	entity.setName(this.getName());
    	entity.setEmail(this.getEmail());
    	entity.setPassword(this.getPassword());
    	entity.setDeleted(this.deleted != null ? this.deleted : false);
    	return entity;
    }
    
    public static UserDto toDto(Uzer entity) {
    	UserDto dto = new UserDto();
    	dto.setId(entity.getId());
    	dto.setName(entity.getName());
    	dto.setEmail(entity.getEmail());
    	dto.setPassword(entity.getPassword());
    	dto.setDeleted(entity.getDeleted());
    	return dto;
    }
}
