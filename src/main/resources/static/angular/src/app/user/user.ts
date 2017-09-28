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

    constructor(json?: any) {
        if (json) {
            this.id       = json.id || null;
            this.email    = json.email || '';
            this.username = json.username || '';
            this.password = json.password || '';
            this.role     = json.role || '';
            this.caption  = json.caption || '';
            this.status   = json.status || '';
        }
    }

    public isEqualsTo(user: User): boolean {
        return this.username == user.username;
    }
}
