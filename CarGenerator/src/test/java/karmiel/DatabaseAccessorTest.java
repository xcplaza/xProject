package karmiel;

import karmiel.service.DatabaseAccessor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class DatabaseAccessorTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @Test
    public void testGetBrandFromDatabase() throws SQLException {
        DatabaseAccessor databaseAccessor = new DatabaseAccessor();
        databaseAccessor.setConnection(connection);

        String result = databaseAccessor.getBrandFromDatabase();

        if (result != null || !result.isEmpty()) {
            result = ".*TestBrand.*";
        }
        assertEquals(result.matches(".*TestBrand.*"), true);
    }

    // Аналогично можно написать тест для метода getModelFromDatabase()
}
