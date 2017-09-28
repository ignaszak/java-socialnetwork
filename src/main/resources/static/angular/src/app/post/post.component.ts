import {Component, Inject, Input, OnInit} from '@angular/core';
import {Post} from "./post";

import {PostServiceInterface} from "./post.service.interface";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {User} from "../user/user";
import {UserServiceInterface} from "../user/user.service.interface";
import {Comment} from "./comment";

@Component({
    selector:    'post-list',
    templateUrl: 'post.component.html'
})
export class PostComponent implements OnInit {

    @Input() username: string;
    private posts: Post[];
    private postForm: FormGroup;
    currentUser: User;

    constructor(
        @Inject('PostServiceInterface') private postService: PostServiceInterface,
        @Inject('UserServiceInterface') private userService: UserServiceInterface
    ) {
        this.posts = [];
        this.postForm = new FormGroup({
            text: new FormControl('', Validators.required)
        });
    }

    ngOnInit(): void {
        this.getCurrentUser();
        if (this.username) {
            this.getPostsByUsername(this.username);
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
            this.postService.addComment(comment, id).then(commentId => {
                let post: Post = this.posts.find(post => post.id == id);
                comment.id = commentId;
                post.comments.push(comment);
            });
            element.value = '';
        }
    }

    loadMoreComments(post: Post): void {
        this.postService.getCommentsByPost(post, post.commentsNextPage)
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

    deleteComment(comment: Comment): void {
        console.log(comment);
    }

    private getPostsByCurrentUser(): void {
        this.postService.getPostsByCurrentUser().then(posts => {
            console.log(posts);
            this.posts = posts;
            this.loadCommentsToPosts();
        });
    }

    private getPostsByUsername(username: string): void {
        this.postService.getPostsByUsername(username).then(response => {
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
