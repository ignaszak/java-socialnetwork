import {Comment} from "./comment";
import {Post} from "../post/post";
import {RestResponse} from "../rest/rest-response";

export interface CommentServiceInterface {

    getCommentsByPost(post: Post, page?: number): Promise<RestResponse<Comment>>;
    addComment(comment: Comment, postId: number): Promise<any>;
    deleteComment(comment: Comment): Promise<any>;
}
