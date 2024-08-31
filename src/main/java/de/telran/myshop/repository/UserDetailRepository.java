package de.telran.myshop.repository;

import de.telran.myshop.entity.UserDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailRepository extends CrudRepository<UserDetail, Long> {
}
