package com.crio.xmeme.services;

import com.crio.xmeme.dtos.MemePostDTO;
import com.crio.xmeme.entities.MemePost;
import com.crio.xmeme.exceptions.NullFieldException;
import com.crio.xmeme.exceptions.ResourceAlreadyExistsException;
import com.crio.xmeme.exceptions.ResourceNotFoundException;
import com.crio.xmeme.repositories.MemePostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MemePostService {
    private final MemePostRepository memePostRepo;

    @Autowired
    public MemePostService(MemePostRepository memePostRepo) {
        this.memePostRepo = memePostRepo;
    }

    public List<MemePostDTO> getAllMemePosts() {
        List<MemePostDTO> posts = new ArrayList<>();
        memePostRepo.findAll(PageRequest.of(0, 100, Sort.by(Sort.Direction.DESC, "createdAt")))
                .forEach(post -> {
            MemePostDTO memePostDTO = new MemePostDTO();
            memePostDTO.setId(String.valueOf(post.getId()));
            memePostDTO.setName(post.getPosterName());
            memePostDTO.setUrl(post.getImageUrl());
            memePostDTO.setCaption(post.getCaption());
            posts.add(memePostDTO);
        });
        return posts;
    }

    public MemePostDTO getMemePostById(Long id) {
        Optional<MemePost> post = memePostRepo.findById(id);
        if (!post.isPresent()) {
            throw new ResourceNotFoundException("Post with the given id doesn't exist");
        }
        MemePostDTO memePostDTO = new MemePostDTO();
        memePostDTO.setId(String.valueOf(post.get().getId()));
        memePostDTO.setName(post.get().getPosterName());
        memePostDTO.setUrl(post.get().getImageUrl());
        memePostDTO.setCaption(post.get().getCaption());
        return memePostDTO;
    }

    public Long addMemePost(MemePostDTO memePostDTO) {
        if (memePostDTO.getCaption() != null && memePostDTO.getUrl() != null && memePostDTO.getName() != null) {
            memePostRepo.findAll().forEach(post -> {
                if (post.getPosterName().equals(memePostDTO.getName())
                        && post.getCaption().equals(memePostDTO.getCaption())
                        && post.getImageUrl().equals(memePostDTO.getUrl())) {
                    throw new ResourceAlreadyExistsException("Post already exists");
                }
            });
            MemePost newMemePost = new MemePost(memePostDTO.getName(), memePostDTO.getUrl(), memePostDTO.getCaption(),
                    new Date());
            return memePostRepo.save(newMemePost).getId();
        } else {
            throw new NullFieldException("Field(s) cannot be empty");
        }
    }
}
