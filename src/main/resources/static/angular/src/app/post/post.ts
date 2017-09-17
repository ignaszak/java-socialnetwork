import {Comment} from "./comment";
import {User} from "../user/user";

export class Post {
    id: number;
    user: User;
    text: string;
    createdDate: any;
    comments: Comment[];
}
