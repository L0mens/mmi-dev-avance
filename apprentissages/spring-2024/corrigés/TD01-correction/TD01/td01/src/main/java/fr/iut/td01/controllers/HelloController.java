package fr.iut.td01.controllers;

import org.springframework.web.bind.annotation.RestController;

import fr.iut.td01.models.Person;
import fr.iut.td01.services.FileService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller for simple REST APIs
 */
@RestController
public class HelloController {
    
    private FileService fileService;

    public HelloController(FileService fileService){
        this.fileService = fileService;
    }

    /**
     * Simply send a hello
     * @return a hello text
     */
    @GetMapping("/hello")
    public String helloWorld(){
        return "Hello, world !";
    }
    
    /**
     * Send hi to a specific name
     * @param name the name of the person
     * @return a welcome message 
     */
    @GetMapping("/hi")
    public String hi(@RequestParam String name) {
        return "Hi "+name+" !";
    }
    
    /**
     * Answer an person object 
     * @return the person
     */
    @GetMapping("/whois")
    public Person whois(){
        return new Person("toto",15);
    }

    /**
     * Send a person
     * @param p the person sent
     * @return a message with the properties of the person
     */
    @PostMapping("/IAm")
    public String iAm(@RequestBody Person p){
        return String.format("You are %s and you're %d years old", p.getName(), p.getAge());
    }

    /**
     * Read a simple image
     * @return an image, JPEG format
     * @throws IOException if the file can not be readen
     */
    @GetMapping(value="/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage() throws IOException{
        return fileService.readFromResources("/images/explorer.jpg");
    }

    /**
     * get all file names of images
     * @return string list with files names
     */
    @GetMapping("/images")
    public List<String> getImageNames(){
        return fileService.listFiles();
    }

    /**
     * get a specific image
     * @param fileName the file name
     * @return the bytes of the image (JPEG)
     * @throws FileNotFoundException if file does not exist
     * @throws IOException if file can not be readen
     */
    @GetMapping(value="/get_image", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(String fileName) throws FileNotFoundException, IOException {

        return fileService.readFromPath(fileName);
    }
    
}
