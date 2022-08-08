package jp.kobespiral.sandazerocarbonappbackend.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.UserValidationException;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Administrator;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.AdministratorRepository;
import static jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.UserErrorCode.*;

/**
 * @author sato
 */
@Service
public class AdministratorService {
    @Autowired
    AdministratorRepository administratorRepository;

    public Administrator getAdministrator(Long administratorId,String password){
        Administrator admin = administratorRepository.findByAdministratorIdAndPassword(administratorId,password);
        if(admin==null){
            throw new  UserValidationException(USER_DOES_NOT_EXIST,"Not exist the administrator", String.format("Try to get adminId : %d",administratorId));
        }
        else{
            return admin;
        }
    }
}
