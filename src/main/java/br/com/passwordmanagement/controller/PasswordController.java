package br.com.passwordmanagement.controller;

import br.com.passwordmanagement.dto.PasswordRequestDto;
import br.com.passwordmanagement.dto.TextMessageDTO;
import br.com.passwordmanagement.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/password")
public class PasswordController {


    @Autowired
    SimpMessagingTemplate template;

    @Autowired
    private PasswordService service;

    @PostMapping("/request")
    public ResponseEntity<String> requestPassword(@RequestBody PasswordRequestDto passRequest){
        return ResponseEntity.ok(service.requestPassword(passRequest));
    }

    @GetMapping("/next")
    public ResponseEntity<?> getNextPassword(){
        String nextPassword = service.getNextPassword();
        if(nextPassword != null){
            TextMessageDTO textMessageDTO = new TextMessageDTO();
            textMessageDTO.setMessage(nextPassword);
            template.convertAndSend("/topic/message", textMessageDTO);
            return ResponseEntity.ok(nextPassword);
        }
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/reset")
    public ResponseEntity<?> resetPasswords(){
        service.resetPasswords();
        TextMessageDTO textMessageDTO = new TextMessageDTO();
        template.convertAndSend("/topic/message", textMessageDTO);
        return ResponseEntity.ok().build();
    }


}
