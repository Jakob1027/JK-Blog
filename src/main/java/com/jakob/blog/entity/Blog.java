package com.jakob.blog.entity;

import com.github.rjeschke.txtmark.Processor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

/**
 * @author Jakob
 */
@Entity
public class Blog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //自增长策略
    private Long id;

    @NotEmpty(message = "标题不能为空")
    @Size(min = 2, max = 50)
    @Column(nullable = false, length = 50)
    private String title;

    @NotEmpty(message = "摘要不能为空")
    @Size(min = 2, max = 300)
    @Column(nullable = false)
    private String summary;

    @Lob  // 大对象，映射 MySQL 的 Long Text 类型
    @Basic(fetch = FetchType.LAZY) // 懒加载
    @NotEmpty(message = "内容不能为空")
    @Size(min = 2)
    @Column(nullable = false) // 映射为字段，值不能为空
    private String content;

    @Lob  // 大对象，映射 MySQL 的 Long Text 类型
    @Basic(fetch = FetchType.LAZY) // 懒加载
    @NotEmpty(message = "内容不能为空")
    @Size(min = 2)
    @Column(nullable = false) // 映射为字段，值不能为空
    private String htmlContent; // 将 md 转为 html

    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    @org.hibernate.annotations.CreationTimestamp    //由数据库自动创建时间
    private Timestamp createTime;

    @Column(name = "readSize")
    private Integer readSize = 0; // 访问量、阅读量

    @Column(name = "commentSize")
    private Integer commentSize = 0;  // 评论量

    @Column(name = "voteSize")
    private Integer voteSize = 0;  // 点赞量

    @Column(name = "tags", length = 100)
    private String tags;  // 标签

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "blog_comment", joinColumns = @JoinColumn(name = "blog_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id", referencedColumnName = "id"))
    private List<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "blog_vote", joinColumns = @JoinColumn(name = "blog_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "vote_id", referencedColumnName = "id"))
    private List<Vote> votes;

    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name="catalog_id")
    private Catalog catalog;

    protected Blog() {
    }

    public Blog(String title, String summary, String content) {
        this.title = title;
        this.summary = summary;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        //将Markdown内容转为html格式
        this.htmlContent = Processor.process(content);
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getReadSize() {
        return readSize;
    }

    public void setReadSize(Integer readSize) {
        this.readSize = readSize;
    }

    public Integer getCommentSize() {
        return commentSize;
    }

    public void setCommentSize(Integer commentSize) {
        this.commentSize = commentSize;
    }

    public Integer getVoteSize() {
        return voteSize;
    }

    public void setVoteSize(Integer voteSize) {
        this.voteSize = voteSize;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
        this.commentSize = this.comments.size();
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
        this.voteSize = this.votes.size();
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    /**
     * 添加评论
     *
     * @param comment
     */
    public void addComment(Comment comment) {
        this.comments.add(comment);
        this.commentSize = this.comments.size();
    }

    /**
     * 删除评论
     *
     * @param commentId
     */
    public void deleteComment(Long commentId) {
        for (Iterator<Comment> i = this.comments.iterator(); i.hasNext(); ) {
            Comment comment = i.next();
            if (comment.getId() == commentId) {
                i.remove();
                break;
            }
        }
        this.commentSize = this.comments.size();
    }

    /**
     * 点赞
     *
     * @param vote
     */
    public boolean addVote(Vote vote) {
        boolean isExist = false;
        for (Vote originVote : votes) {
            if (originVote.getUser().getId() == vote.getUser().getId()) {
                isExist = true;
                break;
            }
        }
        if (!isExist) {
            this.votes.add(vote);
            this.voteSize = this.votes.size();
        }
        return isExist;
    }

    /**
     * 取消点赞
     *
     * @param voteId
     */
    public void deleteVote(Long voteId) {
        for (Iterator<Vote> i = this.votes.iterator(); i.hasNext(); ) {
            Vote vote = i.next();
            if (vote.getId() == voteId) {
                i.remove();
                break;
            }
        }
        this.voteSize = this.votes.size();
    }
}
