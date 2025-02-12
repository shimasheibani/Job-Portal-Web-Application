import com.shima.project.common.Spring;
import com.shima.project.entity.Car;
import com.shima.project.entity.Person;
import com.shima.project.service.PersonService;

import java.util.Arrays;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        PersonService personService = (PersonService) Spring.getBean("PersonService");
        personService.save(Person.builder().name("shima")
                        .carList(Arrays.asList(
                                Car.builder().model("BMW").build(),
                                Car.builder().model("BMW").build()

                        ))
                .build());
    }
}