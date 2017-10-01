import {User} from "../user/user";
import {Comment} from "../comment/comment";

export class Post {
    id: number;
    user: User;
    text: string;
    createdDate: any;
    comments: Comment[];
    commentsNextPage: number;

    public isAuthor(user: User): boolean {
        return this.user.isEqualsTo(user);
    }
}
