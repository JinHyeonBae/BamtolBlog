package com.example.back.repository;

import com.example.back.model.post.PostInformation;
import com.example.back.repository.CustomRepository.InsertCustomRepository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostInformationRepository extends JpaRepository<PostInformation, Integer>, InsertCustomRepository {
    
    @Query("select display_level from post_information where user_id =? AND post_id =?")
    public PostInformation findByUserIdAndPostId(int userId, int postId);

    @Query("select * from post_information where post_id = ?")
    public PostInformation findByPostId(int postId);

    public void savePostInformation(PostInformation postInfo);

}
