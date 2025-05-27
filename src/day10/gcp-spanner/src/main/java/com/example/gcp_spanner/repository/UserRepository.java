package com.example.gcp_spanner.repository;

import com.example.gcp_spanner.entity.Users;
import com.google.cloud.spring.data.spanner.repository.SpannerRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link Users} entities.
 * Provides CRUD operations and query execution on the "users" table in Cloud Spanner.
 * <p>
 * Extends {@link SpannerRepository} to leverage Spring Data Cloud Spanner's support
 * for seamless integration with Google Cloud Spanner.
 * </p>
 */
@Repository
public interface UserRepository extends SpannerRepository<Users, String> {
}
