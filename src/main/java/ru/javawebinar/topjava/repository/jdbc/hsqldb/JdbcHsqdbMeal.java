package ru.javawebinar.topjava.repository.jdbc.hsqldb;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.repository.jdbc.JdbcMealRepositoryImpl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
@Repository
@Profile("hsqldb")
public class JdbcHsqdbMeal extends JdbcMealRepositoryImpl {
    public JdbcHsqdbMeal(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    protected Timestamp dateTimeHandler(LocalDateTime localDateTime) {
        return  Timestamp.valueOf(localDateTime);
    }
}