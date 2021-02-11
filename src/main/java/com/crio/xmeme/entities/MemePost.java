package com.crio.xmeme.entities;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "MemePost")
@Table(name = "meme_post")
public class MemePost {
    @Id
    @SequenceGenerator(
            name = "meme_post_sequence_id_generator",
            sequenceName = "meme_post_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "meme_post_sequence_id_generator"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "poster_name",
            updatable = false,
            nullable = false
    )
    private String posterName;

    @Column(
            name = "image_url",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String imageUrl;

    @Column(
            name = "caption",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String caption;

    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private Date createdAt;

    public MemePost(String posterName, String imageUrl, String caption, Date createdAt) {
        this.posterName = posterName;
        this.imageUrl = imageUrl;
        this.caption = caption;
        this.createdAt = createdAt;
    }

    public MemePost() {}

    public Long getId() {
        return id;
    }

    public String getPosterName() {
        return posterName;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "MemePost{" +
                "id=" + id +
                ", posterName='" + posterName + '\'' +
                ", caption='" + caption + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
