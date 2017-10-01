/**
 * Created by tomek on 11.06.17.
 */
export class User {
    id: number;
    email: string;
    username: string;
    password: string;
    role: string;
    caption: string;
    status: string;

    public isEqualsTo(user: User): boolean {
        return this.username == user.username;
    }
}
