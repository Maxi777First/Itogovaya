package com.example.springsecurityapplication.repositories;

import com.example.springsecurityapplication.models.Order;
import com.example.springsecurityapplication.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByPerson(Person person);

    @Query(value = "select * from orders;", nativeQuery = true)
    List<Order> findAll(Person person);

    List<Order> findByNumberContainingIgnoreCase(String number);

    @Transactional
    @Modifying
    @Query(value = "UPDATE orders SET status = ?2  WHERE id = ?1", nativeQuery = true)
    void EditOrder(int ids, int status);
}
