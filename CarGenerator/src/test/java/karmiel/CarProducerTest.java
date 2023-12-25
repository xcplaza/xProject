package karmiel;

import karmiel.producer.CarProducer;
import karmiel.service.DatabaseAccessor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.reflect.Whitebox;
import org.springframework.test.util.ReflectionTestUtils;


@ExtendWith(MockitoExtension.class)
public class CarProducerTest {
    @Mock
    private DatabaseAccessor databaseAccessor;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private CarProducer carProducer;

    @BeforeEach
    public void setUp() throws SQLException {
        // Устанавливаем поведение моков
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("name")).thenReturn("TestBrand");

        // Устанавливаем мок в databaseAccessor
        Whitebox.setInternalState(carProducer, "databaseAccessor", databaseAccessor);
        Whitebox.setInternalState(databaseAccessor, "connection", connection);
    }


    @Test
    public void testGetBrandFromDatabase() throws SQLException {
        // Вызываем метод, который мы хотим протестировать
        String result = databaseAccessor.getBrandFromDatabase();

        // Проверяем, что результат соответствует ожиданиям
        assertTrue(result.matches(".*TestBrand.*"));

        // Проверяем, что методы были вызваны с ожидаемыми параметрами
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).executeQuery();
        verify(resultSet).next();
        verify(resultSet).getString("name");
    }
}
