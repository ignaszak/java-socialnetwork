import {Post} from "./post";
import {User} from "../user/user";
import {RestResponse} from "../rest/rest-response";

export interface PostServiceInterface {

    getPostsByCurrentUser(page?: number): Promise<RestResponse>;
    getPostsByUser(user: User, page?: number): Promise<RestResponse>;
    addPost(post: Post): Promise<any>;
    deletePost(post: Post): Promise<any>;
}
