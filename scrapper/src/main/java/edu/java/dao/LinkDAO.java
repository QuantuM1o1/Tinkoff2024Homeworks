package edu.java.dao;

import edu.java.dto.LinkDTO;
import java.util.List;

public interface LinkDAO {
    void addLink(String url);
    void removeLink(String url);
    List<LinkDTO> findAllLinks();
}
