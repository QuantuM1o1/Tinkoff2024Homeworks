package edu.java.service;

import edu.java.apiException.LinkAlreadyExistsException;
import edu.java.configuration.ResourcesConfig;
import edu.java.dto.LinkDTO;
import edu.java.property.SupportedResource;
import edu.java.repository.jdbc.JdbcUserLinkRepository;
import edu.java.scrapper.IntegrationTest;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
public class LinkServiceTest extends IntegrationTest {
    @Autowired
    private LinkService linkService;

    @Autowired
    private JdbcUserLinkRepository userLinkRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @MockBean
    private ResourcesConfig mockConfig;

    @Test
    @DisplayName("Добавление ссылки")
    public void add() throws LinkAlreadyExistsException {
        // given
        long id = 1L;
        String url = "https://stackoverflow.com/questions/31322043/project-euler-8-answer-fails-to-be-true";
        String domain = "stackoverflow.com";
        SupportedResource mockResource = Mockito.mock(SupportedResource.class);
        Map<String, SupportedResource> mockMap = new HashMap<>();
        mockMap.put(domain, mockResource);
        String addUser = "INSERT INTO users (chat_id, added_at) VALUES (?, ?)";
        LocalDateTime currentTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(currentTime);
        String addLink
            = "INSERT INTO links (url, added_at, updated_at, last_activity, site_id) VALUES (?, ?, ?, ?, ?)";

        // when
        jdbcTemplate.update(addUser, id, timestamp);
        jdbcTemplate.update(addLink, url, timestamp, timestamp, timestamp, 1);
        when(mockConfig.supportedResources()).thenReturn(mockMap);
        when(mockResource.urlPattern()).thenReturn("^https?://stackoverflow\\.com/.*/(.*)/.*$");
        linkService.add(id, url, domain);
        List<LinkDTO> answer = userLinkRepository.findAllLinksByUser(id);

        // then
        assertThat(answer.size()).isEqualTo(1);
        assertThat(answer.getFirst().url()).isEqualTo(url);
        assertThat(answer.getFirst().domainName()).isEqualTo(domain);
    }

    @Test
    @DisplayName("Удаление ссылки")
    public void remove() {
        // given
        long id = 1L;
        String url = "https://stackoverflow.com/questions/31322043/project-euler-8-answer-fails-to-be-true";
        String addUser = "INSERT INTO users (chat_id, added_at) VALUES (?, ?)";
        LocalDateTime currentTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(currentTime);
        String addLink
            = "INSERT INTO links (url, added_at, updated_at, last_activity, site_id) VALUES (?, ?, ?, ?, ?)";
        String selectLink
            = "SELECT * FROM links l INNER JOIN links_sites ls ON l.site_id = ls.id WHERE deleted_at IS NULL";

        // when
        jdbcTemplate.update(addUser, id, timestamp);
        jdbcTemplate.update(addLink, url, timestamp, timestamp, timestamp, 1);
        List<LinkDTO> links = jdbcTemplate.query(selectLink, new DataClassRowMapper<>(LinkDTO.class));
        userLinkRepository.addUserLink(id, links.getFirst().linkId());

        List<LinkDTO> answer = userLinkRepository.findAllLinksByUser(id);
        assertThat(answer.size()).isEqualTo(1);

        linkService.remove(id, url);
        answer = userLinkRepository.findAllLinksByUser(id);

        // then
        assertThat(answer.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("Вывод всех ссылок по пользователю")
    public void returnAllLinksForUser() {
        // given
        long id = 1L;
        String url = "https://stackoverflow.com/questions/31322043/project-euler-8-answer-fails-to-be-true";
        String domain = "stackoverflow.com";
        String addUser = "INSERT INTO users (chat_id, added_at) VALUES (?, ?)";
        LocalDateTime currentTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(currentTime);
        String addLink
            = "INSERT INTO links (url, added_at, updated_at, last_activity, site_id) VALUES (?, ?, ?, ?, ?)";
        String selectLink
            = "SELECT * FROM links l INNER JOIN links_sites ls ON l.site_id = ls.id WHERE deleted_at IS NULL";

        // when
        jdbcTemplate.update(addUser, id, timestamp);
        jdbcTemplate.update(addLink, url, timestamp, timestamp, timestamp, 1);
        List<LinkDTO> links = jdbcTemplate.query(selectLink, new DataClassRowMapper<>(LinkDTO.class));
        userLinkRepository.addUserLink(id, links.getFirst().linkId());
        List<LinkDTO> answer = (List<LinkDTO>) linkService.listAll(id);

        // then
        assertThat(answer.size()).isEqualTo(1);
        assertThat(answer.getFirst().url()).isEqualTo(url);
        assertThat(answer.getFirst().domainName()).isEqualTo(domain);
    }

    @Test
    @DisplayName("Вывод всех пользователей по ссылке")
    public void returnAllUsersForLink() {
        // given
        long id = 1L;
        String url = "https://stackoverflow.com/questions/31322043/project-euler-8-answer-fails-to-be-true";
        String addUser = "INSERT INTO users (chat_id, added_at) VALUES (?, ?)";
        LocalDateTime currentTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(currentTime);
        String addLink
            = "INSERT INTO links (url, added_at, updated_at, last_activity, site_id) VALUES (?, ?, ?, ?, ?)";
        String selectLink
            = "SELECT * FROM links l INNER JOIN links_sites ls ON l.site_id = ls.id WHERE deleted_at IS NULL";

        // when
        jdbcTemplate.update(addUser, id, timestamp);
        jdbcTemplate.update(addLink, url, timestamp, timestamp, timestamp, 1);
        List<LinkDTO> links = jdbcTemplate.query(selectLink, new DataClassRowMapper<>(LinkDTO.class));
        userLinkRepository.addUserLink(id, links.getFirst().linkId());
        List<Long> answer = (List<Long>) linkService.findAllUsersForLink(links.getFirst().linkId());

        // then
        assertThat(answer.size()).isEqualTo(1);
        assertThat(answer.getFirst()).isEqualTo(id);
    }
}
