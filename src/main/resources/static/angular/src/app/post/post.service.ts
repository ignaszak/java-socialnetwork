import {PostServiceInterface} from "./post.service.interface";
import {Post} from "./post";
import {Http} from "@angular/http";
import {Inject, Injectable} from "@angular/core";
import {User} from "../user/user";
import {RestProviderInterface} from "../rest/rest-provider.interface";
import {RestProvider} from "../rest/rest-provider";
import {RestResponse} from "../rest/rest-response";

@Injectable()
export class PostService implements PostServiceInterface {

    constructor(
        private http: Http,
        @Inject('RestProviderInterface') private provider: RestProviderInterface
    ) {}

    getFeed(page: number = 0): Promise<RestResponse<Post>> {
        let path = this.provider.getPath(RestProvider.FEED_PAGEABLE, {'page': page});
        return this.http.get(path)
            .toPromise()
            .then(RestProvider.getRestResponse)
            .catch(RestProvider.handleError);
    }

    getPostsByUser(user: User, page: number = 0): Promise<RestResponse<Post>> {
        let path: string = this.provider.getPath(RestProvider.USER_POSTS_PAGEABLE, {
            'userId': user.id,
            'page':   page
        });
        return this.http.get(path)
            .toPromise()
            .then(RestProvider.getRestResponse)
            .catch(RestProvider.handleError);
    }

    addPost(post: Post): Promise<any> {
        post.createdDate = new Date();
        return this.http.put(RestProvider.POSTS, post)
            .toPromise()
            .then(result => result.json() as Post)
            .catch(RestProvider.handleError);
    }

    deletePost(post: Post): Promise<any> {
        let path: string = this.provider.getPath(RestProvider.POST, {'postId': post.id});
        return this.http.delete(path).toPromise().catch(RestProvider.handleError);
    }
}
