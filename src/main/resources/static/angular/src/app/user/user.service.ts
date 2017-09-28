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

@Injectable()
export class UserService implements UserServiceInterface{

    constructor(
        private http: Http,
        @Inject('RestProviderInterface') private provider: RestProviderInterface
    ) {}

    getUserByUsername(username: string): Promise<User> {
        let path: string = this.provider.getPath(RestProvider.GET_USER_BY_USERNAME_URL, {'username': username});
        return this.http.get(path)
            .toPromise()
            .then(this.getUser)
            .catch(this.handleError);
    }

    getUserByEmail(email: string): Promise<User> {
        let path: string = this.provider.getPath(RestProvider.GET_USER_BY_EMAIL_URL, {'email': email});
        return this.http.get(path)
            .toPromise()
            .then(this.getUser);
    }

    getCurrentUser(): Promise<User> {
        return this.http.get(RestProvider.GET_CURRENT_USER_URL)
            .toPromise()
            .then(this.getUser)
            .catch(this.handleError);
    }

    updateUser(user: User) {
        this.http.put(RestProvider.GET_CURRENT_USER_URL, user)
            .subscribe();
    }

    static uniqueEmail(http: Http, currentEmail: String = null): any {
        return (control: FormControl) => {
            return new Promise((resolve) => {
                let path: string = RestProvider.getPath(RestProvider.GET_USER_BY_EMAIL_URL, {
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

    private handleError(error: any): Promise<any> {
        console.error('An error occurred', error);
        return Promise.reject(error.message || error);
    }

    private getUser(response: Response): User {
        return new User(response.json());
    }
}
