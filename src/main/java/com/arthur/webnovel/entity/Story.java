package com.arthur.webnovel.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import com.arthur.webnovel.code.State;

@Entity
@Table(name = "story")
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_story")
    @SequenceGenerator(name = "seq_story", sequenceName = "story_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "foreword")
    private String foreword;

    @Column(name = "character")
    private String character;

    @Column(name = "tags")
    @Type(type = "com.iropke.common.hibernate.ArrayType")
    private String[] tags;

    @Column(name = "co_author")
    private String coAuthor;

    @Column(name = "cover_image")
    private String coverImage;

    @Column(name = "views",  columnDefinition = "Integer default 0")
    private Integer views;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private State state;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_at", insertable = false, updatable = false)
    private Date createAt;

    @ManyToOne
    @JoinColumn(name="member")
    private Member member;

    @OneToMany
    @Where(clause = "state not in ('deleted')")
    @OrderBy(clause = "id asc")
    @JoinColumn(name = "story")
    private List<Chapter> chapterList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getForeword() {
        return foreword;
    }

    public void setForeword(String foreword) {
        this.foreword = foreword;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getCoAuthor() {
        return coAuthor;
    }

    public void setCoAuthor(String coAuthor) {
        this.coAuthor = coAuthor;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public List<Chapter> getChapterList() {
        return chapterList;
    }

    public void setChapterList(List<Chapter> chapterList) {
        this.chapterList = chapterList;
    }

}
