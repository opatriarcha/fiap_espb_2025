import br.com.fiap._2espb20252_ddd.cp02.domain.Manager;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ManagerTest {

    private Manager emptyManager;

    private Faker faker;

    private Manager one10Manager;

    private Manager one105Manager;

    private Manager nullManager;

    private static final Double BASE_WAGE = 10000d;
    private static final Double BONUS_10 = 10d;
    private static final Double BONUS_10_5 = 10.5d;
    private static final Double WAGE_10 = 11000d;
    private static final Double WAGE_10_5 = 11050d;


    @BeforeEach
    public void startup(){
        faker = new Faker();
        this.emptyManager = new Manager();
        this.one10Manager = new Manager(faker.number().numberBetween(1L, 10000L),
                faker.name().fullName(),
                faker.idNumber().valid(),
                BASE_WAGE,
                BONUS_10

        );

        this.one105Manager = new Manager(faker.number().numberBetween(1L, 10000L),
                faker.name().fullName(),
                faker.idNumber().valid(),
                BASE_WAGE,
                BONUS_10_5
                );
    }

    @Test
    public void whenFinalAgeCalledFromANullObjectMustBeError(){
        assertThrows(NullPointerException.class, () -> {
            double wage = nullManager.getFinalWage();
        });
    }

    @Test
    public void testFinalWage10Bonus(){
        assertEquals(WAGE_10, this.one10Manager.getFinalWage());
    }

    @Test
    public void testFinalWage10_5Bonus(){
        assertEquals(WAGE_10_5, this.one105Manager.getFinalWage());
    }
}
