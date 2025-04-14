package br.com.fiap.espb.es.ddd.taskManager.domainmodel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="TASKS")
public class Task {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "ID")
    private @Getter @Setter Long id;
    
    @Column(nullable = false, name = "TITLE", length = 100 )
    @NotBlank(message="Title cannot be empty! motherfuckers")
    @Size(min=3, max=100, message="Title must be between 3 and 100 characters")
    private @Getter @Setter String title;
    
    @Column(nullable = true, name = "DESCRIPTION", length = 500 )
    @Size(max=500, message="Description cannot exceed 500 characters")    
    private @Getter @Setter String description;
    
    @NotNull(message="Creation Date cannot be null")
    @Column(nullable = false, name = "CREATION_DATE")
    private @Getter @Setter LocalDate creationDate;
    
    @Column(nullable = false, name = "COMPLETION_DATE")
    private @Getter @Setter LocalDate completionDate;
    
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status cannot be null.")
    @Column(nullable = false, name = "STATUS")
    private @Getter @Setter TaskStatus status;
    
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Priority cannot be null.")
    @Column(nullable = false, name = "PRIORITY")
    private @Getter @Setter TaskPriority priority; 
    
    
    public enum TaskStatus {
        PENDING, IN_PROGRESS, COMPLETED
    }
    
    public enum TaskPriority{
        LOW(1), MEDIUM(3), HIGH(5);
        
        private final int value;
        
        TaskPriority( int value){
            this.value = value;
        }
        
        public int getValue(){
            return this.value;
        }
        
        public static TaskPriority fromValue( int value ){
            for( TaskPriority priority : TaskPriority.values()){
                if( priority.getValue() == value )
                    return priority;
            }
            throw new IllegalArgumentException("Invalid priority value: " + value);
        }
    }
    
    
    
}
