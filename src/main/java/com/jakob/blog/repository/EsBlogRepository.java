package com.jakob.blog.repository;

import com.jakob.blog.entity.es.EsBlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author Jakob
 */
public interface EsBlogRepository extends ElasticsearchRepository<EsBlog,String> {

    Page<EsBlog> findByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(
            String title,String summary,String content,String tags,Pageable pageable);

    EsBlog findByBlogId(Long blogId);
}
