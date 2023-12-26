package karmiel;

import karmiel.service.DatabaseAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql(scripts = "doctors-patients-visits.sql")
public class CarProducerTest {
    private static final long CAR_ID = 859l;
    private static final long CAR_NO_ID = 001l;

    @Autowired
    DatabaseAccessor databaseAccessor;

}
