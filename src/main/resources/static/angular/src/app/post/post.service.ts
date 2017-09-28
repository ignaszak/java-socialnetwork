import {PostServiceInterface} from "./post.service.interface";
import {Post} from "./post";
import {Http, Response} from "@angular/http";
import {Inject, Injectable} from "@angular/core";
import {User} from "../user/user";
import {Comment} from "./comment";
import {RestProviderInterface} from "../rest/rest-provider.interface";
import {RestProvider} from "../rest/rest-provider";
import {RestResponse} from "../rest/rest-response";

@Injectable()
export class PostService implements PostServiceInterface {

    constructor(
        private http: Http,
        @Inject('RestProviderInterface') private provider: RestProviderInterface
    ) {}

    getPostsByCurrentUser(page: number = 0): Promise<Post[]> {
        let path = this.provider.getPath(RestProvider.GET_POSTS_BY_CURRENT_USER_URL, {'page': page});
        return this.http.get(path)
            .toPromise()
            .then(response => response.json()._embedded.posts as Post[])
            .catch(this.handleError);
    }

    getPostsByUsername(username: string, page: number = 0): Promise<RestResponse> {
        let path: string = this.provider.getPath(RestProvider.GET_POSTS_BY_USER_URL, {
            'username': username,
            'page': page
        });
        return this.http.get(path)
            .toPromise()
            .then(this.getRestResponse)
            .catch(this.handleError);
    }

    getPostsByUser(user: User, page: number = 0): Promise<RestResponse> {
        return this.getPostsByUsername(user.username, page);
    }

    getCommentsByPost(post: Post, page: number = 0): Promise<RestResponse> {
        let path: string = this.provider.getPath(RestProvider.GET_COMMENTS_BY_POST_URL, {
            'postId': post.id,
            'page': page
        });
        return this.http.get(path)
            .toPromise()
            .then(this.getRestResponse)
            .catch(this.handleError);
    }

    private getRestResponse(res: Response): RestResponse {
        return new RestResponse(res);
    }

    addPost(post: Post): Promise<any> {
        post.createdDate = new Date();
        return this.http.put(RestProvider.POST_URL, post)
            .toPromise()
            .then(result => result.json() as Post)
            .catch(this.handleError);
    }

    addComment(comment: Comment, postId: number): Promise<any> {
        comment.createdDate = new Date();
        let path = this.provider.getPath(RestProvider.COMMENT_URL, {'postId': postId});
        return this.http.put(path, comment)
            .toPromise()
            .then(result => result.json())
            .catch(this.handleError);
    }

    private handleError(error: any): Promise<any> {
        console.error('An error occurred', error);
        return Promise.reject(error.message || error);
    }
}
