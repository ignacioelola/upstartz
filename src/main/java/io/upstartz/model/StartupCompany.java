package io.upstartz.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class StartupCompany {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(length = 50, nullable = false)
    private String source;

    @Column(length = 1000, nullable = false)
    private String sourceUrl;

    @Column(length = 200, nullable = false)
    private String name;

    @Column(length = 1000, nullable = false)
    private String logoUrl;

    @Column(length = 5000)
    private String description;

    @Column(nullable = false)
    private int upvotes;

    @Column(nullable = false)
    private int downvotes;

    public StartupCompany() {
    }

    public StartupCompany(String source, String sourceUrl, String name, String logoUrl, String description) {
        this.source = source;
        this.sourceUrl = sourceUrl;
        this.name = name;
        this.logoUrl = logoUrl;
        this.description = description;
    }

    public void addUpvote() {
        upvotes++;
    }

    public void addDownvote() {
        downvotes++;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
    }
}