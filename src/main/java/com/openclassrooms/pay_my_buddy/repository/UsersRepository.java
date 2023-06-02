package com.openclassrooms.pay_my_buddy.repository;

import com.openclassrooms.pay_my_buddy.model.Users;
import java.util.Date;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UsersRepository extends CrudRepository<Users, Integer> {
  @Modifying //(clearAutomatically = true)
  @Transactional
  @Query(
    value = "DELETE FROM users WHERE id_users = :idUser",
    nativeQuery = true
  )
  void qdeleteUserById(@Param("idUser") Integer idUser);

  @Modifying //(clearAutomatically = true)
  @Transactional
  @Query(
    value = "DELETE FROM users WHERE id_email = :idMail",
    nativeQuery = true
  )
  void qdeleteUserByIdMail(@Param("idMail") String idMail);

  @Modifying
  @Transactional
  @Query(
    value = "INSERT INTO users (id_email, name_user, first_name, birth_date, password, role_id) values (:idMail, :nameUser, :firstName, :birthDate, :password, :roleId)",
    nativeQuery = true
  )
  void qinsertUser(
    @Param("idMail") String idMail,
    @Param("nameUser") String nameUser,
    @Param("firstName") String firstName,
    @Param("birthDate") Date birthDate,
    @Param("password") String password,
    @Param("roleId") Integer roleId
  );

  @Query(
    value = "SELECT * FROM users WHERE id_users = :idUser",
    nativeQuery = true
  )
  Users qselectUserById(@Param("idUser") Integer idUser);

  @Query(
    value = "SELECT * FROM users WHERE id_email = :idMail",
    nativeQuery = true
  )
  Users qselectUserByIdMail(@Param("idMail") String idMail);
}
