package nl.hu.sd.tennis.presentation.dto.request;

import javax.validation.constraints.NotNull;

public class PlayerRequestDTO {
    @NotNull
    public int id;

    @NotNull
    public String name;
}
