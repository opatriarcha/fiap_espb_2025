import br.com.fiap._2espb20252_ddd.cp02.domain.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.github.javafaker.Faker;
import static org.junit.jupiter.api.Assertions.*;


public class EmployeeTest {

    private Employee emptyEmployee;

    private Faker faker;

    private Employee oneEmployee;

    private Employee nullEmployee;

    private static final Double BASE_WAGE = 1000d;

    @BeforeEach
    public void startup(){
        faker = new Faker();
        this.emptyEmployee = new Employee();
        this.oneEmployee = new Employee(faker.number().numberBetween(1L, 10000L),
                    faker.name().fullName(),
                    faker.idNumber().valid(),
                BASE_WAGE
                );
    }

    @Test
    public void testFinalWage(){
        assertEquals(BASE_WAGE, this.oneEmployee.getFinalWage());
    }

    @Test
    public void whenFinalAgeCalledFromANullObjectMustBeError(){
        assertThrows(NullPointerException.class, () -> {
            double wage = nullEmployee.getFinalWage();
        });
    }

}
