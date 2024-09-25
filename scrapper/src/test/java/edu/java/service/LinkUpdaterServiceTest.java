package edu.java.service;

import edu.java.dto.LinkDTO;
import edu.java.repository.jdbc.JdbcLinksRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
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
    private JdbcLinksRepository mockRepository;

    @Autowired
    private LinkUpdaterService linkUpdaterService;

    @Test
    @DisplayName("Найти н-ное количество ссылок")
    public void returnNNumberOfLinks() {
        // given
        int n = 10;
        List<LinkDTO> mockList = new ArrayList<>();
        LinkDTO mockLink = Mockito.mock(LinkDTO.class);
        IntStream.range(0, n).forEach (i ->
            mockList.add(mockLink)
        );

        // when
        when(this.mockRepository.findNLinksLastUpdated(n)).thenReturn(mockList);
        List<LinkDTO> answer = this.linkUpdaterService.findNLinksToUpdate(n);

        // then
        assertThat(answer.size()).isEqualTo(n);
    }
}
