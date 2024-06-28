package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDaoTest {

    @BeforeEach // Test코드를 실행하기에 앞서 실행해야할 코드가 있다면 여기 작성
    void setUp() {
        // 각 test 진행 전 db_schema.sql 파일을 읽어서 그 안의 sql 쿼리들을 실행해 줌
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("db_schema.sql"));
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
    }

    @Test
    void createTest() throws SQLException {
        // Dao : Data Access Object
        // DB 작업을 위임함
        UserDao userDao = new UserDao();

        userDao.create(new User("wizard", "password", "name", "email"));

        User user = userDao.findByUserId("wizard");

        assertThat(user).isEqualTo(new User("wizard", "password", "name", "email"));
    }
}
