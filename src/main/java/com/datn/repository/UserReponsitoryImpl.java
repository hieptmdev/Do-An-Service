package com.datn.repository;

import com.datn.entity.User;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


@Transactional
public class UserReponsitoryImpl implements UserRepositoryCurd {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> findByCode(Integer code) {
        String sql = "select u.id,u.username, u.code,u.dob, u.email,u.phoneNumber,u.address,u.isAdminAccount,u.name from User u where 1 = 1";
        StringBuilder sb = new StringBuilder();
        sb.append(sql);
        if (code != null) {
            sb.append(" and u.code = :code");
        }

        Query query = entityManager.createQuery(sb.toString());
        if (code != null) {
            query.setParameter("code", code);
        }
        return query.getResultList();
    }
}
