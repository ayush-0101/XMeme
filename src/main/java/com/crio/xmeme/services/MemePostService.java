package com.crio.xmeme.services;

import com.crio.xmeme.dtos.MemePostDTO;
import com.crio.xmeme.entities.MemePost;
import com.crio.xmeme.exceptions.BadRequestException;
import com.crio.xmeme.exceptions.ResourceAlreadyExistsException;
import com.crio.xmeme.exceptions.ResourceNotFoundException;
import com.crio.xmeme.repositories.MemePostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MemePostService {
    private final MemePostRepository memePostRepo;

    @Autowired
    public MemePostService(MemePostRepository memePostRepo) {
        this.memePostRepo = memePostRepo;
    }

    private MemePost getMemePostByIdFromDb(Long id) {
        Optional<MemePost> post = memePostRepo.findById(id);
        if (!post.isPresent()) {
            throw new ResourceNotFoundException();
        }
        return post.get();
    }

    private void checkForBadRequest(Map<String, String> payload, Set<String> allowedFields) {
        if (payload.isEmpty()) {
            throw new BadRequestException();
        }
        payload.forEach((key, value) -> {
            if (!allowedFields.contains(key) || value.equals("")) {
                throw new BadRequestException();
            }
        });
    }

    public List<MemePostDTO> getAllMemePosts() {
        List<MemePostDTO> posts = new ArrayList<>();
        memePostRepo.findAll(PageRequest.of(0, 100, Sort.by(Sort.Direction.DESC, "createdAt"))).forEach(post -> {
            posts.add(new MemePostDTO(
                    String.valueOf(post.getId()),
                    post.getPosterName(),
                    post.getImageUrl(),
                    post.getCaption()
            ));
        });
        return posts;
    }

    public MemePostDTO getMemePostById(Long id) {
        MemePost post = getMemePostByIdFromDb(id);
        return new MemePostDTO(
                String.valueOf(post.getId()),
                post.getPosterName(),
                post.getImageUrl(),
                post.getCaption()
        );
    }

    public Map<String, String> addMemePost(Map<String, String> payload) {
        Set<String> allowedFields = new HashSet<>(Arrays.asList("url", "name", "caption"));
        checkForBadRequest(payload, allowedFields);
        if (payload.size() != 3) {
            throw new BadRequestException();
        }
        memePostRepo.findAll().forEach(post -> {
            if (post.getPosterName().equals(payload.get("name"))
                    && post.getCaption().equals(payload.get("caption"))
                    && post.getImageUrl().equals(payload.get("url"))) {
                throw new ResourceAlreadyExistsException();
            }
        });
        Long savedPostId = memePostRepo.save(new MemePost(
                payload.get("name"),
                payload.get("url"),
                payload.get("caption"),
                new Date()
        )).getId();
        return Collections.singletonMap("id", String.valueOf(savedPostId));
    }

    public void updateMemePost(Map<String, String> payload, Long id) {
        MemePost post = getMemePostByIdFromDb(id);
        Set<String> allowedFields = new HashSet<>(Arrays.asList("url", "caption"));
        checkForBadRequest(payload, allowedFields);
        if (payload.containsKey("url")) {
            post.setImageUrl(payload.get("url"));
        }
        if (payload.containsKey("caption")) {
            post.setCaption(payload.get("caption"));
        }
        memePostRepo.findAll().forEach(memePost -> {
            if (!memePost.getId().equals(id)
                    && memePost.getPosterName().equals(post.getPosterName())
                    && memePost.getCaption().equals(post.getCaption())
                    && memePost.getImageUrl().equals(post.getImageUrl())) {
                throw new ResourceAlreadyExistsException();
            }
        });
        memePostRepo.save(post);
    }

    public void deleteMemePost(Long id) {
        if (!memePostRepo.findById(id).isPresent()) {
            throw new ResourceNotFoundException();
        }
        memePostRepo.deleteById(id);
    }
}
