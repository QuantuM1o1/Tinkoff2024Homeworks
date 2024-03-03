package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.Objects;

/**
 * dto.RemoveLinkRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-03-02T12:40:08.364259700Z[UTC]")
public class RemoveLinkRequest {

  private URI link;

  public RemoveLinkRequest link(URI link) {
    this.link = link;
    return this;
  }

  /**
   * Get link
   * @return link
  */
  @Valid
  @Schema(name = "link", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("link")
  public URI getLink() {
    return link;
  }

  public void setLink(URI link) {
    this.link = link;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RemoveLinkRequest removeLinkRequest = (RemoveLinkRequest) o;
    return Objects.equals(this.link, removeLinkRequest.link);
  }

  @Override
  public int hashCode() {
    return Objects.hash(link);
  }

  @Override
  public String toString() {
      String sb = "class dto.RemoveLinkRequest {\n"
          + "    link: " + toIndentedString(link) + "\n"
          + "}";
    return sb;
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

