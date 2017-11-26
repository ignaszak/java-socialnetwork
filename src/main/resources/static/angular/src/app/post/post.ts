import {User} from "../user/user";
import {Comment} from "../comment/comment";
import {Media} from "../media/media";

export class Post {
    id: number;
    author: User = new User();
    receiver: User = new User();
    text: string;
    createdDate: any;
    comments: Comment[] = [];
    commentsNextPage: number;
    key: string;
    medias: Media[] = [];

    public isAuthor(user: User): boolean {
        return this.author.isEqualsTo(user);
    }
}
