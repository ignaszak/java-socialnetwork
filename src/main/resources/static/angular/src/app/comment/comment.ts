import {User} from "../user/user";

export class Comment {
    id: number;
    author: User;
    text: string;
    createdDate: any;

    public isAuthor(user: User): boolean {
        return this.author.isEqualsTo(user);
    }
}
