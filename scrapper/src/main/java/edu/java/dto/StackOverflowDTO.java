package edu.java.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StackOverflowDTO {
    private List<Item> items;
    class Item {
        @JsonProperty("question_id")
        private long id;
        @JsonProperty("last_activity_date")
        private OffsetDateTime lastActivityDate;
        private String link;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public OffsetDateTime getLastActivityDate() {
            return lastActivityDate;
        }

        public void setLastActivityDate(OffsetDateTime lastActivityDate) {
            this.lastActivityDate = lastActivityDate;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }

    public static StackOverflowDTO fromJson(String json) throws IOException {
        ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
        return mapper.readValue(json, StackOverflowDTO.class);
    }

    public long getId() {
        return items.getFirst().id;
    }

    public void setId(long id) {
        this.items.getFirst().setId(id);
    }

    public OffsetDateTime getLastActivityDate() {
        return items.getFirst().lastActivityDate;
    }
    public void setLastActivityDate(OffsetDateTime lastActivityDate) {
        this.items.getFirst().setLastActivityDate(lastActivityDate);
    }

    public String getLink() {
        return items.getFirst().link;
    }
    public void setLink(String link) {
        this.items.getFirst().setLink(link);
    }
}
