import {Post} from "./post";
import {User} from "../user/user";
import {RestResponse} from "../rest/rest-response";

export interface PostServiceInterface {

    getFeed(page?: number): Promise<RestResponse<Post>>;
    getPostsByUser(user: User, page?: number): Promise<RestResponse<Post>>;
    addPost(post: Post): Promise<Post>;
    deletePost(post: Post): Promise<any>;
    getMediasKey(): string;
    getMediasUrl(key: string): string;
}
