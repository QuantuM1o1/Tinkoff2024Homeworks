package edu.java.repository.jpa;

import edu.java.dto.LinkDTO;
import edu.java.entity.LinkEntity;
import edu.java.entity.UserEntity;
import edu.java.repository.UsersLinksRepository;
import edu.java.repository.jpa.interfaces.JpaLinksRepository;
import edu.java.repository.jpa.interfaces.JpaLinksSitesRepository;
import edu.java.repository.jpa.interfaces.JpaUsersRepository;
import java.util.ArrayList;
import java.util.List;

public class JpaUsersLinksRepositoryImpl implements UsersLinksRepository {
    private JpaUsersRepository jpaUsersRepository;

    private JpaLinksRepository jpaLinksRepository;

    private JpaLinksSitesRepository jpaLinksSitesRepository;

    @Override
    public void addUserLink(long chatId, long linkId) {
        LinkEntity linkEntity = this.jpaLinksRepository.getReferenceById(linkId);
        UserEntity userEntity = this.jpaUsersRepository.getReferenceById(chatId);
        linkEntity.addUser(userEntity);
        this.jpaLinksRepository.saveAndFlush(linkEntity);
        this.jpaUsersRepository.saveAndFlush(userEntity);
    }

    @Override
    public void removeUserLink(long chatId, long linkId) {
        LinkEntity linkEntity = this.jpaLinksRepository.getReferenceById(linkId);
        UserEntity userEntity = this.jpaUsersRepository.getReferenceById(chatId);
        linkEntity.removeUser(userEntity);
        this.jpaLinksRepository.saveAndFlush(linkEntity);
        this.jpaUsersRepository.saveAndFlush(userEntity);
    }

    @Override
    public List<LinkDTO> findAllLinksByUser(long chatId) {
        UserEntity userEntity = this.jpaUsersRepository.getReferenceById(chatId);
        List<LinkEntity> linkEntities = userEntity.getLinks();
        List<LinkDTO> list = new ArrayList<>();
        linkEntities.forEach(linkEntity -> list.add(new LinkDTO(
            linkEntity.getId(),
            linkEntity.getUrl(),
            linkEntity.getUpdatedAt(),
            linkEntity.getLastActivity(),
            linkEntity.getAnswerCount(),
            linkEntity.getCommentCount(),
            this.jpaLinksSitesRepository.getReferenceById(linkEntity.getId()).getDomainName()
        )));

        return list;
    }

    @Override
    public List<Long> findAllUsersByLink(long linkId) {
        LinkEntity linkEntity = this.jpaLinksRepository.getReferenceById(linkId);
        List<UserEntity> userEntities = linkEntity.getUsers();
        List<Long> list = new ArrayList<>();
        userEntities.forEach(userEntity -> list.add(userEntity.getTgChatId()));

        return list;
    }

    @Override
    public List<Long> findUserLink(long chatId, long linkId) {
        LinkEntity linkEntity = this.jpaLinksRepository.getReferenceById(linkId);
        List<Long> list = new ArrayList<>();
        linkEntity.getUsers().forEach(userEntity -> list.add(userEntity.getTgChatId()));

        return list;
    }
}
