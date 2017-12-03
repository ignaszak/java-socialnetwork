import {Component, HostListener, Inject, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {Post} from "./post";

import {PostServiceInterface} from "./post.service.interface";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {User} from "../user/user";
import {UserServiceInterface} from "../user/user.service.interface";
import {CommentServiceInterface} from "../comment/comment.service.interface";
import {Comment} from "../comment/comment";
import {RestResponse} from "../rest/rest-response";
import {Swal} from "../shared/swal";
import {MediaServiceInterface} from "../media/media.service.interface";
import {FileHolder} from "angular2-image-upload";
import {Media} from "../media/media";

@Component({
    selector:    'post-list',
    templateUrl: 'post.component.html'
})
export class PostComponent implements OnInit, OnChanges {

    @Input() user: User;
    currentUser: User;
    posts: Post[] = null;
    postForm: FormGroup;
    postMediaUrl: string;
    postMediaKey: string;
    showUploader: boolean = false;
    private nextPostsPage: number = 0;
    private hasNextPostPage: boolean = true;
    private uploaderCounter: number = 0;

    constructor(
        @Inject('PostServiceInterface') private postService: PostServiceInterface,
        @Inject('UserServiceInterface') private userService: UserServiceInterface,
        @Inject('CommentServiceInterface') private commentService: CommentServiceInterface,
        @Inject('MediaServiceInterface') public mediaService: MediaServiceInterface
    ) {
        this.postForm = new FormGroup({
            text: new FormControl('', Validators.required)
        });
    }

    ngOnChanges(changes: SimpleChanges): void {
        this.user = changes.user.currentValue;
        this.posts = null;
        this.nextPostsPage = 0;
        this.hasNextPostPage = true;
        this.getPostsByUser(this.user);
    }

    ngOnInit(): void {
        this.getCurrentUser();
        this.initPostMedias();
        if (! this.user) this.getFeed();
    }

    onSubmitPost(): void {
        if (this.postForm.valid || this.areMediasAddedToUploader()) {
            let post: Post = this.postForm.value;
            post.author = this.currentUser;
            post.receiver = this.user ? this.user : this.currentUser;
            post.key = this.postMediaKey;
            this.postService.addPost(post).then(responsePost => {
                post.id = responsePost.id;
                post.medias = responsePost.medias;
                this.prependPost(post);
                this.initPostMedias();
            });
            this.postForm.reset();
        }
    }

    @HostListener('window:scroll', ['$event'])
    onScrollDown(): void {
        if (window.innerHeight + window.scrollY === document.body.scrollHeight) {
            if (this.user) {
                this.getPostsByUser(this.user);
            } else {
                this.getFeed();
            }
        }
    }

    commentPost(id: number, event: any): void {
        let comment: Comment = new Comment();
        let element = event.currentTarget || event.srcElement;
        comment.text = element.value;
        if (comment.text) {
            comment.author = this.currentUser;
            this.commentService.addComment(comment, id).then(commentId => {
                let post: Post = this.posts.find(post => post.id == id);
                comment.id = commentId;
                if (typeof post.comments === "undefined") post.comments = [];
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
                    post.comments = response.getResponse().reverse();
                } else {
                    response.getResponse().forEach(comment => {
                        post.comments.unshift(comment);
                    });
                }
            });
    }

    deletePost(post: Post): void {
        Swal.confirm(() => {
            this.postService.deletePost(post).then(() => {
                let postIndex = this.posts.indexOf(post);
                this.posts.splice(postIndex, 1);
            });
        }, 'Delete post?');
    }

    deleteComment(comment: Comment, post: Post): void {
        Swal.confirm(() => {
            this.commentService.deleteComment(comment).then(() => {
                let commentIndex = post.comments.indexOf(comment);
                let postIndex = this.posts.indexOf(post);
                this.posts[postIndex].comments.splice(commentIndex, 1);
            })
        }, 'Delete comment?');
    }

    toggleUploader(): void {
        if (this.showUploader && this.areMediasAddedToUploader()) {
            Swal.confirm(() => {
                this.showUploader = false;
            }, 'All attached medias will be removed!');
        } else {
            this.showUploader = ! this.showUploader;
        }
    }

    updateUploaderCounter(value: number): void {
        this.uploaderCounter += value;
        if (this.uploaderCounter < 1) this.initPostMedias(true);
    }

    removeMedia(event: FileHolder) {
        this.updateUploaderCounter(-1);
        let media: Media = JSON.parse(event.serverResponse['_body']);
        console.log(media);
        this.mediaService.deleteMedia(media);
    }

    private areMediasAddedToUploader(): boolean {
        return this.uploaderCounter > 0;
    }

    private getFeed(): void {
        if (this.hasNextPostPage)
            this.postService.getFeed(this.nextPostsPage).then(response => this.loadPosts(response));
    }

    private getPostsByUser(user: User): void {
        if (this.hasNextPostPage)
            this.postService.getPostsByUser(user, this.nextPostsPage).then(response => this.loadPosts(response));
    }

    private loadPosts(response: RestResponse<Post>): void {
        this.nextPostsPage = response.getNextPage();
        this.hasNextPostPage = response.hasNextPage();
        let posts: Post[] = response.getResponse();
        if (this.posts === null) {
            this.posts = posts;
        } else {
            for (let post of posts) this.posts.push(post);
        }
        this.loadCommentsToPosts();
    }

    private loadCommentsToPosts(): void {
        this.posts.forEach(post => {
            this.loadMoreComments(post);
        });
    }

    private getCurrentUser(): void {
        this.userService.getCurrentUser().then(user => this.currentUser = user);
    }

    private prependPost(post: Post): void {
        this.posts.unshift(post);
    }

    private initPostMedias(show: boolean = false): void {
        this.postMediaKey = this.postService.getMediasKey();
        this.postMediaUrl = this.postService.getMediasUrl(this.postMediaKey);
        this.showUploader = show;
        this.uploaderCounter = 0;
    }
}
