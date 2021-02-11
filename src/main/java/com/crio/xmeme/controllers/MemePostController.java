package com.crio.xmeme.controllers;

import com.crio.xmeme.dtos.MemePostDTO;
import com.crio.xmeme.services.MemePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/memes")
public class MemePostController {
    private final MemePostService memePostService;

    @Autowired
    public MemePostController(MemePostService memePostService) {
        this.memePostService = memePostService;
    }

    @GetMapping
    public List<MemePostDTO> getAllMemePosts() {
        return memePostService.getAllMemePosts();
    }

    @GetMapping("/{id}")
    public MemePostDTO getMemePostById(@PathVariable("id") Long id) {
        return memePostService.getMemePostById(id);
    }

    @PostMapping
    public ResponseEntity<?> addMemePost(@RequestBody MemePostDTO memePostDTO) {
        Long savedPostId = memePostService.addMemePost(memePostDTO);
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("id", String.valueOf(savedPostId));
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }
}
