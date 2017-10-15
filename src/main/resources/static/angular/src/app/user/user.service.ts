/**
 * Created by tomek on 11.06.17.
 */

import {Inject, Injectable} from "@angular/core";
import {User} from "./user";
import {Http, Response} from "@angular/http";

import 'rxjs/add/operator/toPromise';
import {FormControl} from "@angular/forms";
import {UserServiceInterface} from "./user.service.interface";
import {RestProviderInterface} from "../rest/rest-provider.interface";
import {RestProvider} from "../rest/rest-provider";
import {RestResponse} from "../rest/rest-response";

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

    updateUser(user: User) {
        this.http.put(RestProvider.USER_CURRENT, user).subscribe();
    }

    getFriendsByUser(user: User): Promise<RestResponse> {
        let path: string = this.provider.getPath(RestProvider.USER_FRIENDS, {'userId': user.id});
        return this.http.get(path)
            .toPromise()
            .then(RestProvider.getRestResponse)
            .catch(RestProvider.handleError);
    }

    getInvitationsbyUser(user: User): Promise<RestResponse> {
        let path: string = this.provider.getPath(RestProvider.USER_FRIENDS, {'userId': user.id});
        return this.http.get(path)
            .toPromise()
            .then(RestProvider.getRestResponse)
            .catch(RestProvider.handleError);
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
