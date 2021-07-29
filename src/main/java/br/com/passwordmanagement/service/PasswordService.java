package br.com.passwordmanagement.service;

import br.com.passwordmanagement.dto.PasswordRequestDto;
import br.com.passwordmanagement.enums.Priority;
import br.com.passwordmanagement.model.Password;
import br.com.passwordmanagement.repository.PasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PasswordService {

    @Autowired
    private PasswordRepository repository;


    public void resetPasswords(){
        repository.deleteAll();
    }

    public String getNextPassword(){
        PageRequest pageRequest = PageRequest.of(0, 1, Sort.by("priority").descending().and(Sort.by("number").ascending()));
        List<Password> nextPasswordList = repository.getNext(pageRequest);
        if(!nextPasswordList.isEmpty()){
            Password password = nextPasswordList.get(0);
//            password.setNext(true);
//            repository.save(password);
            String parsedPassword = (password.isPriority() ? "P" : "N") + String.format("%04d" , password.getNumber());
            repository.delete(password);
            return parsedPassword;
        }
        return null;
//        return repository.getNext(pageRequest).stream().findFirst().orElse(null);
    }

    public String requestPassword(PasswordRequestDto passRequest){
        boolean p = Priority.P.equals(passRequest.getPriority());
        List<Password> mostRecentPasswordByPriority = repository.findMostRecentPasswordByPriority(p, PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "number")));
        Password newPassword = new Password();
        newPassword.setNext(false);
        newPassword.setPriority(p);
        if(!mostRecentPasswordByPriority.isEmpty()){
            newPassword.setNumber(mostRecentPasswordByPriority.get(0).getNumber() + 1);
        }else{
            newPassword.setNumber(1);
        }
        Password saved = repository.save(newPassword);
        return passRequest.getPriority().name().toString() + String.format("%04d" , saved.getNumber());
    }

    public static void main(String[] args) {
        String padded = String.format("%04d" , 110);
        System.out.println("Integer number left padded with zero : " + padded);
    }
}
