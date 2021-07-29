package br.com.passwordmanagement.dto;

import br.com.passwordmanagement.enums.Priority;

public class PasswordRequestDto {

    private Priority priority;

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}
