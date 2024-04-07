package edu.java.service;

import edu.java.dto.LinkDTO;
import edu.java.repository.jdbc.JdbcLinkRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = LinkUpdaterService.class)
public class LinkUpdaterServiceTest {
    @MockBean
    JdbcLinkRepository mockRepository;

    @Autowired
    LinkUpdaterService linkUpdaterService;

    @Test
    @DisplayName("Найти н-ное количество ссылок")
    public void returnNNumberOfLinks() {
        // given
        int n = 10;
        List<LinkDTO> mockList = new ArrayList<>();
        LinkDTO mockLink = Mockito.mock(LinkDTO.class);
        for (int i = 0; i < n; i++) {
            mockList.add(mockLink);
        }

        // when
        when(mockRepository.findNLinksLastUpdated(n)).thenReturn(mockList);
        List<LinkDTO> answer = linkUpdaterService.findNLinksToUpdate(n);

        // then
        assertThat(answer.size()).isEqualTo(n);
    }
}
