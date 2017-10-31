import {User} from "../user/user";
import {Comment} from "../comment/comment";

export class Post {
    id: number;
    author: User = new User();
    receiver: User = new User();
    text: string;
    createdDate: any;
    comments: Comment[] = [];
    commentsNextPage: number;

    public isAuthor(user: User): boolean {
        return this.author.isEqualsTo(user);
    }
}
