import {Component, Inject, Input, OnInit} from '@angular/core';
import {Post} from "./post";

import {PostServiceInterface} from "./post.service.interface";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {User} from "../user/user";
import {UserServiceInterface} from "../user/user.service.interface";
import {CommentServiceInterface} from "../comment/comment.service.interface";
import {Comment} from "../comment/comment";

@Component({
    selector:    'post-list',
    templateUrl: 'post.component.html'
})
export class PostComponent implements OnInit {

    @Input() username: string;
    private posts: Post[];
    private postForm: FormGroup;
    currentUser: User;
    user: User;

    constructor(
        @Inject('PostServiceInterface') private postService: PostServiceInterface,
        @Inject('UserServiceInterface') private userService: UserServiceInterface,
        @Inject('CommentServiceInterface') private commentService: CommentServiceInterface
    ) {
        this.posts = [];
        this.postForm = new FormGroup({
            text: new FormControl('', Validators.required)
        });
    }

    ngOnInit(): void {
        this.getCurrentUser();
        if (this.username) {
            this.userService.getUserByUsername(this.username).then(user => {
                this.user = user;
                this.getPostsByUser(user);
            });
        } else {
            this.getPostsByCurrentUser();
        }
    }

    onSubmitPost(): void {
        if (this.postForm.valid) {
            let post: Post = this.postForm.value;
            post.user = this.currentUser;
            post.comments = [];
            this.postService.addPost(post).then(postId => {
                post.id = postId;
                this.prependPost(post);
            });
            this.postForm.reset();
        }
    }

    commentPost(id: number, event: any): void {
        let comment: Comment = new Comment();
        let element = event.currentTarget || event.srcElement;
        comment.text = element.value;
        if (comment.text) {
            comment.user = this.currentUser;
            this.commentService.addComment(comment, id).then(commentId => {
                let post: Post = this.posts.find(post => post.id == id);
                comment.id = commentId;
                post.comments.push(comment);
            });
            element.value = '';
        }
    }

    loadMoreComments(post: Post): void {
        this.commentService.getCommentsByPost(post, post.commentsNextPage)
            .then(response => {
                post.commentsNextPage = response.getNextPage();
                if (typeof post.comments === "undefined") {
                    post.comments = response.getComments().reverse();
                } else {
                    response.getComments().reverse().forEach(comment => {
                        post.comments.unshift(comment);
                    });
                }
            });
    }

    deletePost(post: Post): void {
        this.postService.deletePost(post).then(() => {
            let postIndex = this.posts.indexOf(post);
            this.posts.splice(postIndex, 1);
        });
    }

    deleteComment(comment: Comment, post: Post): void {
        this.commentService.deleteComment(comment).then(() => {
            let commentIndex = post.comments.indexOf(comment);
            let postIndex = this.posts.indexOf(post);
            this.posts[postIndex].comments.splice(commentIndex, 1);
        })
    }

    private getPostsByCurrentUser(): void {
        this.postService.getPostsByCurrentUser().then(posts => {
            this.posts = posts;
            this.loadCommentsToPosts();
        });
    }

    private getPostsByUser(user: User): void {
        this.postService.getPostsByUser(user).then(response => {
            this.posts = response.getPosts();
            this.loadCommentsToPosts();
        });
    }

    private loadCommentsToPosts(): void {
        this.posts.forEach(post => {
            this.loadMoreComments(post);
        });
    }

    private getCurrentUser(): void {
        this.userService.getCurrentUser().then(user => this.currentUser = user);
    }

    private prependPost(post: Post) {
        this.posts.unshift(post);
    }
}
