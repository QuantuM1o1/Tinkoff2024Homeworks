package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * dto.LinkUpdateRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-02-28T13:42:52.041659600Z[UTC]")
public class LinkUpdateRequest {

  private Long id;

  private URI url;

  private String description;

  @Valid
  private List<Long> tgChatIds;

  public LinkUpdateRequest id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */

  @Schema(name = "id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LinkUpdateRequest url(URI url) {
    this.url = url;
    return this;
  }

  /**
   * Get url
   * @return url
  */
  @Valid
  @Schema(name = "url", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("url")
  public URI getUrl() {
    return url;
  }

  public void setUrl(URI url) {
    this.url = url;
  }

  public LinkUpdateRequest description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  */

  @Schema(name = "description", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LinkUpdateRequest tgChatIds(List<Long> tgChatIds) {
    this.tgChatIds = tgChatIds;
    return this;
  }

  public LinkUpdateRequest addTgChatIdsItem(Long tgChatIdsItem) {
    if (this.tgChatIds == null) {
      this.tgChatIds = new ArrayList<>();
    }
    this.tgChatIds.add(tgChatIdsItem);
    return this;
  }

  /**
   * Get tgChatIds
   * @return tgChatIds
  */

  @Schema(name = "tgChatIds", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("tgChatIds")
  public List<Long> getTgChatIds() {
    return tgChatIds;
  }

  public void setTgChatIds(List<Long> tgChatIds) {
    this.tgChatIds = tgChatIds;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LinkUpdateRequest linkUpdate = (LinkUpdateRequest) o;
    return Objects.equals(this.id, linkUpdate.id)
        && Objects.equals(this.url, linkUpdate.url)
        && Objects.equals(this.description, linkUpdate.description)
        && Objects.equals(this.tgChatIds, linkUpdate.tgChatIds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, url, description, tgChatIds);
  }

  @Override
  public String toString() {
      return "class dto.LinkUpdateRequest {\n"
          + "    id: " + toIndentedString(id) + "\n"
          + "    url: " + toIndentedString(url) + "\n"
          + "    description: " + toIndentedString(description) + "\n"
          + "    tgChatIds: " + toIndentedString(tgChatIds) + "\n"
          + "}";
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

