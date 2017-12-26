import {CommentServiceInterface} from "./comment.service.interface";
import {Inject, Injectable} from "@angular/core";
import {Post} from "../post/post";
import {RestResponse} from "../rest/rest-response";
import {Http} from "@angular/http";
import {RestProviderInterface} from "../rest/rest-provider.interface";
import {RestProvider} from "../rest/rest-provider";
import {Comment} from "./comment";

@Injectable()
export class CommentService implements CommentServiceInterface {

    constructor(
        private http: Http,
        @Inject('RestProviderInterface') private provider: RestProviderInterface
    ) {}

    getCommentsByPost(post: Post, page?: number): Promise<RestResponse<Comment>> {
        let path: string = this.provider.getPath(RestProvider.POST_COMMENTS_PAGEABLE, {
            'postId': post.id,
            'page': page
        });
        return this.http.get(path)
            .toPromise()
            .then(RestProvider.getRestResponse)
            .catch(RestProvider.handleError);
    }

    addComment(comment: Comment, postId: number): Promise<Comment> {
        comment.createdDate = new Date();
        let path = this.provider.getPath(RestProvider.POST_COMMENTS, {'postId': postId});
        return this.http.put(path, comment)
            .toPromise()
            .then(result => result.json() as Comment)
            .catch(RestProvider.handleError);
    }

    deleteComment(comment: Comment): Promise<any> {
        let path = this.provider.getPath(RestProvider.COMMENT_URL, {'commentId': comment.id});
        return this.http.delete(path).toPromise().catch(RestProvider.handleError);
    }
}
