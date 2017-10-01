import {User} from "../user/user";

export class Comment {
    id: number;
    user: User;
    text: string;
    createdDate: any;

    public isAuthor(user: User): boolean {
        return this.user.isEqualsTo(user);
    }
}
