package br.com.fiap.espb.es.ddd.taskManager.controllers;

import br.com.fiap.espb.es.ddd.taskManager.domainmodel.Task;
import br.com.fiap.espb.es.ddd.taskManager.dto.TaskDTO;
import br.com.fiap.espb.es.ddd.taskManager.mapper.TaskMapper;
import br.com.fiap.espb.es.ddd.taskManager.service.TaskService;
import java.util.logging.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/tasks")
public class TaskApiController {
    
    private final TaskService taskService;
 
    
    private static final Logger LOG = Logger.getLogger(TaskApiController.class.getName());
    
    

    public TaskApiController(TaskService taskService) {
        this.taskService = taskService;
    }
    
    //http://localhost:8080/api/tasks/1
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id){
        
        LOG.fine("Entering getTaskById controller method!");
        
        Task task = taskService.findById(id).orElse(null);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(TaskMapper.toDTO(task));
    }
    
    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDto){
        Task savedTask = this.taskService.save(TaskMapper.toEntity(taskDto));
        return ResponseEntity.ok(TaskMapper.toDTO(savedTask));
    }
}
