package ru.javawebinar.topjava.repository.jdbc.postgres;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.repository.jdbc.JdbcMealRepositoryImpl;

import java.time.LocalDateTime;

@Repository
@Profile("postgres")
public class JdbcPostgMeal extends JdbcMealRepositoryImpl {
    @Override
    protected LocalDateTime dateTimeHandler(LocalDateTime localDateTime) {
        return localDateTime;
    }

    public JdbcPostgMeal(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }
}
