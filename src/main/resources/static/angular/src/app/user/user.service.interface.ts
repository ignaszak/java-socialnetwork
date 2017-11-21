import {User} from "./user";
import {RestResponse} from "../rest/rest-response";
import {Relation} from "./relation";
import {Invitation} from "./invitation";

export interface UserServiceInterface {

    getUserByUsername(username: string): Promise<User>;
    getUserByEmail(email: string): Promise<User>;
    getCurrentUser(): Promise<User>;
    getFriendsByUser(user: User, page: number): Promise<RestResponse<User>>;
    getCurrentUserInvitations(page: number): Promise<RestResponse<Invitation>>;
    countCurrentUserInvitations(): Promise<number>;
    getRelationByUser(user: User): Promise<Relation>;
    updateUser(user: User): Promise<boolean>;
    changePassword(passwordSet: any): Promise<boolean>;
    inviteUser(user: User): Promise<boolean>;
    acceptByUserId(userId: number): Promise<boolean>;
    deleteRelationByUserId(userId: number): Promise<any>;
}
