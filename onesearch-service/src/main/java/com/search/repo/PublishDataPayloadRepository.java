package com.search.repo;

import com.search.entity.PublishDataPayload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PublishDataPayloadRepository extends JpaRepository<PublishDataPayload, String> {
    @Query("select p from PublishDataPayload p where lower(p.content) like lower(concat('%', :query,'%'))")
    public List<PublishDataPayload> findByNameLikeUsingNamedParameter(@Param("query") String query);
}