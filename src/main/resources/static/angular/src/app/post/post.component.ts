import {Component, Inject, Input} from '@angular/core';
import {Post} from "./post";

import {PostServiceInterface} from "./post.service.interface";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {User} from "../user/user";
import {UserServiceInterface} from "../user/user.service.interface";

@Component({
    selector:    'post-list',
    templateUrl: 'post.component.html'
})
export class PostComponent {

    @Input() user: User;
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
        console.log(this.user);
        if (this.user) {
            this.getPostsByUser(this.user);
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

    private getPostsByCurrentUser(): void {
        this.postService.getPostsByCurrentUser().then(posts => this.posts = posts);
    }

    private getPostsByUser(user: User): void {
        this.postService.getPostByUser(user).then(posts => this.posts = posts);
    }

    private getCurrentUser(): void {
        this.userService.getCurrentUser().then(user => this.currentUser = user);
    }

    private prependPost(post: Post) {
        this.posts.unshift(post);
    }
}
