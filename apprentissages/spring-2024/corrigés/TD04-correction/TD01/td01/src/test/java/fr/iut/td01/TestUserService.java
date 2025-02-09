package fr.iut.td01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import fr.iut.td01.models.UserData;
import fr.iut.td01.repositories.UserRepository;
import fr.iut.td01.services.UserService;

public class TestUserService {
    @Test
    public void testFindUser(){
         // create a mock repository, then the service
         UserRepository repository = Mockito.mock(UserRepository.class);
         UserService service = new UserService(repository);

         // mock a user
         UserData user = Mockito.mock(UserData.class);
         Mockito.when(user.getLogin()).thenReturn("user");
         Mockito.when(user.getPassword()).thenReturn("toto");
         Mockito.when(user.isAdmin()).thenReturn(false);
    
         // mock the repository action
         Mockito.when(repository.findById(anyString())).thenAnswer(invocation->{
            Optional<UserData> result = Optional.empty();

            if(invocation.getArgument(0).equals("user")){
                result = Optional.of(user);
            }
            return result;
         });

         // test if an existing user is found
         var data = service.loadUserByUsername("user");
         assertEquals("user", data.getUsername());
         assertEquals("toto", data.getPassword());
         assertEquals(1, data.getAuthorities().size());
         
         // test if an non existing user is not found
         assertThrows(UsernameNotFoundException.class,()->{service.loadUserByUsername("zrai");} );
    }
}
