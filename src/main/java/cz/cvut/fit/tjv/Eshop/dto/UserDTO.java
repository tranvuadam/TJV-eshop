package cz.cvut.fit.tjv.Eshop.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class UserDTO {

    private Long id;

    public String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "d.M.yyyy")
    public LocalDate dateOfBirth;

    public UserDTO() {
    }

    public UserDTO(String name, LocalDate dateOfBirth, Long id) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
