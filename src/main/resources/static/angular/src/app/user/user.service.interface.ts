import {User} from "./user";

export interface UserServiceInterface {

    getUserByUsername(username: string): Promise<User>;
    getUserByEmail(email: string): Promise<User>;
    getCurrentUser(): Promise<User>;
    updateUser(user: User);
}
