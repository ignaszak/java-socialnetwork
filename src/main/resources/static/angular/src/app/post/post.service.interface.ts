import {Post} from "./post";
import {User} from "../user/user";

export interface PostServiceInterface {

    getPostsByCurrentUser(): Promise<Post[]>;
    getPostByUser(user: User): Promise<Post[]>;
    addPost(post: Post);
}
