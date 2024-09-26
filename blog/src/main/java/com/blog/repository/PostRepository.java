package com.blog.repository;

import com.blog.entity.Posts;
import org.hibernate.dialect.aggregate.PostgreSQLAggregateSupport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Posts, Long> {
}
