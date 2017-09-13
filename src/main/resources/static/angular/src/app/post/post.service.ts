import {PostServiceInterface} from "./post.service.interface";
import {Post} from "./post";
import {Http} from "@angular/http";
import {Injectable} from "@angular/core";
import {User} from "../user/user";

@Injectable()
export class PostService implements PostServiceInterface {

    private static readonly GET_POSTS_URL = '/rest-api/post';
    private static readonly GET_POSTS_BY_CURRENT_USER_URL = '/rest-api/posts/search/findAllByCurrentUser';
    private static readonly GET_POSTS_BY_USER_URL = '/rest-api/posts/search/findAllByUser_Username?username=';

    constructor(private http: Http) {}

    getPostsByCurrentUser(): Promise<Post[]> {
        return this.http.get(PostService.GET_POSTS_BY_CURRENT_USER_URL)
            .toPromise()
            .then(response => response.json()._embedded.posts as Post[])
            .catch(this.handleError);
    }

    getPostByUser(user: User): Promise<Post[]> {
        return this.http.get(PostService.GET_POSTS_BY_USER_URL + user.username)
            .toPromise()
            .then(response => response.json()._embedded.posts as Post[])
            .catch(this.handleError);
    }

    addPost(post: Post) {
        post.createdDate = new Date();
        this.http.put(PostService.GET_POSTS_URL, post).subscribe();
    }

    private handleError(error: any): Promise<any> {
        console.error('An error occurred', error);
        return Promise.reject(error.message || error);
    }
}
