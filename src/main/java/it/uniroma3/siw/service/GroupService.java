package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Group;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public Group getGroupsByUser(User user) {
        return groupRepository.findByUsers(user);
    }

    @Transactional
    public void save(Group group) {
        groupRepository.save(group);
    }

    @Transactional
    public Group getGroup(Long id) {
        Optional<Group> result = groupRepository.findById(id);
        return result.orElse(null);
    }
    @Transactional
    public Group getGroupByName(String name) {
        return groupRepository.findByName(name);
    }

    @Transactional
    public void deleteById(Long id) {
        groupRepository.deleteById(id);
    }

    @Transactional
    public boolean alreadyExists(Group group) {
        return groupRepository.existsByUsers(group.getUsers());
    }

    @Transactional
    public List<Group> getAllGroups() {
        List<Group> groups = new ArrayList<>();
        Iterable<Group> iterable = groupRepository.findAll();
        iterable.forEach(groups::add);
        return groups;
    }
}

