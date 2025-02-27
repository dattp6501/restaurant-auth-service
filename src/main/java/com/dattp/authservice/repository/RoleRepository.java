package com.dattp.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dattp.authservice.entity.Role;

import java.util.List;
import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role,Long>{
    public Optional<Role> findById(Long id);


    @Query(
        value = "SELECT r.id AS id, r.name AS name, r.is_enable AS is_enable, r.create_at AS create_at, r.update_at AS update_at "
        +"FROM auth.users u "
        +"INNER JOIN auth.role_user ru ON u.id=ru.user_id "
        +"INNER JOIN auth.role r ON r.id=ru.role_id "
        +"WHERE u.username = :username ",
        nativeQuery = true
    )
    public List<Role> getRoles(@Param("username") String username);

    public Optional<Role> findByName(String name);

    public List<Role> findRolesByNameIn(List<String> names);
}
