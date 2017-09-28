import {Injectable} from "@angular/core";
import {RestProviderInterface} from "./rest-provider.interface";

@Injectable()
export class RestProvider implements RestProviderInterface {

    public static readonly REST_URL = '/rest-api';

    // Custom Controllers
    public static readonly POST_URL                      = RestProvider.REST_URL + '/posts';
    public static readonly COMMENT_URL                   = RestProvider.REST_URL + '/posts/{postId}/comments';
    public static readonly GET_CURRENT_USER_URL          = RestProvider.REST_URL + '/users/current';
    // Repository
    public static readonly GET_POSTS_BY_CURRENT_USER_URL = RestProvider.REST_URL + '/repository/posts/search/queryAllByCurrentUser?page={page}&projection=postProjection';
    public static readonly GET_POSTS_BY_USER_URL         = RestProvider.REST_URL + '/repository/posts/search/queryAllByUser_UsernameOrderByCreatedDateDesc?username={username}&page={page}&projection=postProjection';
    public static readonly GET_COMMENTS_BY_POST_URL      = RestProvider.REST_URL + '/repository/comments/search/queryAllByPost_IdOrderByCreatedDateDesc?postId={postId}&page={page}&projection=commentProjection';
    public static readonly GET_USER_BY_USERNAME_URL      = RestProvider.REST_URL + '/repository/users/search/findUserByUsername?username={username}';
    public static readonly GET_USER_BY_EMAIL_URL         = RestProvider.REST_URL + '/repository/users/search/findUserByEmailOrNewEmail?email={email}';

    public getPath(path: string, params?: Object): string {
        return RestProvider.getPath(path, params);
    }

    public static getPath(path: string, params?: Object): string {
        for (let key in params) path = path.replace('{' + key + '}', params[key]);
        return path;
    }
}
