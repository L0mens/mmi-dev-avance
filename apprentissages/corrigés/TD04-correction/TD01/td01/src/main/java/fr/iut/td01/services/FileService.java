package fr.iut.td01.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import fr.iut.td01.errors.ApiFileNotFoundException;

/**
 * Service for files management
 */
@Service
public class FileService {
    private String basePath;

    public FileService(@Value("${images_path}") String path){
        this.basePath=path;
    }

    /**
     * Read a file from the resources
     * @param path the path of the file in resources folder
     * @return bytes of the file
     * @throws IOException if something bad happens
     */
    public byte[] readFromResources(String path) throws IOException{
        InputStream stream = getClass().getResourceAsStream(path);   
        byte[] bytes= stream.readAllBytes();
        return bytes;
    }

    /**
     * List all files in the base folder
     * @return names of all the files
     */
    public List<String> listFiles(){
        ArrayList<String> fileNames = new ArrayList<>();
        File folder = new File(this.basePath);
        File[] files = folder.listFiles();
        for(File file : files){
            if(file.isFile()){
                fileNames.add(file.getName());
            }
        }
        return fileNames;
    }

    /**
     * @param fileName the name of the file, into the base folder
     * @return bytes of the file
     * @throws ApiFileNotFoundException if file not found or not readable
     */
    public byte[] readFromPath(String fileName) {
        byte[] bytes=null;
        try{
            try(FileInputStream stream = new FileInputStream(Paths.get(this.basePath,fileName).toString()))
            {
                bytes = stream.readAllBytes();
            }
        }
        catch(IOException ex){
            throw new ApiFileNotFoundException(fileName);
        }
        return bytes;
    }
}
