package com.stortor.hw7.repositories;

import com.stortor.hw7.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    public Role findByName(String name);
}
