import {Post} from "./post";
import {User} from "../user/user";

export class Comment {
    id: number;
    post: Post;
    user: User;
    text: string;
    createdDate: any;
}
