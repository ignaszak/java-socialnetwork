/**
 * Created by tomek on 11.06.17.
 */

import {Injectable} from "@angular/core";
import {User} from "./user";
import {Http} from "@angular/http";

import 'rxjs/add/operator/toPromise';
import {FormControl} from "@angular/forms";
import {UserServiceInterface} from "./user.service.interface";

@Injectable()
export class UserService implements UserServiceInterface{

    private static readonly GET_CURRENT_USER_URL = '/rest-api/current-user';
    private static readonly GET_USER_BY_USERNAME_URL = '/rest-api/users/search/findUserByUsername?username=';
    private static readonly GET_USER_BY_EMAIL_URL = '/rest-api/users/search/findUserByEmailOrNewEmail?email=';

    constructor(private http: Http) {}

    getUserByUsername(username: string): Promise<User> {
        return this.http.get(UserService.GET_USER_BY_USERNAME_URL + username)
            .toPromise()
            .then(response => response.json() as User)
            .catch(this.handleError);
    }

    getUserByEmail(email: string): Promise<User> {
        return this.http.get(UserService.GET_USER_BY_EMAIL_URL + email)
            .toPromise()
            .then(response => response.json() as User);
    }

    getCurrentUser(): Promise<User> {
        return this.http.get(UserService.GET_CURRENT_USER_URL)
            .toPromise()
            .then(response => response.json() as User)
            .catch(this.handleError);
    }

    updateUser(user: User) {
        this.http.put(UserService.GET_CURRENT_USER_URL, user)
            .subscribe();
    }

    static uniqueEmail(http: Http, currentEmail: String = null): any {
        return (control: FormControl) => {
            return new Promise((resolve) => {
                http.get(UserService.GET_USER_BY_EMAIL_URL + control.value)
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
}
