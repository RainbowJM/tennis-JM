package nl.hu.sd.tennis.presentation.dto.response;

import org.springframework.hateoas.RepresentationModel;

public class PlayerResponseDTO extends RepresentationModel {
    private int id;

    private String name;

    public PlayerResponseDTO(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
