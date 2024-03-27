package edu.java.scrapper;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.DriverManager;

public class MigrationsTest extends IntegrationTest {
    private Connection connection;

    private static final String TABLE_CHAT = "chat";
    private static final String TABLE_LINK = "link";
    private static final String TABLE_CHAT_LINK = "chat_link";

    @BeforeEach
    public void init() throws Exception {
        connection = DriverManager.getConnection(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword());
    }

    @Test
    @DisplayName("Chat table exists")
    public void chat_TableExistenceTest() throws Exception {
        assertTrue(connection.getMetaData().getTables(null, null, TABLE_CHAT, null).next());
    }

    @Test
    @DisplayName("Link table exists")
    public void link_TableExistenceTest() throws Exception {
        assertTrue(connection.getMetaData().getTables(null, null, TABLE_LINK, null).next());
    }

    @Test
    @DisplayName("Chat-link table exists")
    public void chat_link_TableExistenceTest() throws Exception {
        assertTrue(connection.getMetaData().getTables(null, null, TABLE_CHAT_LINK, null).next());
    }
}
