import {Injectable} from "@angular/core";
import {RestProviderInterface} from "./rest-provider.interface";
import {RestResponse} from "./rest-response";
import {Response} from "@angular/http";

@Injectable()
export class RestProvider implements RestProviderInterface {

    private static readonly REST_URL = '/rest-api';

    public static readonly FEED_PAGEABLE          = RestProvider.REST_URL + '/feed?page={page}';

    public static readonly POSTS                  = RestProvider.REST_URL + '/posts';
    public static readonly POST                   = RestProvider.REST_URL + '/posts/{postId}';
    public static readonly POST_COMMENTS          = RestProvider.REST_URL + '/posts/{postId}/comments';
    public static readonly POST_COMMENTS_PAGEABLE = RestProvider.REST_URL + '/posts/{postId}/comments?page={page}';

    public static readonly USER                   = RestProvider.REST_URL + '/users/{userId}';
    public static readonly USER_POSTS_PAGEABLE    = RestProvider.REST_URL + '/users/{userId}/posts?page={page}';
    public static readonly USER_CURRENT           = RestProvider.REST_URL + '/users/current';
    public static readonly USER_BY_EMAIL          = RestProvider.REST_URL + '/users/findByEmail?email={email}';
    public static readonly USER_BY_USERNAME       = RestProvider.REST_URL + '/users/findByUsername?username={username}';

    public static readonly COMMENT_URL            = RestProvider.REST_URL + '/comments/{commentId}';

    public getPath(path: string, params?: Object): string {
        return RestProvider.getPath(path, params);
    }

    public static getPath(path: string, params?: Object): string {
        for (let key in params) path = path.replace('{' + key + '}', params[key]);
        return path;
    }

    public static getRestResponse(res: Response): RestResponse {
        return new RestResponse(res);
    }

    public static handleError(error: any): Promise<any> {
        console.error('An error occurred', error);
        return Promise.reject(error.message || error);
    }
}