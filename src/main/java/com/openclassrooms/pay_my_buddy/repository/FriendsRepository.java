package com.openclassrooms.pay_my_buddy.repository;

import com.openclassrooms.pay_my_buddy.model.Friends;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface FriendsRepository extends CrudRepository<Friends, Integer> {
  @Modifying //(clearAutomatically = true)
  @Transactional
  @Query(
    value = "DELETE FROM friends WHERE users_id_users = :idUser AND buddy = :idBuddy",
    nativeQuery = true
  )
  void qdeleteBuddy(
    @Param("idUser") Integer idUser,
    @Param("idBuddy") Integer idBuddy
  );

  @Modifying //(clearAutomatically = true)
  @Transactional
  @Query(
    value = "DELETE FROM friends WHERE users_id_users = :idUser",
    nativeQuery = true
  )
  void qdeleteBuddyidUser(@Param("idUser") Integer idUser);

  @Modifying //(clearAutomatically = true)
  @Transactional
  @Query(
    value = "DELETE FROM friends WHERE buddy = :idBuddy",
    nativeQuery = true
  )
  void qdeleteBuddyidBuddy(@Param("idBuddy") Integer idBuddy);

  @Modifying
  @Transactional
  @Query(
    value = "INSERT INTO friends (users_id_users, buddy) values (:idUser, :idBuddy)",
    nativeQuery = true
  )
  void qinsertBuddy(
    @Param("idUser") Integer idUser,
    @Param("idBuddy") Integer idBuddy
  );

  @Query(
    value = "SELECT * FROM friends WHERE users_id_users = :idUser AND buddy = :idBuddy",
    nativeQuery = true
  )
  Friends qselectBuddy(
    @Param("idUser") Integer idUser,
    @Param("idBuddy") Integer idBuddy
  );

  @Query(
    value = "SELECT * FROM friends WHERE users_id_users = :idUser",
    nativeQuery = true
  )
  Friends qselectBuddyidUser(@Param("idUser") Integer idUser);

  @Query(
    value = "SELECT * FROM friends WHERE buddy = :idBuddy",
    nativeQuery = true
  )
  Friends qselectBuddyidBuddy(@Param("idBuddy") Integer idBuddy);
}
