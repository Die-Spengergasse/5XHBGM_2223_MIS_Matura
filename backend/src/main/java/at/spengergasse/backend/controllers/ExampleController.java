package at.spengergasse.backend.controllers;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;

import at.spengergasse.backend.models.Comment;
import at.spengergasse.backend.models.ExampleModel;
import at.spengergasse.backend.repositories.CommentRepository;
import at.spengergasse.backend.repositories.ExampleRepository;
import lombok.AllArgsConstructor;
import lombok.val;
import lombok.experimental.var;

import java.util.List;
import java.util.stream.StreamSupport;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/example")
public class ExampleController {
    
    ExampleRepository exampleRepository;

    CommentRepository commentRepository;

    Gson gson;

    @GetMapping("/")
    List<ExampleModel> getAll(){
        return StreamSupport.stream(exampleRepository.findAll().spliterator(), false).toList();
    }

    @PutMapping("/")
    ExampleModel putExampleModel(@RequestBody String exampleModelStr){

        ExampleModel exampleModel =  gson.fromJson(exampleModelStr, ExampleModel.class);

        if(exampleModel.getId() != null && exampleModel.getId() == 0){
            exampleModel.setId(null);
        }

        for (Comment comment : exampleModel.getComments()) {
            if(comment.getId() != null && comment.getId()== 0){
                comment.setId(null);
            }
        }

        val comments = StreamSupport.stream(commentRepository.saveAll(exampleModel.getComments()).spliterator(), false).toList() ;
        exampleModel.setComments(comments);

        return exampleRepository.save(exampleModel);
    }

    @GetMapping("/{id}")
    ExampleModel getExampleModel(@PathVariable Long id){
        return exampleRepository.findById(id).orElse(null);
    }

}
