import {Component, Inject, Input} from '@angular/core';
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
export class PostComponent {

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
            this.postService.addPost(post);
            this.prependPost(post);
            this.postForm.reset();
        }
    }

    commentPost(id: number): void {
        let comment: Comment = new Comment();
        comment.post = this.posts.find(post => post.id == id);
        comment.user = this.currentUser;
        this.postService.comment(comment);
    }

    private getPostsByCurrentUser(): void {
        this.postService.getPostsByCurrentUser().then(posts => {
            this.posts = posts;
            this.loadCommentsToPosts();
        });
    }

    private getPostsByUsername(username: string): void {
        this.postService.getPostsByUsername(username).then(posts => {
            this.posts = posts;
            this.loadCommentsToPosts();
        });
    }

    private loadCommentsToPosts(): void {
        let count = this.posts.length;
        for (let i = 0; i < count; ++ i)
            this.postService.getCommentsByPost(this.posts[i])
                .then(comments => this.posts[i].comments = comments);
    }

    private getCurrentUser(): void {
        this.userService.getCurrentUser().then(user => this.currentUser = user);
    }

    private prependPost(post: Post) {
        this.posts.unshift(post);
    }
}
