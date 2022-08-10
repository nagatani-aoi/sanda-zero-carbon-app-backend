package jp.kobespiral.sandazerocarbonappbackend.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.UserValidationException;
import jp.kobespiral.sandazerocarbonappbackend.domain.entity.Administrator;
import jp.kobespiral.sandazerocarbonappbackend.domain.repository.AdministratorRepository;
import static jp.kobespiral.sandazerocarbonappbackend.cofigration.exception.ErrorCode.*;

/**
 * @author sato
 */
@Service
public class AdministratorService {
    @Autowired
    AdministratorRepository administratorRepository;

    public Administrator getAdministrator(Long administratorId,String password){
        if(!administratorRepository.existsByAdministratorIdAndPassword(administratorId, password)){
            throw new  UserValidationException(ADMIN_DOES_NOT_EXISTS,"get administrator", String.format("adminId : %d  ,password : %d doesn't exist",administratorId,password));
        }
        else{
            Administrator admin = administratorRepository.findByAdministratorIdAndPassword(administratorId,password);
            return admin;
        }
    }
}
