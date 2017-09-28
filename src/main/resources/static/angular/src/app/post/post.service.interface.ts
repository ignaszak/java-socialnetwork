import {Post} from "./post";
import {User} from "../user/user";
import {Comment} from "./comment";
import {RestResponse} from "../rest/rest-response";

export interface PostServiceInterface {

    getPostsByCurrentUser(page?: number): Promise<Post[]>;
    getPostsByUsername(username: string, page?: number): Promise<RestResponse>;
    getPostsByUser(user: User, page?: number): Promise<RestResponse>;
    getCommentsByPost(post: Post, page?: number): Promise<RestResponse>;
    addPost(post: Post): Promise<any>;
    addComment(comment: Comment, postId: number): Promise<any>;
}
