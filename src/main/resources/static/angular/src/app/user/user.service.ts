/**
 * Created by tomek on 11.06.17.
 */

import {Inject, Injectable} from "@angular/core";
import {User} from "./user";
import {Http} from "@angular/http";

import 'rxjs/add/operator/toPromise';
import {FormControl} from "@angular/forms";
import {UserServiceInterface} from "./user.service.interface";
import {RestProviderInterface} from "../rest/rest-provider.interface";
import {RestProvider} from "../rest/rest-provider";
import {RestResponse} from "../rest/rest-response";
import {Relation} from "./relation";
import {Invitation} from "./invitation";

@Injectable()
export class UserService implements UserServiceInterface{

    constructor(
        private http: Http,
        @Inject('RestProviderInterface') private provider: RestProviderInterface
    ) {}

    getUserByUsername(username: string): Promise<User> {
        let path: string = this.provider.getPath(RestProvider.USER_BY_USERNAME, {'username': username});
        return this.http.get(path)
            .toPromise()
            .then(response => response.json() as User)
            .catch(RestProvider.handleError);
    }

    getUserByEmail(email: string): Promise<User> {
        let path: string = this.provider.getPath(RestProvider.USER_BY_EMAIL, {'email': email});
        return this.http.get(path)
            .toPromise()
            .then(response => response.json() as User)
            .catch(RestProvider.handleError);
    }

    getCurrentUser(): Promise<User> {
        return this.http.get(RestProvider.USER_CURRENT)
            .toPromise()
            .then(response => response.json() as User)
            .catch(RestProvider.handleError);
    }

    getFriendsByUser(user: User, page: number = 0): Promise<RestResponse<User>> {
        let path: string = this.provider.getPath(RestProvider.USER_FRIENDS_PAGEABLE, {'userId': user.id, 'page': page});
        return this.http.get(path)
            .toPromise()
            .then(RestProvider.getRestResponse)
            .catch(RestProvider.handleError);
    }

    getCurrentUserInvitations(page: number = 0): Promise<RestResponse<Invitation>> {
        let path: string = this.provider.getPath(RestProvider.USER_CURRENT_INVITATIONS_PAGEABLE, {'page': page});
        return this.http.get(path)
            .toPromise()
            .then(RestProvider.getRestResponse)
            .catch(RestProvider.handleError);
    }

    countCurrentUserInvitations(): Promise<number> {
        return this.http.get(RestProvider.USER_CURRENT_INVITATIONS_COUNT)
            .toPromise()
            .then(response => Number(response.text()))
            .catch(RestProvider.handleError);
    }

    getRelationByUser(user: User): Promise<Relation> {
        let path: string = this.provider.getPath(RestProvider.USER_FRIEND, {'userId': user.id});
        return this.http.get(path)
            .toPromise()
            .then(response => {
                if (response.text()) return response.json() as Relation;
                return new Relation();
            })
            .catch(RestProvider.handleError)
    }

    updateUser(user: User): void {
        this.http.put(RestProvider.USER_CURRENT, user).subscribe();
    }

    inviteUser(user: User): Promise<boolean> {
        let path: string = this.provider.getPath(RestProvider.USER_FRIENDS, {'userId': user.id});
        return this.http.post(path, null)
            .toPromise()
            .then(() => false) // False for not accepted invitation
            .catch(RestProvider.handleError);
    }

    acceptByUserId(userId: number): Promise<boolean> {
        let path: string = this.provider.getPath(RestProvider.USER_FRIEND, {'userId': userId});
        return this.http.post(path, null)
            .toPromise()
            .then(() => true)
            .catch(RestProvider.handleError);
    }

    deleteRelationByUserId(userId: number): Promise<any> {
        let path: string = this.provider.getPath(RestProvider.USER_FRIENDS, {'userId': userId});
        return this.http.delete(path, null)
            .toPromise()
            .then(() => null)
            .catch(RestProvider.handleError);
    }

    changePassword(passwordSet: any): void {
        this.http.put(RestProvider.USER_CURRENT_PASSWORD, passwordSet).subscribe();
    }

    static uniqueEmail(http: Http, currentEmail: String = null): any {
        return (control: FormControl) => {
            return new Promise((resolve) => {
                let path: string = RestProvider.getPath(RestProvider.USER_BY_EMAIL, {
                    'email': control.value
                });
                http.get(path)
                    .map(res => res.json())
                    .subscribe(
                        user => {
                            if (user.email != currentEmail && user.email == control.value) {
                                resolve({'uniqueEmail': true});
                            } else {
                                resolve(null);
                            }
                        }
                    );
            });
        }
    };
}
