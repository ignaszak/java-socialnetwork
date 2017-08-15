/**
 * Created by tomek on 11.06.17.
 */

import {Injectable} from "@angular/core";
import {User} from "./user";
import {Http} from "@angular/http";

import {FormControl} from "@angular/forms";
import {UserServiceInterface} from "./user.service.interface";

@Injectable()
export class UserDevService implements UserServiceInterface {

    getUserByUsername(username: string): Promise<User> {
        return this.getDevUser();
    }

    getUserByEmail(email: string): Promise<User> {
        return this.getDevUser();
    }

    getCurrentUser(): Promise<User> {
        return this.getDevUser();
    }

    getDevUser(): Promise<User> {
        let user: User = {
            id: 1,
            email: 'test@test.pl',
            username: 'test',
            password: '',
            role: 'ROLE_TEST',
            caption: 'Test caption',
            status: ''
        };
        return Promise.resolve(user);
    }

    updateUser(user: User) {
    }

    static uniqueEmail(http: Http, currentEmail: String = null): any {
        return (control: FormControl) => {
            return new Promise((resolve) => {
                resolve(null);
                //resolve({'uniqueEmail': true});
            });
        }
    };

    private handleError(error: any): Promise<any> {
        console.error('An error occurred', error);
        return Promise.reject(error.message || error);
    }
}
