import {PostServiceInterface} from "./post.service.interface";
import {Post} from "./post";
import {Http} from "@angular/http";
import {Injectable} from "@angular/core";
import {User} from "../user/user";
import {Comment} from "./comment";

@Injectable()
export class PostService implements PostServiceInterface {

    private static readonly POST_URL = '/rest-api/post';
    private static readonly COMMENT_URL = '/rest-api/comment';
    private static readonly GET_POSTS_BY_CURRENT_USER_URL = '/rest-api/posts/search/findAllByCurrentUser';
    private static readonly GET_POSTS_BY_USER_URL = '/rest-api/posts/search/findAllByUser_Username?username=';
    private static readonly GET_COMMENTS_BY_POST_URL = '/rest-api/posts/{postId}/comments';

    constructor(private http: Http) {}

    getPostsByCurrentUser(): Promise<Post[]> {
        return this.http.get(PostService.GET_POSTS_BY_CURRENT_USER_URL)
            .toPromise()
            .then(response => response.json()._embedded.posts as Post[])
            .catch(this.handleError);
    }

    getPostsByUsername(username: string): Promise<Post[]> {
        return this.http.get(PostService.GET_POSTS_BY_USER_URL + username)
            .toPromise()
            .then(response => response.json()._embedded.posts as Post[])
            .catch(this.handleError);
    }

    getPostsByUser(user: User): Promise<Post[]> {
        return this.getPostsByUsername(user.username);
    }

    getCommentsByPost(post: Post): Promise<Comment[]> {
        return this.http.get(PostService.GET_COMMENTS_BY_POST_URL.replace('{postId}', post.id.toString()))
            .toPromise()
            .then(response => response.json()._embedded.comments as Comment[])
            .catch(this.handleError);
    }

    addPost(post: Post) {
        post.createdDate = new Date();
        this.http.put(PostService.POST_URL, post).subscribe();
    }

    comment(comment: Comment) {
        comment.createdDate = new Date();
        this.http.put(PostService.POST_URL, comment).subscribe();
    }

    private handleError(error: any): Promise<any> {
        console.error('An error occurred', error);
        return Promise.reject(error.message || error);
    }
}
