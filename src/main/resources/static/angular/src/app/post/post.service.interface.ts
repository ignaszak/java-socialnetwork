import {Post} from "./post";
import {User} from "../user/user";
import {Comment} from "./comment";

export interface PostServiceInterface {

    getPostsByCurrentUser(): Promise<Post[]>;
    getPostsByUsername(username: string): Promise<Post[]>;
    getPostsByUser(user: User): Promise<Post[]>;
    getCommentsByPost(post: Post): Promise<Comment[]>;
    addPost(post: Post);
    comment(comment: Comment);
}
